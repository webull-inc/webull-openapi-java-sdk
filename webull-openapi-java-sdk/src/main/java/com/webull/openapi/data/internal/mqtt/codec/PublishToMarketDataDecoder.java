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
package com.webull.openapi.data.internal.mqtt.codec;

import com.google.gson.Gson;
import com.webull.openapi.core.common.dict.SubscribeType;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.internal.mqtt.message.MqttPublish;
import com.webull.openapi.data.quotes.domain.QuotesBasic;
import com.webull.openapi.data.quotes.subscribe.codec.AbstractInboundDecoder;
import com.webull.openapi.data.quotes.subscribe.message.MarketData;
import com.webull.openapi.data.quotes.subscribe.message.Metadata;
import com.webull.openapi.data.quotes.subscribe.message.NoticeData;
import com.webull.openapi.data.quotes.subscribe.message.QuotesPublish;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class PublishToMarketDataDecoder extends AbstractInboundDecoder<MqttPublish, MarketData> {

    private static final Logger logger = LoggerFactory.getLogger(PublishToMarketDataDecoder.class);
    private static final String ECHO_TOPIC = "echo";
    private static final String NOTICE_TOPIC = "notice";

    private final Gson gson = new Gson();
    private final List<Consumer<NoticeData>> noticeHandlers;

    private final Map<SubscribeType, QuotesPublishDecoder<? extends QuotesBasic>> delegates = new EnumMap<>(SubscribeType.class);

    public PublishToMarketDataDecoder() {
        this(Collections.emptyList());
    }

    public PublishToMarketDataDecoder(List<Consumer<NoticeData>> noticeHandlers) {
        this.noticeHandlers = noticeHandlers != null ? noticeHandlers : Collections.emptyList();
        delegates.put(SubscribeType.QUOTE, new QuoteDecoder());
        delegates.put(SubscribeType.SNAPSHOT, new SnapshotDecoder());
        delegates.put(SubscribeType.TICK, new TickDecoder());
        delegates.put(SubscribeType.EVENT_QUOTE, new EventDepthDecoder());
        delegates.put(SubscribeType.EVENT_SNAPSHOT, new EventSnapshotDecoder());
        delegates.put(SubscribeType.EVENT_TICK, new EventTickDecoder());
    }

    @Override
    public MarketData decode(MqttPublish in) {
        String topic = in.getTopic();

        if (ECHO_TOPIC.equals(topic)) {
            logger.debug("No decoding is required, the type is {}.", topic);
            return null;
        }

        if (NOTICE_TOPIC.equals(topic)) {
            handleNotice(in);
            return null;
        }

        Optional<SubscribeType> subscribeTypeOpt = SubscribeType.fromType(topic);
        if (!subscribeTypeOpt.isPresent()) {
            logger.warn("Unrecognized data type={}.", topic);
            return null;
        }
        Metadata metadata = new Metadata(subscribeTypeOpt.get());
        QuotesPublishDecoder<?> delegate = delegates.get(subscribeTypeOpt.get());
        QuotesPublish<?> delegateOut = delegate.decode(in.getPayload());
        return new MarketData(metadata, delegateOut);
    }

    private void handleNotice(MqttPublish in) {
        NoticeData notice = decodeNotice(in);
        if (notice == null) {
            return;
        }

        logger.debug("Received notice message: {}.", notice);

        // dispatch to notice handlers
        for (Consumer<NoticeData> handler : noticeHandlers) {
            try {
                handler.accept(notice);
            } catch (Exception e) {
                logger.error("Error in notice handler, type={}.", notice.getType(), e);
            }
        }
    }

    private NoticeData decodeNotice(MqttPublish in) {
        try {
            byte[] payload = in.getPayloadAsBytes();
            if (payload == null || payload.length == 0) {
                logger.warn("Received empty notice message.");
                return null;
            }
            String json = new String(payload, StandardCharsets.UTF_8);
            return gson.fromJson(json, NoticeData.class);
        } catch (Exception e) {
            logger.error("Failed to decode notice message.", e);
            return null;
        }
    }
}
