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
package com.webull.openapi.core.sse.internal.lifecycle;

import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.sse.lifecycle.SseFailedContext;
import com.webull.openapi.core.sse.lifecycle.SseInboundHandler;
import com.webull.openapi.core.sse.lifecycle.SseSessionHandler;
import com.webull.openapi.core.sse.lifecycle.SseSuccessContext;
import com.webull.openapi.core.sse.lifecycle.SseSysEventHandler;
import com.webull.openapi.core.sse.message.SseSubscribeResponse;

/**
 * Interruptions are logged at WARN without the stack trace since most of them are recovered by
 * reconnecting, e.g. the 429 raised during a rolling release. Giving up is logged at ERROR by
 * the connection itself.
 */
public class SseLoggingHandler implements SseSessionHandler, SseInboundHandler, SseSysEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(SseLoggingHandler.class);

    @Override
    public void onSuccess(SseSuccessContext context) {
        if (logger.isDebugEnabled()) {
            logger.debug("Sse event session subscribed, ack={}.", context.getAck().orElse(null));
        }
    }

    @Override
    public void onFailed(SseFailedContext context) {
        if (context.userCancelled()) {
            logger.debug("Sse event session ended by user cancel.");
        } else {
            logger.warn("Sse event session interrupted, status={}, cause={}.",
                    context.getStatusCode(), String.valueOf(context.getCause()));
        }
    }

    @Override
    public void onMessage(SseSubscribeResponse message) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received sse event msg={}", message);
        }
    }

    @Override
    public void onSubscribeExpired(SseSubscribeResponse response) {
        logger.error("Sse event subscribe expired, msg={}", response);
    }
}
