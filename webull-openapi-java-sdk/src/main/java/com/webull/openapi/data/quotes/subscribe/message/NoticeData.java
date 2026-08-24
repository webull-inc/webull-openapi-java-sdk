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
package com.webull.openapi.data.quotes.subscribe.message;

/**
 * Notice message received from the server via MQTT "notice" topic.
 * <p>
 * Two types of notice messages:
 * <ul>
 *   <li>type=1001: Status message (rtt, drop, sent)</li>
 *   <li>type=1002: Permission grabbed message (content)</li>
 * </ul>
 */
public class NoticeData {

    /**
     * Notice type: "1001" for status, "1002" for permission grabbed.
     */
    private String type;

    /**
     * Client-server latency in ms (type=1001).
     */
    private Long rtt;

    /**
     * Server dropped message count (type=1001).
     */
    private Long drop;

    /**
     * Server sent message count (type=1001).
     */
    private Long sent;

    /**
     * Notice content, e.g. permission grabbed reason (type=1002).
     */
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRtt() {
        return rtt;
    }

    public void setRtt(Long rtt) {
        this.rtt = rtt;
    }

    public Long getDrop() {
        return drop;
    }

    public void setDrop(Long drop) {
        this.drop = drop;
    }

    public Long getSent() {
        return sent;
    }

    public void setSent(Long sent) {
        this.sent = sent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Whether this is a permission grabbed notice.
     */
    public boolean isPermissionGrabbed() {
        return "1002".equals(type);
    }

    /**
     * Whether this is a status notice.
     */
    public boolean isStatus() {
        return "1001".equals(type);
    }

    @Override
    public String toString() {
        return "NoticeData{" +
                "type='" + type + '\'' +
                ", rtt=" + rtt +
                ", drop=" + drop +
                ", sent=" + sent +
                ", content='" + content + '\'' +
                '}';
    }
}
