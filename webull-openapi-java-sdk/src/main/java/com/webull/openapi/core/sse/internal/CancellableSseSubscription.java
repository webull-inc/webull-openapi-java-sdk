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
package com.webull.openapi.core.sse.internal;

import com.webull.openapi.core.exception.ClientException;
import com.webull.openapi.core.exception.ServerException;
import com.webull.openapi.core.sse.ISseSubscription;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class CancellableSseSubscription implements ISseSubscription {

    private final SseConnection connection;

    public CancellableSseSubscription(SseConnection connection) {
        this.connection = connection;
    }

    @Override
    public void unsubscribe() {
        this.connection.cancel();
    }

    @Override
    public CompletableFuture<Void> completable() {
        return this.connection.streamingFuture();
    }

    @Override
    public void blockingAwait() {
        try {
            this.connection.streamingFuture().get();
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new ClientException("Waiting for result interrupted.", interrupted);
        } catch (ExecutionException ex) {
            // surface the server rejection (e.g. 401/403) as is, so callers can catch ServerException
            Throwable cause = ex.getCause();
            if (cause instanceof ServerException) {
                throw (ServerException) cause;
            }
            if (cause instanceof ClientException) {
                throw (ClientException) cause;
            }
            throw new ClientException("Error waiting for result.", ex);
        } catch (Exception ex) {
            throw new ClientException("Error waiting for result.", ex);
        }
    }
}
