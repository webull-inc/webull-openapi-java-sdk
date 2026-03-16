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
import com.webull.openapi.data.quotes.domain.EventTick;
import com.webull.openapi.data.quotes.subscribe.exception.DecoderException;
import com.webull.openapi.data.quotes.subscribe.message.QuotesPublish;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class EventTickDecoder implements QuotesPublishDecoder<EventTick> {

    @Override
    public QuotesPublish<EventTick> decode(ByteBuffer in) throws DecoderException {
        try {
            Quotes.EventTick from = Quotes.EventTick.parseFrom(in);
            EventTick eventTick = new EventTick();
            eventTick.setSymbol(from.getBasic().getSymbol());
            eventTick.setInstrumentId(from.getBasic().getInstrumentId());

            List<EventTick.Tick> tickRecordList = new ArrayList<>();
            EventTick.Tick tickRecord = new EventTick.Tick();
            tickRecord.setTime(from.getTime());
            tickRecord.setYesPrice(from.getYesPrice());
            tickRecord.setNoPrice(from.getNoPrice());
            tickRecord.setVolume(from.getVolume());
            tickRecord.setSide(from.getSide());
            tickRecord.setTradeId(from.getTradeId());
            tickRecordList.add(tickRecord);
            eventTick.setResult(tickRecordList);

            return new QuotesPublish<>(from.getBasic().getTimestamp(), eventTick);
        } catch (InvalidProtocolBufferException e) {
            throw new DecoderException("Decode event tick data error", e);
        }
    }
}
