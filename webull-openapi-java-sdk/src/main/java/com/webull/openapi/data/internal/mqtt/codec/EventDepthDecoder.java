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

import com.google.protobuf.InvalidProtocolBufferException;
import com.webull.openapi.data.quotes.domain.*;
import com.webull.openapi.data.quotes.subsribe.exception.DecoderException;
import com.webull.openapi.data.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

public class EventDepthDecoder implements QuotesPublishDecoder<EventDepth> {

    @Override
    public QuotesPublish<EventDepth> decode(ByteBuffer in) {
        try {
            Quotes.EventQuote from = Quotes.EventQuote.parseFrom(in);
            EventDepth snapshot = new EventDepth();
            snapshot.setInstrumentId(from.getBasic().getInstrumentId());
            snapshot.setSymbol(from.getBasic().getSymbol());
            snapshot.setYesBids(from.getYesBidsList().stream().map(this::assembleAskBid).collect(Collectors.toList()));
            snapshot.setNoBids(from.getNoBidsList().stream().map(this::assembleAskBid).collect(Collectors.toList()));
            return new QuotesPublish<>(from.getBasic().getTimestamp(), snapshot);
        } catch (InvalidProtocolBufferException e) {
            throw new DecoderException("Decode event snapshot data error", e);
        }
    }

    private EventDepth.AskBid assembleAskBid(Quotes.EventAskBid from) {
        EventDepth.AskBid result = new EventDepth.AskBid();
        result.setPrice(from.getPrice());
        result.setSize(from.getSize());
        return result;
    }
}
