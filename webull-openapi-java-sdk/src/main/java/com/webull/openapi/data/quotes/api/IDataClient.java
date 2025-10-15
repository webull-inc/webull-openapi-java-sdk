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
package com.webull.openapi.data.quotes.api;

import com.webull.openapi.data.quotes.domain.Bar;
import com.webull.openapi.data.quotes.domain.BatchBarResponse;
import com.webull.openapi.data.quotes.domain.CorpAction;
import com.webull.openapi.data.quotes.domain.CorpActionRequest;
import com.webull.openapi.data.quotes.domain.EodBars;
import com.webull.openapi.data.quotes.domain.Instrument;
import com.webull.openapi.data.quotes.domain.Quote;
import com.webull.openapi.data.quotes.domain.Snapshot;
import com.webull.openapi.data.quotes.domain.Tick;

import java.util.List;
import java.util.Set;

public interface IDataClient {

    List<Instrument> getInstruments(Set<String> symbols, String category);

    default List<Bar> getBars(String symbol, String category, String timespan) {
        return getBars(symbol, category, timespan, 200);
    }

    List<Bar> getBars(String symbol, String category, String timespan, int count);

    /**
     * Get batch bars for multiple symbols.
     */
    BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count);

    List<Bar> getBars(String symbol, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions);

    /**
     * Get batch bars for multiple symbols.
     */
    BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions);

    /**
     * Only for Webull JP
     */
    List<EodBars> getEodBars(Set<String> instrumentIds, String date, Integer count);

    /**
     * Only for Webull JP
     */
    List<CorpAction> getCorpAction(CorpActionRequest action);

    Quote getQuote(String symbol, String category, String depth, Boolean overnightRequired);

    List<Snapshot> getSnapshots(Set<String> symbols, String category, Boolean extendHourRequired, Boolean overnightRequired);

    default Tick getTicks(String symbol, String category) {
        return getTicks(symbol, category, 30, null);
    }

    Tick getTicks(String symbol, String category, int count, List<String> tradingSessions);
}
