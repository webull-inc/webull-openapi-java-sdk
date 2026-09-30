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
package com.webull.openapi.core.sse.internal.lifecycle.proxy;

import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.sse.internal.SseObserver;
import com.webull.openapi.core.sse.lifecycle.SseHandler;
import com.webull.openapi.core.sse.lifecycle.SseInboundHandler;
import com.webull.openapi.core.sse.lifecycle.SseSessionHandler;
import com.webull.openapi.core.sse.lifecycle.SseSysEventHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SseHandlerProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(SseHandlerProxyFactory.class);

    private SseHandlerProxyFactory() {
    }

    private static class InstanceHolder {
        private static final SseHandlerProxyFactory instance = new SseHandlerProxyFactory();
    }

    public static SseHandlerProxyFactory getInstance() {
        return SseHandlerProxyFactory.InstanceHolder.instance;
    }

    public List<SseObserver> create(SseHandler handler) {
        return create(Collections.singletonList(handler));
    }

    public List<SseObserver> create(List<SseHandler> handlers) {
        List<SseObserver> proxies = new LinkedList<>();
        handlers.forEach(handler -> this.addProxies(proxies, handler));
        return proxies;
    }

    private void addProxies(List<SseObserver> proxies, SseHandler handler) {
        final int size = proxies.size();
        if (handler instanceof SseSessionHandler) {
            proxies.add(new SseSessionHandlerProxy((SseSessionHandler) handler));
        }
        if (handler instanceof SseInboundHandler) {
            proxies.add(new SseInboundHandlerProxy((SseInboundHandler) handler));
        }
        if (handler instanceof SseSysEventHandler) {
            proxies.add(new SseSysEventHandlerProxy((SseSysEventHandler) handler));
        }
        if (size == proxies.size()) {
            logger.warn("Incapable of creating proxy for handler type={}.", handler.getClass());
        }
    }
}
