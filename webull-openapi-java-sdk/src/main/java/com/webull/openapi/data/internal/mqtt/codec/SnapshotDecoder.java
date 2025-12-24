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
import com.webull.openapi.data.quotes.domain.Snapshot;
import com.webull.openapi.data.quotes.subsribe.exception.DecoderException;
import com.webull.openapi.data.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;

public class SnapshotDecoder implements QuotesPublishDecoder<Snapshot> {

    @Override
    public QuotesPublish<Snapshot> decode(ByteBuffer in) {
        try {
            Quotes.Snapshot from = Quotes.Snapshot.parseFrom(in);
            Snapshot snapshot = new Snapshot();
            snapshot.setSymbol(from.getBasic().getSymbol());
            snapshot.setInstrumentId(from.getBasic().getInstrumentId());
            snapshot.setLastTradeTime(from.getTradeTime().isEmpty() ? null : Long.parseLong(from.getTradeTime()));
            snapshot.setPrice(from.getPrice());
            snapshot.setOpen(from.getOpen());
            snapshot.setHigh(from.getHigh());
            snapshot.setLow(from.getLow());
            snapshot.setPreClose(from.getPreClose());
            snapshot.setClose(from.getPrice());
            snapshot.setVolume(from.getVolume());
            snapshot.setChange(from.getChange());
            snapshot.setChangeRatio(from.getChangeRatio());
            snapshot.setTradingSession(from.getBasic().getTradingSession());
            snapshot.setExtendHourLastTradeTime(from.getExtTradeTime().isEmpty() ? null : Long.parseLong(from.getExtTradeTime()));
            snapshot.setExtendHourLastPrice(from.getExtPrice());
            snapshot.setExtendHourHigh(from.getExtHigh());
            snapshot.setExtendHourLow(from.getExtLow());
            snapshot.setExtendHourVolume(from.getExtVolume());
            snapshot.setExtendHourChange(from.getExtChange());
            snapshot.setExtendHourChangeRatio(from.getExtChangeRatio());
            snapshot.setOvnLastTradeTime(from.getOvnTradeTime().isEmpty() ? null : Long.parseLong(from.getOvnTradeTime()));
            snapshot.setOvnPrice(from.getOvnPrice());
            snapshot.setOvnHigh(from.getOvnHigh());
            snapshot.setOvnLow(from.getOvnLow());
            snapshot.setOvnVolume(from.getOvnVolume());
            snapshot.setOvnChange(from.getOvnChange());
            snapshot.setOvnChangeRatio(from.getOvnChangeRatio());
            return new QuotesPublish<>(from.getBasic().getTimestamp(), snapshot);
        } catch (InvalidProtocolBufferException e) {
            throw new DecoderException("Decode snapshot data error", e);
        }
    }
}
