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
package com.webull.openapi.trade;

import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.trade.events.internal.CancellableISubscription;
import com.webull.openapi.trade.grpc.BaseGrpcClient;
import com.webull.openapi.trade.grpc.StatefulResponseObserver;
import com.webull.openapi.trade.grpc.auth.SignatureCallCredentials;
import com.webull.openapi.trade.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.trade.grpc.lifecycle.proxy.HandlerProxyFactory;
import com.webull.openapi.trade.grpc.retry.SynchronousGrpcRetryable;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.trade.events.internal.lifecycle.proxy.SubscribeHandlerProxyFactory;
import com.webull.openapi.trade.events.internal.proto.EventServiceGrpc;
import com.webull.openapi.trade.events.internal.proto.Events;
import com.webull.openapi.trade.events.subscribe.ITradeEventClient;
import com.webull.openapi.trade.events.subscribe.ISubscription;
import com.webull.openapi.trade.events.subscribe.message.SubscribeRequest;
import io.grpc.CallCredentials;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class TradeEventClient extends BaseGrpcClient<Events.SubscribeResponse> implements ITradeEventClient {

    private final AtomicBoolean subscribing = new AtomicBoolean(false);

    private final EventServiceGrpc.EventServiceStub stub;

    public TradeEventClient(String appKey,
                            String appSecret,
                            String host,
                            int port,
                            RetryPolicy retryPolicy,
                            boolean enableTls,
                            List<GrpcHandler> handlers) {
        this(appKey, appSecret, host, port, retryPolicy, enableTls, handlers,
                SubscribeHandlerProxyFactory.getInstance());
    }

    protected TradeEventClient(String appKey,
                               String appSecret,
                               String host,
                               int port,
                               RetryPolicy retryPolicy,
                               boolean enableTls,
                               List<GrpcHandler> handlers,
                               HandlerProxyFactory<Events.SubscribeResponse> handlerProxyFactory) {
        super(appKey, appSecret, host, port, retryPolicy, enableTls, handlers, handlerProxyFactory);
        this.buildChannel();
        this.stub = EventServiceGrpc.newStub(this.channel);
    }

    @Override
    public ISubscription subscribe(SubscribeRequest request) {
        // try lock
        if (!subscribing.compareAndSet(false, true)) {
            throw new ClientException(ErrorCode.INVALID_STATE, "Client is already subscribed");
        }

        Events.SubscribeRequest grpcRequest = Events.SubscribeRequest.newBuilder()
                .setSubscribeType(request.getSubscribeType())
                .setTimestamp(request.getTimestamp())
                .addAllAccounts(request.getAccounts())
                .build();

        byte[] requestBytes = grpcRequest.toByteArray();
        CallCredentials credentials = new SignatureCallCredentials(appKey, appSecret, requestBytes);
        EventServiceGrpc.EventServiceStub credentialsStub = this.stub.withCallCredentials(credentials);

        StatefulResponseObserver<Events.SubscribeRequest, Events.SubscribeResponse> responseObserver =
                new StatefulResponseObserver<>(this.subObservers);
        Supplier<Object> doSubscribe = () -> {
            credentialsStub.subscribe(grpcRequest, responseObserver);
            return null;
        };
        responseObserver.setRetryable(new SynchronousGrpcRetryable(doSubscribe, this.retryPolicy));

        ISubscription subscription = new CancellableISubscription(responseObserver);
        // release lock
        subscription.completable().whenComplete((ignore, ex) -> subscribing.compareAndSet(true, false));

        doSubscribe.get();
        return subscription;
    }
}
