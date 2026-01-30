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

import com.webull.openapi.core.common.dict.SubscribeType;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.internal.mqtt.message.MqttPublish;
import com.webull.openapi.data.quotes.domain.QuotesBasic;
import com.webull.openapi.data.quotes.subsribe.codec.AbstractInboundDecoder;
import com.webull.openapi.data.quotes.subsribe.message.MarketData;
import com.webull.openapi.data.quotes.subsribe.message.Metadata;
import com.webull.openapi.data.quotes.subsribe.message.QuotesPublish;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PublishToMarketDataDecoder extends AbstractInboundDecoder<MqttPublish, MarketData> {

    private static final Logger logger = LoggerFactory.getLogger(PublishToMarketDataDecoder.class);

    private final Map<SubscribeType, QuotesPublishDecoder<? extends QuotesBasic>> delegates = new EnumMap<>(SubscribeType.class);

    public PublishToMarketDataDecoder() {
        delegates.put(SubscribeType.QUOTE, new QuoteDecoder());
        delegates.put(SubscribeType.SNAPSHOT, new SnapshotDecoder());
        delegates.put(SubscribeType.TICK, new TickDecoder());
        delegates.put(SubscribeType.EVENT_QUOTE, new EventDepthDecoder());
        delegates.put(SubscribeType.EVENT_SNAPSHOT, new EventSnapshotDecoder());
    }

    @Override
    public MarketData decode(MqttPublish in) {
        String topic = in.getTopic();
        List<String> notDecodeTopic = new ArrayList<>();
        notDecodeTopic.add("echo");
        notDecodeTopic.add("notice");
        if (notDecodeTopic.contains(topic)) {
            logger.debug("No decoding is required, the type is {}.", topic);
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
}
