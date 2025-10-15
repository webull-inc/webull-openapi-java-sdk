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
package com.webull.openapi.data.internal.mqtt.lifecycle;

import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.common.ArgNames;
import com.webull.openapi.data.internal.mqtt.support.SchedulerConfig;
import com.webull.openapi.data.quotes.api.IDataClient;
import com.webull.openapi.data.quotes.api.subsribe.IMarketStreamingClient;
import com.webull.openapi.data.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.data.quotes.subsribe.lifecycle.QuotesSubsConnectedContext;
import com.webull.openapi.data.quotes.subsribe.lifecycle.QuotesSubsFailedContext;
import com.webull.openapi.data.quotes.subsribe.lifecycle.QuotesSubsSessionHandler;
import com.webull.openapi.data.quotes.subsribe.lifecycle.SubscriptionManager;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.core.utils.StringUtils;
import io.reactivex.disposables.Disposable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ApiSubscriptionManager implements SubscriptionManager, QuotesSubsSessionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiSubscriptionManager.class);

    private volatile Disposable subscribeDisposable;
    private final AtomicReference<SubscribeTask> taskRef = new AtomicReference<>();

    private final IMarketStreamingClient apiClient;
    private final RetryPolicy retryPolicy;
    private final Map<SubsGroupKey, Set<String>> subscriptionGroup = new ConcurrentHashMap<>();

    private volatile boolean subscribeOnConnected = false;
    private Consumer<Throwable> subsOnConnectedFailedFallback;

    public ApiSubscriptionManager(IMarketStreamingClient apiClient, RetryPolicy retryPolicy) {
        this.apiClient = apiClient;
        this.retryPolicy = retryPolicy;
    }

    public ApiSubscriptionManager(IMarketStreamingClient apiClient, RetryPolicy retryPolicy,
                                  Set<String> symbols, String category, Set<String> subTypes,
                                  String depth, Boolean overnightRequired
                                  ) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notEmpty(ArgNames.SUBTYPES, subTypes);
        this.apiClient = apiClient;
        this.retryPolicy = retryPolicy;
        this.subscriptionGroup.put(SubsGroupKey.of(category, subTypes, depth, overnightRequired), symbols);
    }

    @Override
    public void subscribeOnConnected(Consumer<Throwable> subscribeFailedFallback) {
        this.subsOnConnectedFailedFallback = subscribeFailedFallback;
        this.subscribeOnConnected = true;
    }

    @Override
    public void addSubscription(String sessionId, Set<String> symbols, String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
        this.apiClient.subscribe(sessionId, symbols, category, subTypes, depth, overnightRequired);
        this.subscriptionGroup.merge(SubsGroupKey.of(category, subTypes, depth, overnightRequired), symbols, (existed, newValue) -> {
            existed.addAll(newValue);
            return existed;
        });
    }

    @Override
    public void removeSubscription(String sessionId, Set<String> symbols, String category, Set<String> subTypes,
                                   Boolean unsubscribeAll) {
        this.apiClient.unsubscribe(sessionId, symbols, category, subTypes, unsubscribeAll);
        this.subscriptionGroup.computeIfPresent(SubsGroupKey.of(category, subTypes, null, null), (key, existed) -> {
            existed.removeAll(symbols);
            return existed.isEmpty() ? null : existed;
        });
    }

    @Override
    public void onConnected(QuotesSubsConnectedContext context) {
        if (!this.subscribeOnConnected) {
            return;
        }

        String sessionId = context.getAuthProvider().getSessionId();
        if (StringUtils.isBlank(sessionId)) {
            logger.error("Cannot add quotes subscription before session initialized.");
            return;
        }
        if (this.subscribeDisposable != null) {
            this.subscribeDisposable.dispose();
        }
        SubscribeTask newTask = new SubscribeTask(sessionId, context.getState());
        taskRef.set(newTask);
        if (logger.isDebugEnabled()) {
            logger.debug("Start add quotes subscription task[{}] on mqtt connected.", newTask.taskId);
        }
        this.subscribeDisposable = SchedulerConfig.api().scheduleDirect(newTask);
    }

    @Override
    public void onDisconnected(QuotesSubsFailedContext context) {
        if (!this.subscribeOnConnected) {
            return;
        }
        if (this.subscribeDisposable != null) {
            this.subscribeDisposable.dispose();
            this.subscribeDisposable = null;
        }
        SubscribeTask task = taskRef.getAndSet(null);
        if (logger.isDebugEnabled() && task != null) {
            logger.debug("Stop add quotes subscription task[{}] on mqtt disconnected.", task.taskId);
        }
    }

    @Override
    public void close() {
        this.onDisconnected(null);
    }

    private final class SubscribeTask implements Runnable {

        private final String taskId;
        private final String sessionId;
        private final ClientStateMachine state;
        private int attempts = 0;

        private SubscribeTask(String sessionId, ClientStateMachine state) {
            this.state = state;
            this.taskId = GUID.get();
            this.sessionId = sessionId;
        }

        @Override
        public void run() {
            if (this != taskRef.get()) {
                // break when a new task added.
                return;
            }
            try {
                if (CollectionUtils.isNotEmpty(subscriptionGroup)) {
                    subscriptionGroup.forEach((groupKey, symbols) ->
                            apiClient.subscribe(sessionId, symbols, groupKey.getCategory(), groupKey.getSubTypes(),
                                    groupKey.getDepth(), groupKey.getOvernightRequired()));
                    if (logger.isDebugEnabled()) {
                        logger.debug("Add quotes subscription successful by task[{}].", this.taskId);
                    }
                }
            } catch (Exception e) {
                QuotesApiFailedContext newContext = new QuotesApiFailedContext(state, ++attempts, e);
                boolean shouldRetry = retryPolicy.shouldRetry(newContext);
                logger.error("Add subscription error when subscribing quotes, attempts={}, should retry={}, task[{}].",
                        attempts, shouldRetry, this.taskId, e);
                if (shouldRetry) {
                    long delayMillis = retryPolicy.nextRetryDelay(newContext, TimeUnit.MILLISECONDS);
                    subscribeDisposable = SchedulerConfig.api().scheduleDirect(this, delayMillis, TimeUnit.MILLISECONDS);
                } else {
                    try {
                        subsOnConnectedFailedFallback.accept(e);
                    } catch (Exception fallbackErr) {
                        logger.error("Startup subscribe failed fallback error", fallbackErr);
                    }
                }
            }
        }
    }

    private static class SubsGroupKey {

        private final String category;
        private final Set<String> subTypes;
        private final String depth;
        private final Boolean overnightRequired;

        private SubsGroupKey(String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
            this.category = category;
            this.subTypes = subTypes;
            this.depth = depth;
            this.overnightRequired = overnightRequired;
        }

        static SubsGroupKey of(String category, Set<String> subTypes, String depth, Boolean overnightRequired) {
            return new SubsGroupKey(category, subTypes, depth, overnightRequired);
        }

        String getCategory() {
            return category;
        }

        Set<String> getSubTypes() {
            return subTypes;
        }

        String getDepth() {
            return depth;
        }

        Boolean getOvernightRequired() {
            return overnightRequired;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SubsGroupKey groupKey = (SubsGroupKey) o;
            // The same category, subTypes, and symbol cannot be subscribed to multiple times. The uniqueness is determined by category + subTypes.
            return Objects.equals(category, groupKey.category) && Objects.equals(subTypes, groupKey.subTypes)
                    && Objects.equals(depth, groupKey.getDepth()) && Objects.equals(overnightRequired, groupKey.getOvernightRequired());
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, subTypes, depth, overnightRequired);
        }
    }
}
