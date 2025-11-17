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
package com.webull.openapi.data;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.core.common.Headers;
import com.webull.openapi.core.common.Versions;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.http.HttpRequest;
import com.webull.openapi.core.http.common.HttpMethod;
import com.webull.openapi.core.http.initializer.ClientInitializer;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.data.common.ArgNames;
import com.webull.openapi.data.quotes.api.IDataClient;
import com.webull.openapi.data.quotes.domain.Bar;
import com.webull.openapi.data.quotes.domain.BatchBarResponse;
import com.webull.openapi.data.quotes.domain.CorpAction;
import com.webull.openapi.data.quotes.domain.CorpActionRequest;
import com.webull.openapi.data.quotes.domain.EodBars;
import com.webull.openapi.data.quotes.domain.Instrument;
import com.webull.openapi.data.quotes.domain.Quote;
import com.webull.openapi.data.quotes.domain.Snapshot;
import com.webull.openapi.data.quotes.domain.Tick;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class DataClient implements IDataClient {

    private static final String NOT_SUPPORT_MSG = "Http client not support for this method, please use default grpc client.";

    private final HttpApiClient apiClient;
    private String userId;

    public DataClient(HttpApiConfig config) {
        this.apiClient = new HttpApiClient(config);
        ClientInitializer.init(apiClient);
    }

    public DataClient(HttpApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    /**
     * Set user ID which will be added as x-user-id header to all requests
     * @param userId user ID
     * @return current client instance
     */
    public DataClient setUserId(String userId) {
        this.userId = userId;
        return this;
    }
    
    /**
     * Add custom headers to the request
     * @param request HTTP request
     */
    private void addCustomHeaders(HttpRequest request) {
        if (StringUtils.isNotEmpty(this.userId)) {
            request.getHeaders().put(Headers.USER_ID_KEY, this.userId);
        }
    }

    @Override
    public List<Instrument> getInstruments(Set<String> symbols, String category) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/instrument/stock/list", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<Instrument>>() {}.getType()).doAction();
    }

    @Override
    public List<Bar> getBars(String symbol, String category, String timespan, int count) {
        return getBars(symbol, category, timespan, count, null, null);
    }

    @Override
    public BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count) {
        return getBatchBars(symbols, category, timespan, count, null, null);
    }

    @Override
    public List<Bar> getBars(String symbol, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions) {
        Assert.notBlank(Arrays.asList(ArgNames.SYMBOL, ArgNames.CATEGORY, ArgNames.TIMESPAN), symbol, category, timespan);
        HttpRequest request = new HttpRequest("/openapi/market-data/stock/bars", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.TIMESPAN, timespan);
        params.put(ArgNames.COUNT, count);
        if(Objects.nonNull(realTimeRequired)){
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        if(CollectionUtils.isNotEmpty(tradingSessions)){
            params.put(ArgNames.TRADING_SESSIONS, String.join(",", tradingSessions));
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<Bar>>() {}.getType()).doAction();
    }

    @Override
    public BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        HttpRequest request = new HttpRequest("/openapi/market-data/stock/batch-bars", Versions.V2, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, symbols);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.TIMESPAN, timespan);
        params.put(ArgNames.COUNT, count);
        if(Objects.nonNull(realTimeRequired)){
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        if(CollectionUtils.isNotEmpty(tradingSessions)){
            params.put(ArgNames.TRADING_SESSIONS, String.join(",", tradingSessions));
        }
        request.setBody(params);
        addCustomHeaders(request);
        return apiClient.request(request)
                .responseType(new TypeToken<BatchBarResponse>() {}.getType())
                .doAction();
    }

    @Override
    public List<EodBars> getEodBars(Set<String> instrumentIds, String date, Integer count){
        Assert.notEmpty(ArgNames.INSTRUMENT_IDS, instrumentIds);
        HttpRequest request = new HttpRequest("/market-data/eod-bars", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.INSTRUMENT_IDS, String.join(",", instrumentIds));
        if(StringUtils.isNotEmpty(date)){
            params.put(ArgNames.DATE, date);
        }
        params.put(ArgNames.COUNT, count == null ? 1 : count);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<EodBars>>() {}.getType()).doAction();
    }

    @Override
    public List<CorpAction> getCorpAction(CorpActionRequest action){
        HttpRequest request = new HttpRequest("/instrument/corp-action", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        Assert.notEmpty(ArgNames.EVENT_TYPES, action.getEventTypes());
        params.put(ArgNames.EVENT_TYPES, String.join(",", action.getEventTypes()));
        Optional.ofNullable(action.getInstrumentIds()).filter(StringUtils::isNotEmpty).ifPresent(instrumentIds -> params.put(ArgNames.INSTRUMENT_IDS, String.join(",", instrumentIds)));
        Optional.ofNullable(action.getStartDate()).filter(StringUtils::isNotEmpty).ifPresent(startDate -> params.put(ArgNames.START_DATE, startDate));
        Optional.ofNullable(action.getEndDate()).filter(StringUtils::isNotEmpty).ifPresent(endDate -> params.put(ArgNames.END_DATE, endDate));
        Optional.ofNullable(action.getLastUpdateTime()).filter(StringUtils::isNotEmpty).ifPresent(lastUpdateTime -> params.put(ArgNames.LAST_UPDATE_TIME, lastUpdateTime));
        Optional.ofNullable(action.getPageNumber()).filter(StringUtils::isNotEmpty).ifPresent(pageNumber -> params.put(ArgNames.PAGE_NUMBER, pageNumber));
        Optional.ofNullable(action.getPageSize()).filter(StringUtils::isNotEmpty).ifPresent(pageSize -> params.put(ArgNames.PAGE_SIZE, pageSize));
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<CorpAction>>() {}.getType()).doAction();
    }

    @Override
    public Quote getQuote(String symbol, String category, String depth, Boolean overnightRequired) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/stock/quotes", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if(StringUtils.isNotBlank(depth)){
            params.put(ArgNames.DEPTH, depth);
        }
        if(Objects.nonNull(overnightRequired)){
            params.put(ArgNames.OVERNIGHT_REQUIRED, overnightRequired);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<Quote>() {}.getType()).doAction();
    }

    @Override
    public List<Snapshot> getSnapshots(Set<String> symbols, String category, Boolean extendHourRequired, Boolean overnightRequired) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/stock/snapshot", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        if(Objects.nonNull(extendHourRequired)){
            params.put(ArgNames.EXTEND_HOUR_REQUIRED, extendHourRequired);
        }
        if(Objects.nonNull(overnightRequired)){
            params.put(ArgNames.OVERNIGHT_REQUIRED, overnightRequired);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<Snapshot>>() {}.getType()).doAction();
    }

    @Override
    public Tick getTicks(String symbol, String category, int count, List<String> tradingSessions) {
        Assert.notBlank(Arrays.asList(ArgNames.SYMBOL, ArgNames.CATEGORY), symbol, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/stock/tick", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.COUNT, count);
        if(CollectionUtils.isNotEmpty(tradingSessions)){
            params.put(ArgNames.TRADING_SESSIONS, String.join(",", tradingSessions));
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<Tick>() {}.getType()).doAction();
    }

}
