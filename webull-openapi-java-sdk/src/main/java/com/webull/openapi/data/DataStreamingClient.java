/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.data;

import com.hivemq.client.internal.rx.RxFutureConverter;
import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilder;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.MqttProxyProtocol;
import com.hivemq.client.mqtt.exceptions.MqttClientStateException;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.internal.mqtt.lifecycle.QuotesClientState;
import com.webull.openapi.data.internal.mqtt.lifecycle.binder.MqttClientHandlerBinder;
import com.webull.openapi.data.internal.mqtt.lifecycle.binder.MqttClientHandlerBinderRegistry;
import com.webull.openapi.data.internal.mqtt.message.MqttPublish;
import com.webull.openapi.data.quotes.subsribe.IDataStreamingClient;
import com.webull.openapi.data.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.data.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.data.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.data.quotes.subsribe.lifecycle.SubscriptionManager;
import com.webull.openapi.data.quotes.subsribe.message.ConnAck;
import com.webull.openapi.data.quotes.subsribe.proxy.ProxyConfig;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class DataStreamingClient implements IDataStreamingClient {

    private static final Logger logger = LoggerFactory.getLogger(DataStreamingClient.class);

    private final ClientStateMachine stateMachine = new QuotesClientState();

    private final Mqtt3RxClient client;
    private final MqttClientHandlerBinderRegistry handlerBinderRegistry = MqttClientHandlerBinderRegistry.getInstance();

    private final RetryPolicy retryPolicy;
    private final AuthProvider authProvider;
    private final SubscriptionManager subscriptionManager;
    private final LinkedList<QuotesSubsHandler> handlers;


    public DataStreamingClient(String host,
                                  int port,
                                  boolean enableTls,
                                  RetryPolicy retryPolicy,
                                  AuthProvider authProvider,
                                  SubscriptionManager subscriptionManager,
                                  LinkedList<QuotesSubsHandler> handlers,
                                  long connectTimeoutMillis,
                                  long readTimeoutMillis,
                                  ProxyConfig proxyConfig) {
        Assert.notBlank("host", host);
        Assert.inRange("port", port, 0, 65535);
        Assert.notNull("authProvider", authProvider);
        Assert.notNull("subscriptionManager", subscriptionManager);

        this.retryPolicy = retryPolicy != null ? retryPolicy : RetryPolicy.never();
        this.authProvider = authProvider;
        this.subscriptionManager = subscriptionManager;
        this.handlers = handlers;

        Mqtt3ClientBuilder clientBuilder = Mqtt3Client.builder()
                .identifier(authProvider.getSessionId());

        // transport config
        MqttClientTransportConfigBuilder transportBuilder = MqttClientTransportConfig.builder()
                .serverHost(host)
                .serverPort(port)
                .socketConnectTimeout(connectTimeoutMillis, TimeUnit.MILLISECONDS)
                .mqttConnectTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS);

        if (enableTls) {
            transportBuilder = transportBuilder.sslWithDefaultConfig();
        }

        if (proxyConfig != null) {
            MqttProxyProtocol protocol = null;
            switch (proxyConfig.getProtocol()) {
                case SOCKS4:
                    protocol = MqttProxyProtocol.SOCKS_4;
                    break;
                case SOCKS5:
                    protocol = MqttProxyProtocol.SOCKS_5;
                    break;
                case HTTP:
                    protocol = MqttProxyProtocol.HTTP;
                    break;
            }
            if (protocol != null) {
                transportBuilder.proxyConfig()
                        .protocol(protocol)
                        .host(proxyConfig.getHost())
                        .port(proxyConfig.getPort())
                        .username(proxyConfig.getUsername())
                        .password(proxyConfig.getPassword())
                        .applyProxyConfig();
            }
        }

        final Mqtt3ClientBuilder finalBuilder = clientBuilder.transportConfig(transportBuilder.build());

        if (CollectionUtils.isNotEmpty(this.handlers)) {
            this.handlers.forEach(handler -> handlerBinderRegistry.get(handler)
                    .ifPresent(binder -> binder.bindOnSession(handler, this.stateMachine, this.authProvider, finalBuilder)));
        }

        this.client = finalBuilder.buildRx();
        this.subscriptionManager.subscribeOnConnected(err -> {
            if (this.client.getState().isConnected()) {
                this.client.disconnect().blockingAwait();
            }
        });
    }

    @Override
    public Single<ConnAck> connectRx() {
        return Single.fromCallable(stateMachine::callConnect)
                .flatMap(success -> {
                    if (!success) {
                        return Single.error(new ClientException(ErrorCode.INVALID_STATE, "Quotes client is not disconnected"));
                    }
                    return Single.just(true);
                }).flatMap(ignored -> this.client.connectWith()
                        .simpleAuth()
                        .username(this.authProvider.getAppKey())
                        .password("".getBytes())
                        .applySimpleAuth()
                        .applyConnect()
                        .map(mqtt3ConnAck -> new ConnAck(mqtt3ConnAck.getReturnCode().getCode())))
                .doOnSuccess(ack -> stateMachine.connected())
                .doOnError(err -> stateMachine.connectFailed());
    }

    @Override
    public Completable disconnectRx() {
       return stateMachine.callDisconnect(() -> {
           if (this.client.getState().isConnectedOrReconnect()) {
               try {
                   this.client.disconnect().blockingAwait();
               } catch (MqttClientStateException e) {
                   logger.warn("Disconnect mqtt client that is not connected.");
               }
           }
       });
    }

    @Override
    public Flowable<Object> subscribeRx() {
        Flowable<Object> publishes = this.client.publishes(MqttGlobalPublishFilter.ALL)
                .map(mqtt3Publish -> new MqttPublish(mqtt3Publish.getTopic().toString(), mqtt3Publish.getPayload().orElse(null)));

        if (CollectionUtils.isEmpty(this.handlers)) {
            return publishes;
        }
        LinkedHashMap<QuotesSubsHandler, MqttClientHandlerBinder> handlerToBinders = new LinkedHashMap<>();
        for (QuotesSubsHandler handler : this.handlers) {
            handlerBinderRegistry.get(handler).ifPresent(binder -> handlerToBinders.put(handler, binder));
        }

        return this.bindBatch(handlerToBinders.entrySet().iterator(), publishes);
    }

    private Flowable<Object> bindBatch(Iterator<Map.Entry<QuotesSubsHandler, MqttClientHandlerBinder>> iterator, Flowable<Object> flowable) {
        if (!iterator.hasNext()) {
            return flowable;
        }
        Map.Entry<QuotesSubsHandler, MqttClientHandlerBinder> entry = iterator.next();
        return bindBatch(iterator, entry.getValue().bindOnPublishes(entry.getKey(), flowable));
    }

    @Override
    public Completable addSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
        return Completable.fromRunnable(() -> this.subscriptionManager.addSubscription(authProvider.getSessionId(), symbols, category, subTypes, depth, overnightRequired));
    }

    @Override
    public Completable removeSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes) {
        return removeSubscriptionRx(symbols, category, subTypes, false);
    }

    @Override
    public Completable removeAllSubscriptionRx() {
        return removeSubscriptionRx(null, null, null, true);
    }

    private Completable removeSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes, Boolean unsubscribeAll) {
        return Completable.fromRunnable(() -> this.subscriptionManager.removeSubscription(authProvider.getSessionId(), symbols, category, subTypes, unsubscribeAll));
    }

    @Override
    public CompletableFuture<ConnAck> connectAsync() {
        return RxFutureConverter.toFuture(this.connectRx());
    }

    @Override
    public CompletableFuture<Void> disconnectAsync() {
        return RxFutureConverter.toFuture(this.disconnectRx());
    }

    @Override
    public CompletableFuture<Void> subscribeAsync() {
        return RxFutureConverter.toFuture(this.subscribeRx().ignoreElements());
    }

    @Override
    public CompletableFuture<Void> addSubscriptionAsync(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
        return RxFutureConverter.toFuture(this.addSubscriptionRx(symbols, category, subTypes, depth, overnightRequired));
    }

    @Override
    public CompletableFuture<Void> removeSubscriptionAsync(Set<String> symbols, String category, Set<String> subTypes) {
        return RxFutureConverter.toFuture(this.removeSubscriptionRx(symbols, category, subTypes));
    }

    @Override
    public CompletableFuture<Void> removeAllSubscriptionAsync() {
        return RxFutureConverter.toFuture(this.removeAllSubscriptionRx());
    }

    @Override
    public ConnAck connectBlocking() {
        return this.connectRx().blockingGet();
    }

    @Override
    public void disconnectBlocking() {
        this.disconnectRx().blockingAwait();
    }

    @Override
    public void subscribeBlocking() {
        this.subscribeRx().ignoreElements().blockingAwait();
    }

    @Override
    public void addSubscriptionBlocking(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
        this.addSubscriptionRx(symbols, category, subTypes, depth, overnightRequired).blockingAwait();
    }

    @Override
    public void removeSubscriptionBlocking(Set<String> symbols, String category, Set<String> subTypes) {
        this.removeSubscriptionRx(symbols, category, subTypes).blockingAwait();
    }

    @Override
    public void removeAllSubscriptionBlocking() {
        this.removeAllSubscriptionRx().blockingAwait();
    }

    @Override
    public void close() {
        // change state first
        this.stateMachine.close();
        // release other resources
        this.authProvider.close();
        this.subscriptionManager.close();
        if (!this.stateMachine.isDisconnected()) {
            this.disconnectRx().blockingAwait();
        }
    }
}
