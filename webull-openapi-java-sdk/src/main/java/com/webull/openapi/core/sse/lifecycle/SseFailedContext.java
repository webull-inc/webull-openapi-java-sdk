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
package com.webull.openapi.core.sse.lifecycle;

public interface SseFailedContext {

    boolean userCancelled();

    /**
     * @return the HTTP status code when the subscription was rejected before the stream was
     * established, 429 for a {@code numOfConnExceed} frame, or 0 for transport level failures.
     */
    int getStatusCode();

    Throwable getCause();
}
