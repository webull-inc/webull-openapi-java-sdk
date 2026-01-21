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
import com.webull.openapi.data.quotes.domain.EventSnapshot;
import com.webull.openapi.data.quotes.domain.Snapshot;
import com.webull.openapi.data.quotes.subsribe.exception.DecoderException;
import com.webull.openapi.data.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;

public class EventSnapshotDecoder implements QuotesPublishDecoder<EventSnapshot> {

    @Override
    public QuotesPublish<EventSnapshot> decode(ByteBuffer in) {
        try {
            Quotes.EventSnapshot from = Quotes.EventSnapshot.parseFrom(in);
            EventSnapshot snapshot = new EventSnapshot();
            snapshot.setInstrumentId(from.getBasic().getInstrumentId());
            snapshot.setSymbol(from.getBasic().getSymbol());
            snapshot.setPrice(from.getPrice());
            snapshot.setVolume(from.getVolume());
            snapshot.setLastTradeTime(from.getLastTradeTime().isEmpty() ? null : Long.parseLong(from.getLastTradeTime()));
            snapshot.setOpenInterest(from.getOpenInterest());
            snapshot.setYesBid(from.getYesBid());
            snapshot.setYesBidSize(from.getYesBidSize());
            snapshot.setYesAsk(from.getYesAsk());
            snapshot.setYesAskSize(from.getYesAskSize());
            snapshot.setNoBid(from.getNoBid());
            snapshot.setNoBidSize(from.getNoBidSize());
            snapshot.setNoAsk(from.getNoAsk());
            snapshot.setNoAskSize(from.getNoAskSize());
            return new QuotesPublish<>(from.getBasic().getTimestamp(), snapshot);
        } catch (InvalidProtocolBufferException e) {
            throw new DecoderException("Decode event snapshot data error", e);
        }
    }
}
