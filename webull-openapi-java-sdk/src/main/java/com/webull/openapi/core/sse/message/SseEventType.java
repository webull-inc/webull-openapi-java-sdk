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
package com.webull.openapi.core.sse.message;

import java.util.Optional;

/**
 * Frame types identified by the SSE {@code event:} field name. The {@code eventType} inside the
 * data is not reliable for classification since zero-valued fields are omitted by the server.
 *
 * <p>Heartbeats are SSE comment lines rather than events, and auth failures close the connection
 * without any frame, so neither has a type here.
 */
public enum SseEventType {

    SubscribeSuccess("subscribeSuccess"),
    /**
     * Business event, the upstream event type value is carried by {@code eventType}.
     */
    Event("event"),
    /**
     * The session mutex rejected this connection after the stream was committed, equivalent to 429.
     */
    NumOfConnExceed("numOfConnExceed"),
    SubscribeExpired("subscribeExpired"),
    ;

    private final String eventName;

    SseEventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public static Optional<SseEventType> fromEventName(String eventName) {
        for (SseEventType type : values()) {
            if (type.eventName.equals(eventName)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
