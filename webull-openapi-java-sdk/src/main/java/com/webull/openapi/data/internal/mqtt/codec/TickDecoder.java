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
import com.webull.openapi.data.quotes.domain.Tick;
import com.webull.openapi.data.quotes.domain.TickRecord;
import com.webull.openapi.data.quotes.subsribe.exception.DecoderException;
import com.webull.openapi.data.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TickDecoder implements QuotesPublishDecoder<Tick> {

    @Override
    public QuotesPublish<Tick> decode(ByteBuffer in) throws DecoderException {
        try {
            Quotes.Tick from = Quotes.Tick.parseFrom(in);
            Tick tick = new Tick();
            tick.setSymbol(from.getBasic().getSymbol());
            tick.setInstrumentId(from.getBasic().getInstrumentId());

            List<TickRecord> tickRecordList = new ArrayList<>();
            TickRecord tickRecord = new TickRecord();
            tickRecord.setTime(from.getTime());
            tickRecord.setPrice(from.getPrice());
            tickRecord.setVolume(from.getVolume());
            tickRecord.setSide(from.getSide());
            tickRecord.setTradingSession(from.getBasic().getTradingSession());
            tickRecordList.add(tickRecord);
            tick.setResult(tickRecordList);

            return new QuotesPublish<>(from.getBasic().getTimestamp(), tick);
        } catch (InvalidProtocolBufferException e) {
            throw new DecoderException("Decode tick data error", e);
        }
    }
}
