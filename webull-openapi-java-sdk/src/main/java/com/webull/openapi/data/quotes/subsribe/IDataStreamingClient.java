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
package com.webull.openapi.data.quotes.subsribe;

import com.webull.openapi.data.internal.mqtt.DataStreamingClientBuilder;
import com.webull.openapi.data.quotes.subsribe.message.ConnAck;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.io.Closeable;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface IDataStreamingClient extends Closeable {

    static IDataStreamingClientBuilder builder() {
        return new DataStreamingClientBuilder();
    }

    Single<ConnAck> connectRx();

    Completable disconnectRx();

    Flowable<Object> subscribeRx();

    Completable addSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired);

    Completable removeSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes);

    Completable removeAllSubscriptionRx();

    CompletableFuture<ConnAck> connectAsync();

    CompletableFuture<Void> disconnectAsync();

    CompletableFuture<Void> subscribeAsync();

    CompletableFuture<Void> addSubscriptionAsync(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired);

    CompletableFuture<Void> removeSubscriptionAsync(Set<String> symbols, String category, Set<String> subTypes);

    CompletableFuture<Void> removeAllSubscriptionAsync();

    ConnAck connectBlocking();

    void disconnectBlocking();

    void subscribeBlocking();

    void addSubscriptionBlocking(Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired);

    void removeSubscriptionBlocking(Set<String> symbols, String category, Set<String> subTypes);

    void removeAllSubscriptionBlocking();

    @Override
    default void close() {
        // cover IOException
    }
}
