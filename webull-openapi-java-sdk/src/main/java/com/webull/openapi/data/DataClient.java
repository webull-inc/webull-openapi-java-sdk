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
import com.webull.openapi.data.quotes.domain.*;
import com.webull.openapi.trade.response.PaginatedResult;

import java.util.*;

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
        return getBars(symbol, category, timespan, count, realTimeRequired, tradingSessions, null, null);
    }

    /**
     * @deprecated The endpoint /market-data/stocks/bars/get is no longer available.
     * This method now delegates to {@link #getBatchBars} which calls the batch
     * endpoint /market-data/stocks/bars/list. Prefer getBatchBars directly.
     */
    @Deprecated
    @Override
    public List<Bar> getBars(String symbol, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions, Long startTime, Long endTime) {
        Assert.notBlank(Arrays.asList(ArgNames.SYMBOL, ArgNames.CATEGORY, ArgNames.TIMESPAN), symbol, category, timespan);
        BatchBarResponse response = getBatchBars(Collections.singletonList(symbol), category, timespan, count,
                realTimeRequired, tradingSessions, startTime, endTime);
        if (Objects.isNull(response) || CollectionUtils.isEmpty(response.getResult())) {
            return Collections.emptyList();
        }
        return response.getResult().stream()
                .filter(nBar -> symbol.equals(nBar.getSymbol()))
                .findFirst()
                .map(NBar::getResult)
                .orElse(Collections.emptyList());
    }

    @Override
    public BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions) {
        return getBatchBars(symbols, category, timespan, count, realTimeRequired, tradingSessions, null, null);
    }

    @Override
    public BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions, Long startTime, Long endTime) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        HttpRequest request = new HttpRequest("/market-data/stocks/bars/list", Versions.V3, HttpMethod.POST);
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
        if(Objects.nonNull(startTime)){
            params.put(ArgNames.START_TIME, startTime);
        }
        if(Objects.nonNull(endTime)){
            params.put(ArgNames.END_TIME, endTime);
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
        HttpRequest request = new HttpRequest("/market-data/eod-bars", Versions.V3, HttpMethod.GET);
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
        HttpRequest request = new HttpRequest("/instrument/corp-action", Versions.V3, HttpMethod.GET);
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
        HttpRequest request = new HttpRequest("/market-data/stocks/depths/list", Versions.V3, HttpMethod.GET);
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
        HttpRequest request = new HttpRequest("/market-data/stocks/snapshots/list", Versions.V3, HttpMethod.GET);
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
        HttpRequest request = new HttpRequest("/market-data/stocks/ticks/list", Versions.V3, HttpMethod.GET);
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

    @Override
    public List<FootprintResponse> getFootprint(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, String tradingSessions) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        HttpRequest request = new HttpRequest("/market-data/stocks/footprints/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.TIMESPAN, timespan);
        params.put(ArgNames.COUNT, count);
        if(Objects.nonNull(realTimeRequired)){
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        if(StringUtils.isNotEmpty(tradingSessions)){
            params.put(ArgNames.TRADING_SESSIONS, tradingSessions);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<FootprintResponse>>() {}.getType()).doAction();
    }

    @Override
    public List<NBar> getFuturesBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        HttpRequest request = new HttpRequest("/market-data/futures/bars/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.TIMESPAN, timespan);
        params.put(ArgNames.COUNT, count);
        if(Objects.nonNull(realTimeRequired)){
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<NBar>>() {}.getType()).doAction();
    }

    @Override
    public DepthOfBook getFuturesDepth(String symbol, String category, String depth) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/futures/depths/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if(StringUtils.isNotBlank(depth)){
            params.put(ArgNames.DEPTH, depth);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<DepthOfBook>() {}.getType()).doAction();
    }

    @Override
    public List<Snapshot> getFuturesSnapshots(Set<String> symbols, String category) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/futures/snapshots/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<Snapshot>>() {}.getType()).doAction();
    }

    @Override
    public Tick getFutureTicks(String symbol, String category, int count) {
        Assert.notBlank(Arrays.asList(ArgNames.SYMBOL, ArgNames.CATEGORY), symbol, category);
        HttpRequest request = new HttpRequest("/market-data/futures/ticks/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.COUNT, count);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<Tick>() {}.getType()).doAction();
    }

    @Override
    public List<OptionBars> getOptionBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        HttpRequest request = new HttpRequest("/market-data/options/bars/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.TIMESPAN, timespan);
        params.put(ArgNames.COUNT, count);
        if(Objects.nonNull(realTimeRequired)){
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<OptionBars>>() {}.getType()).doAction();
    }

    @Override
    public OptionTick getOptionTicks(String symbol, String category, int count) {
        Assert.notBlank(Arrays.asList(ArgNames.SYMBOL, ArgNames.CATEGORY), symbol, category);
        HttpRequest request = new HttpRequest("/market-data/options/ticks/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.COUNT, count);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<OptionTick>() {}.getType()).doAction();
    }

    @Override
    public List<OptionSnapshot> getOptionSnapshots(Set<String> symbols, String category) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/options/snapshots/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<OptionSnapshot>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<FuturesProduct> getFuturesProducts(String category) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/trading/instruments/futures/product-codes/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FuturesProduct>>() {}.getType()).doAction();
    }

    @Override
    public List<FuturesProduct> getFuturesProductsV2(String category, String productClassId) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/trading/instruments/futures/product-codes/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotEmpty(productClassId)) {
            params.put(ArgNames.PRODUCT_CLASS_ID, productClassId);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FuturesProduct>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<FuturesInstrument> getFuturesInstruments(Set<String> symbols, String category) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/trading/instruments/futures/contracts/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FuturesInstrument>>() {}.getType()).doAction();
    }

    @Override
    public List<FuturesInstrument> getFuturesInstrumentsV2(String category, Set<String> symbols, String code, String status) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/trading/instruments/futures/contracts/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (CollectionUtils.isNotEmpty(symbols)) {
            params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        }
        if (StringUtils.isNotEmpty(code)) {
            params.put(ArgNames.CODE, code);
        }
        if (StringUtils.isNotEmpty(status)) {
            params.put(ArgNames.STATUS, status);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FuturesInstrument>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<FuturesInstrument> getFuturesInstrumentsByCode(String code, String category, String contractType) {
        Assert.notBlank(Arrays.asList(ArgNames.CATEGORY, ArgNames.CODE), category, code);
        HttpRequest request = new HttpRequest("/openapi/instrument/futures/by-code", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CODE, code);
        params.put(ArgNames.CATEGORY, category);
        if (Objects.nonNull(contractType)) {
            params.put(ArgNames.TYPE, contractType);
        }

        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FuturesInstrument>>() {}.getType()).doAction();
    }

    @Override
    public List<FuturesProductClass> getFuturesProductClasses(String category) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/trading/instruments/futures/product-classes/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FuturesProductClass>>() {}.getType()).doAction();
    }

    @Override
    public List<FootprintResponse> getFuturesFootprint(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, String tradingSessions) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        HttpRequest request = new HttpRequest("/market-data/futures/footprints/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.TIMESPAN, timespan);
        params.put(ArgNames.COUNT, count);
        if(Objects.nonNull(realTimeRequired)){
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        if(StringUtils.isNotEmpty(tradingSessions)){
            params.put(ArgNames.TRADING_SESSIONS, tradingSessions);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<FootprintResponse>>() {}.getType()).doAction();
    }

    @Override
    public List<Snapshot> getCryptoSnapshots(Set<String> symbols, String category) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/crypto/snapshots/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<Snapshot>>() {}.getType()).doAction();
    }

    @Override
    public List<NBar> getCryptoBars(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(Arrays.asList(ArgNames.CATEGORY, ArgNames.TIMESPAN), category, timespan);
        HttpRequest request = new HttpRequest("/market-data/crypto/bars/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.TIMESPAN, timespan);
        params.put(ArgNames.COUNT, count);
        if(Objects.nonNull(realTimeRequired)){
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<NBar>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<StockInstrumentDetail> getInstrumentsV1(InstrumentQueryParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        String category = param.getCategory();
        Set<String> symbols = param.getSymbols();
        String status = param.getStatus();
        String lastInstrumentId = param.getLastInstrumentId();
        int pageSize = param.getPageSize();
        String subCategory = param.getSubCategory();
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/instrument/stock/list", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if(CollectionUtils.isNotEmpty(symbols)){
            params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        }
        params.put(ArgNames.CATEGORY, category);
        if(StringUtils.isNotEmpty(status)){
            params.put(ArgNames.STATUS, status);
        }
        if(StringUtils.isNotEmpty(subCategory)){
            params.put(ArgNames.SUB_CATEGORY, subCategory);
        }
        if(StringUtils.isNotEmpty(lastInstrumentId)){
            params.put(ArgNames.LAST_INSTRUMENT_ID, lastInstrumentId);
        }
        params.put(ArgNames.PAGE_SIZE, pageSize);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<StockInstrumentDetail>>() {}.getType()).doAction();
    }

    @Override
    public PaginatedResult<StockInstrumentDetail> getInstrumentsV2(InstrumentQueryParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        Assert.notBlank(ArgNames.CATEGORY, param.getCategory());
        HttpRequest request = new HttpRequest("/trading/instruments/stocks/profiles/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, param.getCategory());
        if (CollectionUtils.isNotEmpty(param.getSymbols())) {
            params.put(ArgNames.SYMBOLS, String.join(",", param.getSymbols()));
        }
        if (StringUtils.isNotEmpty(param.getStatus())) {
            params.put(ArgNames.STATUS, param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getSubCategory())) {
            params.put(ArgNames.SUB_CATEGORY, param.getSubCategory());
        }
        if (StringUtils.isNotEmpty(param.getPaginationKey())) {
            params.put(ArgNames.PAGINATION_KEY, param.getPaginationKey());
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<PaginatedResult<StockInstrumentDetail>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<CryptoInstrumentDetail> getCryptoInstrument(InstrumentQueryParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        String category = param.getCategory();
        Set<String> symbols = param.getSymbols();
        String status = param.getStatus();
        String lastInstrumentId = param.getLastInstrumentId();
        int pageSize = param.getPageSize();
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/instrument/crypto/list", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if(CollectionUtils.isNotEmpty(symbols)){
            params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        }
        if(StringUtils.isNotEmpty(status)){
            params.put(ArgNames.STATUS, status);
        }
        if(StringUtils.isNotEmpty(lastInstrumentId)){
            params.put(ArgNames.LAST_INSTRUMENT_ID, lastInstrumentId);
        }
        params.put(ArgNames.PAGE_SIZE, pageSize);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<CryptoInstrumentDetail>>() {}.getType()).doAction();
    }

    @Override
    public PaginatedResult<CryptoInstrumentDetail> getCryptoInstrumentV2(InstrumentQueryParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        Assert.notBlank(ArgNames.CATEGORY, param.getCategory());
        HttpRequest request = new HttpRequest("/trading/instruments/crypto/profiles/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, param.getCategory());
        if (CollectionUtils.isNotEmpty(param.getSymbols())) {
            params.put(ArgNames.SYMBOLS, String.join(",", param.getSymbols()));
        }
        if (StringUtils.isNotEmpty(param.getStatus())) {
            params.put(ArgNames.STATUS, param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getPaginationKey())) {
            params.put(ArgNames.PAGINATION_KEY, param.getPaginationKey());
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<PaginatedResult<CryptoInstrumentDetail>>() {}.getType()).doAction();
    }

    @Override
    public List<EventCategories> getEventCategories() {
        HttpRequest request = new HttpRequest("/trading/instruments/event-contracts/categories/list", Versions.V3, HttpMethod.GET);
        return apiClient.request(request).responseType(new TypeToken<List<EventCategories>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<EventSeries> getEventSeriesList(String category, Set<String> symbols, String lastSeriesId, int pageSize) {
        HttpRequest request = new HttpRequest("/openapi/instrument/event/series/list", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(category)){
            params.put(ArgNames.CATEGORY, category);
        }
        if (CollectionUtils.isNotEmpty(symbols)) {
            params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        }
        if (StringUtils.isNotEmpty(lastSeriesId)){
            params.put(ArgNames.LAST_SERIES_ID, lastSeriesId);
        }
        if (pageSize > 0){
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<EventSeries>>() {}.getType()).doAction();
    }

    @Override
    public PaginatedResult<EventSeries> getEventSeriesList(String category, Set<String> symbols, String paginationKey) {
        HttpRequest request = new HttpRequest("/trading/instruments/event-contracts/series/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(category)) {
            params.put(ArgNames.CATEGORY, category);
        }
        if (CollectionUtils.isNotEmpty(symbols)) {
            params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        }
        if (StringUtils.isNotEmpty(paginationKey)) {
            params.put(ArgNames.PAGINATION_KEY, paginationKey);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<PaginatedResult<EventSeries>>() {}.getType()).doAction();
    }

    @Override
    public List<EventEvents> getEventEvents(String seriesSymbol, Set<String> symbols, String status) {
        Assert.notBlank(ArgNames.SERIES_SYMBOL, seriesSymbol);
        HttpRequest request = new HttpRequest("/trading/instruments/event-contracts/events/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SERIES_SYMBOL, seriesSymbol);
        if (CollectionUtils.isNotEmpty(symbols)) {
            params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        }
        if (StringUtils.isNotEmpty(status)) {
            params.put(ArgNames.STATUS, status);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<EventEvents>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<EventMarket> getEventInstrumentsList(EventInstrumentParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        Assert.notBlank(ArgNames.SERIES_SYMBOL, param.getSeriesSymbol());
        HttpRequest request = new HttpRequest("/openapi/instrument/event/market/list", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SERIES_SYMBOL, param.getSeriesSymbol());
        if (StringUtils.isNotEmpty(param.getEventSymbol())) {
            params.put(ArgNames.EVNET_SYMBOL, param.getEventSymbol());
        }
        if (CollectionUtils.isNotEmpty(param.getSymbols())) {
            params.put(ArgNames.SYMBOLS, String.join(",", param.getSymbols()));
        }
        if (StringUtils.isNotEmpty(param.getExpirationDateAfter())) {
            params.put(ArgNames.EXPIRATION_DATE_AFTER, param.getExpirationDateAfter());
        }
        if (StringUtils.isNotEmpty(param.getLastInstrumentId())) {
            params.put(ArgNames.LAST_INSTRUMENT_ID, param.getLastInstrumentId());
        }
        params.put(ArgNames.PAGE_SIZE, param.getPageSize());
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<EventMarket>>() {}.getType()).doAction();
    }

    @Override
    public PaginatedResult<EventMarket> getEventInstrumentsListV1(EventInstrumentParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        Assert.notBlank(ArgNames.SERIES_SYMBOL, param.getSeriesSymbol());
        HttpRequest request = new HttpRequest("/trading/instruments/event-contracts/markets/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SERIES_SYMBOL, param.getSeriesSymbol());
        if (StringUtils.isNotEmpty(param.getEventSymbol())) {
            params.put(ArgNames.EVNET_SYMBOL, param.getEventSymbol());
        }
        if (CollectionUtils.isNotEmpty(param.getSymbols())) {
            params.put(ArgNames.SYMBOLS, String.join(",", param.getSymbols()));
        }
        if (StringUtils.isNotEmpty(param.getExpirationDateAfter())) {
            params.put(ArgNames.EXPIRATION_DATE_AFTER, param.getExpirationDateAfter());
        }
        if (StringUtils.isNotEmpty(param.getPaginationKey())) {
            params.put(ArgNames.PAGINATION_KEY, param.getPaginationKey());
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<PaginatedResult<EventMarket>>() {}.getType()).doAction();
    }

    @Override
    public List<EventSnapshot> getEventSnapshot(Set<String> symbols, String category) {
        Assert.notNull(ArgNames.SYMBOLS, symbols);
        HttpRequest request = new HttpRequest("/market-data/event-contracts/snapshots/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if(CollectionUtils.isNotEmpty(symbols)){
            params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        }
        if(StringUtils.isNotBlank(category)){
            params.put(ArgNames.CATEGORY, category);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<EventSnapshot>>() {}.getType()).doAction();
    }

    @Override
    public EventDepth getEventDepth(String symbol, String category, String depth) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        HttpRequest request = new HttpRequest("/market-data/event-contracts/depths/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        if(StringUtils.isNotBlank(category)){
            params.put(ArgNames.CATEGORY, category);
        }
        if(StringUtils.isNotBlank(depth)){
            params.put(ArgNames.DEPTH, depth);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<EventDepth>() {}.getType()).doAction();
    }

    @Override
    public List<EventBars> getEventBars(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired) {
        Assert.notNull(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        HttpRequest request = new HttpRequest("/market-data/event-contracts/bars/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOLS, String.join(",", symbols));
        params.put(ArgNames.TIMESPAN, timespan);
        if (StringUtils.isNotBlank(category)) {
            params.put(ArgNames.CATEGORY, category);
        }
        if (count > 0) {
            params.put(ArgNames.COUNT, count);
        }
        if (Objects.nonNull(realTimeRequired)) {
            params.put(ArgNames.REAL_TIME_REQUIRED, realTimeRequired);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<EventBars>>() {}.getType()).doAction();
    }

    @Override
    public EventTick getEventTick(String symbol, String category, int count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        HttpRequest request = new HttpRequest("/market-data/event-contracts/ticks/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        if (StringUtils.isNotBlank(category)) {
            params.put(ArgNames.CATEGORY, category);
        }
        if (count > 0) {
            params.put(ArgNames.COUNT, count);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<EventTick>() {}.getType()).doAction();
    }

    // ==================== Company Profile & Analyst APIs ====================

    @Override
    public CompanyProfile getCompanyProfile(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/company-profiles/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<CompanyProfile>() {}.getType()).doAction();
    }

    @Override
    public AnalystTargetPrice getAnalystTargetPrice(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/analysis/target-prices/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<AnalystTargetPrice>() {}.getType()).doAction();
    }

    @Override
    public AnalystRating getAnalystRating(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/analysis/ratings/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<AnalystRating>() {}.getType()).doAction();
    }

    // ==================== Watchlist APIs ====================

    @Override
    public List<Watchlist> getWatchlists() {
        HttpRequest request = new HttpRequest("/market-data/watchlists/list", Versions.V3, HttpMethod.GET);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<Watchlist>>() {}.getType()).doAction();
    }

    @Override
    public WatchlistCreateResponse createWatchlist(String name, Integer sort) {
        Assert.notBlank(ArgNames.NAME, name);
        HttpRequest request = new HttpRequest("/market-data/watchlists/create", Versions.V3, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.NAME, name);
        if (Objects.nonNull(sort)) {
            params.put(ArgNames.SORT, sort);
        }
        request.setBody(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<WatchlistCreateResponse>() {}.getType()).doAction();
    }

    @Override
    public void updateWatchlist(String watchlistId, String name, Integer sort) {
        Assert.notBlank(ArgNames.WATCHLIST_ID, watchlistId);
        HttpRequest request = new HttpRequest("/market-data/watchlists/update", Versions.V3, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.WATCHLIST_ID, watchlistId);
        if (StringUtils.isNotBlank(name)) {
            params.put(ArgNames.NAME, name);
        }
        if (Objects.nonNull(sort)) {
            params.put(ArgNames.SORT, sort);
        }
        request.setBody(params);
        addCustomHeaders(request);
        apiClient.request(request).responseType(new TypeToken<Boolean>() {}.getType()).doAction();
    }

    @Override
    public void deleteWatchlist(String watchlistId) {
        Assert.notBlank(ArgNames.WATCHLIST_ID, watchlistId);
        HttpRequest request = new HttpRequest("/market-data/watchlists/delete", Versions.V3, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.WATCHLIST_ID, watchlistId);
        request.setBody(params);
        addCustomHeaders(request);
        apiClient.request(request).responseType(new TypeToken<Boolean>() {}.getType()).doAction();
    }

    @Override
    public WatchlistInstrumentsResponse getWatchlistInstruments(String watchlistId) {
        Assert.notBlank(ArgNames.WATCHLIST_ID, watchlistId);
        HttpRequest request = new HttpRequest("/market-data/watchlists/instruments/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.WATCHLIST_ID, watchlistId);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<WatchlistInstrumentsResponse>() {}.getType()).doAction();
    }

    @Override
    public void addWatchlistInstruments(String watchlistId, List<WatchlistInstrumentParam> instruments) {
        Assert.notBlank(ArgNames.WATCHLIST_ID, watchlistId);
        Assert.notEmpty(ArgNames.INSTRUMENTS, instruments);
        HttpRequest request = new HttpRequest("/market-data/watchlists/instruments/add", Versions.V3, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.WATCHLIST_ID, watchlistId);
        params.put(ArgNames.INSTRUMENTS, instruments);
        request.setBody(params);
        addCustomHeaders(request);
        apiClient.request(request).responseType(new TypeToken<Boolean>() {}.getType()).doAction();
    }

    @Override
    public void removeWatchlistInstruments(String watchlistId, List<WatchlistInstrumentParam> instruments) {
        Assert.notBlank(ArgNames.WATCHLIST_ID, watchlistId);
        Assert.notEmpty(ArgNames.INSTRUMENTS, instruments);
        HttpRequest request = new HttpRequest("/market-data/watchlists/instruments/remove", Versions.V3, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.WATCHLIST_ID, watchlistId);
        params.put(ArgNames.INSTRUMENTS, instruments);
        request.setBody(params);
        addCustomHeaders(request);
        apiClient.request(request).responseType(new TypeToken<Boolean>() {}.getType()).doAction();
    }

    @Override
    public void updateWatchlistInstruments(String watchlistId, List<WatchlistInstrumentParam> instruments) {
        Assert.notBlank(ArgNames.WATCHLIST_ID, watchlistId);
        Assert.notEmpty(ArgNames.INSTRUMENTS, instruments);
        HttpRequest request = new HttpRequest("/market-data/watchlists/instruments/update", Versions.V3, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.WATCHLIST_ID, watchlistId);
        params.put(ArgNames.INSTRUMENTS, instruments);
        request.setBody(params);
        addCustomHeaders(request);
        apiClient.request(request).responseType(new TypeToken<Boolean>() {}.getType()).doAction();
    }

    // ==================== Fundamentals APIs ====================

    @Override
    public List<CapitalFlow> getCapitalFlow(String symbol, String category, Integer count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/capital-flows/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (Objects.nonNull(count)) {
            params.put(ArgNames.COUNT, count);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<CapitalFlow>>() {}.getType()).doAction();
    }

    @Override
    public IndustryComparison getIndustryComparison(String symbol, String category, String sortBy) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/industry-comparisons/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<IndustryComparison>() {}.getType()).doAction();
    }

    @Override
    public SecFilings getSecFilings(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/filings/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<SecFilings>() {}.getType()).doAction();
    }

    @Override
    public List<EarningsCalendar> getEarningsCalendar(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/earnings-calendars/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<EarningsCalendar>>() {}.getType()).doAction();
    }

    @Override
    public List<DividendCalendar> getDividendCalendar(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/dividend-calendars/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<DividendCalendar>>() {}.getType()).doAction();
    }

    @Override
    public List<ForecastEps> getForecastEps(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/forecast-eps/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<ForecastEps>>() {}.getType()).doAction();
    }

    @Override
    public List<FundSplit> getFundSplits(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-splits/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FundSplit>>() {}.getType()).doAction();
    }

    @Override
    public List<FundRating> getFundRating(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-ratings/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FundRating>>() {}.getType()).doAction();
    }

    @Override
    public FundPerformance getFundPerformance(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-performances/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<FundPerformance>() {}.getType()).doAction();
    }

    @Override
    public List<FundNetValue> getFundNetValue(String symbol, String category, String lastDate, Integer count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-net-values/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(lastDate)) {
            params.put(ArgNames.LAST_DATE, lastDate);
        }
        if (Objects.nonNull(count)) {
            params.put(ArgNames.COUNT, count);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FundNetValue>>() {}.getType()).doAction();
    }

    @Override
    public List<FundHolding> getFundHoldings(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-holdings/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FundHolding>>() {}.getType()).doAction();
    }

    @Override
    public List<FundFile> getFundFiles(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-files/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FundFile>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<FundDividend> getFundDividends(String symbol, String category, Integer pageIndex, Integer pageSize) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/fundamentals/fund/dividends", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (Objects.nonNull(pageIndex)) {
            params.put(ArgNames.PAGE_INDEX, pageIndex);
        }
        if (Objects.nonNull(pageSize)) {
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FundDividend>>() {}.getType()).doAction();
    }

    @Override
    public PaginatedResult<FundDividend> getFundDividends(String symbol, String category, String paginationKey) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-dividends/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotEmpty(paginationKey)) {
            params.put(ArgNames.PAGINATION_KEY, paginationKey);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<PaginatedResult<FundDividend>>() {}.getType()).doAction();
    }

    @Override
    public FundBrief getFundBrief(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-brief/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<FundBrief>() {}.getType()).doAction();
    }

    @Override
    public List<FundAllocation> getFundAllocation(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/fund-allocations/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FundAllocation>>() {}.getType()).doAction();
    }

    @Override
    public FinancialIndicators getFinancialsIndicators(String symbol, String category, String type, Integer count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/indicators/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(type)) {
            params.put("type", type);
        }
        if (Objects.nonNull(count)) {
            params.put(ArgNames.COUNT, count);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<FinancialIndicators>() {}.getType()).doAction();
    }

    @Override
    public List<FinancialIncome> getFinancialsIncome(String symbol, String category, String type, Integer count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/income-statements/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(type)) {
            params.put("type", type);
        }
        if (Objects.nonNull(count)) {
            params.put(ArgNames.COUNT, count);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FinancialIncome>>() {}.getType()).doAction();
    }

    @Override
    public List<FinancialCashflow> getFinancialsCashflow(String symbol, String category, String type, Integer count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/cash-flows/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(type)) {
            params.put("type", type);
        }
        if (Objects.nonNull(count)) {
            params.put(ArgNames.COUNT, count);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FinancialCashflow>>() {}.getType()).doAction();
    }

    @Override
    public List<FinancialBalanceSheet> getFinancialsBalanceSheet(String symbol, String category, String type, Integer count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/balance-sheets/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(type)) {
            params.put("type", type);
        }
        if (Objects.nonNull(count)) {
            params.put(ArgNames.COUNT, count);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FinancialBalanceSheet>>() {}.getType()).doAction();
    }

    @Override
    public FinancialAlert getFinancialsAlert(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/fundamentals/financial-alerts/get", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<FinancialAlert>() {}.getType()).doAction();
    }

    // ==================== Screener APIs ====================

    @Override
    @Deprecated
    public ScreenerResponse getGainersLosers(String rankType, String category, String sortBy,
                                              Integer pageIndex, Integer pageSize, String direction) {
        Assert.notBlank(ArgNames.RANK_TYPE, rankType);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.SORT_BY, sortBy);
        HttpRequest request = new HttpRequest("/openapi/market-data/screener/gainers-losers", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.RANK_TYPE, rankType);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.SORT_BY, sortBy);
        if (Objects.nonNull(pageIndex)) {
            params.put(ArgNames.PAGE_INDEX, pageIndex);
        }
        if (Objects.nonNull(pageSize)) {
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<ScreenerResponse>() {}.getType()).doAction();
    }

    @Override
    public List<ScreenerStock> getGainersLosers(String rankType, String category, String sortBy, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/screeners/gainers-losers/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(rankType)) {
            params.put(ArgNames.RANK_TYPE, rankType);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<ScreenerStock>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public ScreenerResponse getMostActive(String category, String rankType, String sortBy,
                                           Integer pageIndex, Integer pageSize, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/screener/top-active", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(rankType)) {
            params.put(ArgNames.RANK_TYPE, rankType);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (Objects.nonNull(pageIndex)) {
            params.put(ArgNames.PAGE_INDEX, pageIndex);
        }
        if (Objects.nonNull(pageSize)) {
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<ScreenerResponse>() {}.getType()).doAction();
    }

    @Override
    public List<ScreenerStock> getMostActive(String category, String rankType, String sortBy, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/screeners/top-actives/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(rankType)) {
            params.put(ArgNames.RANK_TYPE, rankType);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<ScreenerStock>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public List<MarketSector> getMarketSectors(String category, String aggType, String period,
                                                   Integer pageIndex, Integer pageSize, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/screener/market-sectors", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(aggType)) {
            params.put(ArgNames.AGG_TYPE, aggType);
        }
        if (StringUtils.isNotBlank(period)) {
            params.put(ArgNames.PERIOD, period);
        }
        if (Objects.nonNull(pageIndex)) {
            params.put(ArgNames.PAGE_INDEX, pageIndex);
        }
        if (Objects.nonNull(pageSize)) {
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<MarketSector>>() {}.getType()).doAction();
    }

    @Override
    public PaginatedResult<MarketSector> getMarketSectors(String category, String aggType, String period,
                                                          String direction, String paginationKey) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/screeners/market-sectors/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(aggType)) {
            params.put(ArgNames.AGG_TYPE, aggType);
        }
        if (StringUtils.isNotBlank(period)) {
            params.put(ArgNames.PERIOD, period);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        if (StringUtils.isNotEmpty(paginationKey)) {
            params.put(ArgNames.PAGINATION_KEY, paginationKey);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<PaginatedResult<MarketSector>>() {}.getType()).doAction();
    }

    @Override
    public MarketSectorDetail getMarketSectorsDetail(String sectorId, String category, String period,
                                                    Integer pageIndex, Integer pageSize, String sortBy, String direction) {
        Assert.notBlank(ArgNames.SECTOR_ID, sectorId);
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/screener/market-sectors-detail", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SECTOR_ID, sectorId);
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(period)) {
            params.put(ArgNames.PERIOD, period);
        }
        if (Objects.nonNull(pageIndex)) {
            params.put(ArgNames.PAGE_INDEX, pageIndex);
        }
        if (Objects.nonNull(pageSize)) {
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<MarketSectorDetail>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public HighDividendResponse getHighDividend(String category, String sortBy,
                                             Integer pageIndex, Integer pageSize, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/screener/high-dividend", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (Objects.nonNull(pageIndex)) {
            params.put(ArgNames.PAGE_INDEX, pageIndex);
        }
        if (Objects.nonNull(pageSize)) {
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<HighDividendResponse>() {}.getType()).doAction();
    }

    @Override
    public List<HighDividendStock> getHighDividend(String category, String sortBy, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/screeners/high-dividend-ranks/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<HighDividendStock>>() {}.getType()).doAction();
    }

    @Override
    @Deprecated
    public FiftyTwoWeekResponse get52Whl(String rankType, String category, String sortBy,
                                      Integer pageIndex, Integer pageSize, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/openapi/market-data/screener/52whl", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(rankType)) {
            params.put(ArgNames.RANK_TYPE, rankType);
        }
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (Objects.nonNull(pageIndex)) {
            params.put(ArgNames.PAGE_INDEX, pageIndex);
        }
        if (Objects.nonNull(pageSize)) {
            params.put(ArgNames.PAGE_SIZE, pageSize);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<FiftyTwoWeekResponse>() {}.getType()).doAction();
    }

    @Override
    public List<FiftyTwoWeekStock> get52Whl(String rankType, String category, String sortBy, String direction) {
        Assert.notBlank(ArgNames.CATEGORY, category);
        HttpRequest request = new HttpRequest("/market-data/screeners/week52-high-low/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(rankType)) {
            params.put(ArgNames.RANK_TYPE, rankType);
        }
        params.put(ArgNames.CATEGORY, category);
        if (StringUtils.isNotBlank(sortBy)) {
            params.put(ArgNames.SORT_BY, sortBy);
        }
        if (StringUtils.isNotBlank(direction)) {
            params.put(ArgNames.DIRECTION, direction);
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<FiftyTwoWeekStock>>() {}.getType()).doAction();
    }

    // ==================== NOII APIs ====================

    @Override
    public List<NoiiBar> getNoiiBars(String symbol, String category, String imbalanceActionType) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.IMBALANCE_ACTION_TYPE, imbalanceActionType);
        HttpRequest request = new HttpRequest("/market-data/stocks/noii-bars/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.IMBALANCE_ACTION_TYPE, imbalanceActionType);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<NoiiBar>>() {}.getType()).doAction();
    }

    @Override
    public NoiiSnapshot getNoiiSnapshot(String symbol, String category, String imbalanceActionType) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.IMBALANCE_ACTION_TYPE, imbalanceActionType);
        HttpRequest request = new HttpRequest("/market-data/stocks/noii-snapshots/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.SYMBOL, symbol);
        params.put(ArgNames.CATEGORY, category);
        params.put(ArgNames.IMBALANCE_ACTION_TYPE, imbalanceActionType);
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<NoiiSnapshot>() {}.getType()).doAction();
    }


    // ==================== Option Instrument APIs ====================

    @Override
    @Deprecated
    public List<OptionContract> getOptionContracts(OptionContractQueryParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        Assert.notBlank(ArgNames.CATEGORY, param.getCategory());
        HttpRequest request = new HttpRequest("/openapi/instrument/option/contracts", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, param.getCategory());
        if (StringUtils.isNotEmpty(param.getUnderlyingSymbols())) {
            params.put(ArgNames.UNDERLYING_SYMBOLS, param.getUnderlyingSymbols());
        }
        if (StringUtils.isNotEmpty(param.getStatus())) {
            params.put(ArgNames.STATUS, param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getStartDate())) {
            params.put(ArgNames.START_DATE, param.getStartDate());
        }
        if (StringUtils.isNotEmpty(param.getEndDate())) {
            params.put(ArgNames.END_DATE, param.getEndDate());
        }
        if (StringUtils.isNotEmpty(param.getRootSymbol())) {
            params.put(ArgNames.ROOT_SYMBOL, param.getRootSymbol());
        }
        if (StringUtils.isNotEmpty(param.getOptionSymbol())) {
            params.put(ArgNames.OPTION_SYMBOL, param.getOptionSymbol());
        }
        if (StringUtils.isNotEmpty(param.getOptionType())) {
            params.put(ArgNames.OPTION_TYPE, param.getOptionType());
        }
        if (StringUtils.isNotEmpty(param.getStyle())) {
            params.put(ArgNames.STYLE, param.getStyle());
        }
        if (Objects.nonNull(param.getStrikePriceGte())) {
            params.put(ArgNames.STRIKE_PRICE_GTE, param.getStrikePriceGte());
        }
        if (Objects.nonNull(param.getStrikePriceLte())) {
            params.put(ArgNames.STRIKE_PRICE_LTE, param.getStrikePriceLte());
        }
        if (Objects.nonNull(param.getPpind())) {
            params.put(ArgNames.PPIND, param.getPpind());
        }
        if (Objects.nonNull(param.getShowDeliverables())) {
            params.put(ArgNames.SHOW_DELIVERABLES, param.getShowDeliverables());
        }
        if (param.getPageSize() > 0) {
            params.put(ArgNames.PAGE_SIZE, param.getPageSize());
        }
        if (StringUtils.isNotEmpty(param.getLastInstrumentId())) {
            params.put(ArgNames.LAST_INSTRUMENT_ID, param.getLastInstrumentId());
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<List<OptionContract>>() {}.getType()).doAction();
    }

    @Override
    public PaginatedResult<OptionContract> getOptionContractsV2(OptionContractQueryParam param) {
        Assert.notNull(ArgNames.PARAMETER, param);
        Assert.notBlank(ArgNames.CATEGORY, param.getCategory());
        HttpRequest request = new HttpRequest("/trading/instruments/options/contracts/list", Versions.V3, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ArgNames.CATEGORY, param.getCategory());
        if (StringUtils.isNotEmpty(param.getUnderlyingSymbols())) {
            params.put(ArgNames.UNDERLYING_SYMBOLS, param.getUnderlyingSymbols());
        }
        if (StringUtils.isNotEmpty(param.getStatus())) {
            params.put(ArgNames.STATUS, param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getStartDate())) {
            params.put(ArgNames.START_DATE, param.getStartDate());
        }
        if (StringUtils.isNotEmpty(param.getEndDate())) {
            params.put(ArgNames.END_DATE, param.getEndDate());
        }
        if (StringUtils.isNotEmpty(param.getRootSymbol())) {
            params.put(ArgNames.ROOT_SYMBOL, param.getRootSymbol());
        }
        if (StringUtils.isNotEmpty(param.getOptionSymbol())) {
            params.put(ArgNames.OPTION_SYMBOL, param.getOptionSymbol());
        }
        if (StringUtils.isNotEmpty(param.getOptionType())) {
            params.put(ArgNames.OPTION_TYPE, param.getOptionType());
        }
        if (StringUtils.isNotEmpty(param.getStyle())) {
            params.put(ArgNames.STYLE, param.getStyle());
        }
        if (Objects.nonNull(param.getStrikePriceGte())) {
            params.put(ArgNames.STRIKE_PRICE_GTE, param.getStrikePriceGte());
        }
        if (Objects.nonNull(param.getStrikePriceLte())) {
            params.put(ArgNames.STRIKE_PRICE_LTE, param.getStrikePriceLte());
        }
        if (Objects.nonNull(param.getPpind())) {
            params.put(ArgNames.PPIND, param.getPpind());
        }
        if (Objects.nonNull(param.getShowDeliverables())) {
            params.put(ArgNames.SHOW_DELIVERABLES, param.getShowDeliverables());
        }
        if (StringUtils.isNotEmpty(param.getPaginationKey())) {
            params.put(ArgNames.PAGINATION_KEY, param.getPaginationKey());
        }
        request.setQuery(params);
        addCustomHeaders(request);
        return apiClient.request(request).responseType(new TypeToken<PaginatedResult<OptionContract>>() {}.getType()).doAction();
    }

}
