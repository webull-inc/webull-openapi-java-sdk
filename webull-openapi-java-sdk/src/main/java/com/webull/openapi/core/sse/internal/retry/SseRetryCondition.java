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
package com.webull.openapi.core.sse.internal.retry;

import com.webull.openapi.core.http.common.HttpStatus;
import com.webull.openapi.core.retry.RetryContext;
import com.webull.openapi.core.retry.condition.RetryCondition;
import com.webull.openapi.core.sse.lifecycle.SseFailedContext;

public class SseRetryCondition implements RetryCondition {

    /**
     * Statuses worth another attempt. 429 means the previous session of the same app key is
     * still held by the server side, typically a leftover during a rolling release, so it has
     * to be retried with backoff. A {@code numOfConnExceed} frame is reported as 429 as well. The remaining 4xx are caused by credentials or parameters
     * that reconnecting cannot fix.
     */
    private static final int[] RETRYABLE_STATUS_CODES = {
            HttpStatus.TOO_MANY_REQUESTS,
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.BAD_GATEWAY,
            HttpStatus.SERVICE_UNAVAILABLE,
            HttpStatus.GATEWAY_TIMEOUT
    };

    private static final int NO_STATUS_CODE = 0;

    private SseRetryCondition() {
    }

    private static class InstanceHolder {
        private static final SseRetryCondition instance = new SseRetryCondition();
    }

    public static SseRetryCondition getInstance() {
        return SseRetryCondition.InstanceHolder.instance;
    }

    @Override
    public boolean shouldRetry(RetryContext context) {
        if (!(context instanceof SseFailedContext)) {
            throw new IllegalArgumentException("Retry context[" + context.getClass().getName() +
                    "] is inappropriate to sse retry condition!");
        }
        SseFailedContext failedContext = (SseFailedContext) context;
        if (failedContext.userCancelled()) {
            return false;
        }
        int statusCode = failedContext.getStatusCode();
        if (NO_STATUS_CODE == statusCode) {
            // transport failure or stream closed by the server
            return true;
        }
        for (int retryableStatusCode : RETRYABLE_STATUS_CODES) {
            if (retryableStatusCode == statusCode) {
                return true;
            }
        }
        return false;
    }
}
