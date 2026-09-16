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

import com.webull.openapi.data.quotes.domain.*;
import com.webull.openapi.trade.response.PaginatedResult;

import java.util.List;
import java.util.Set;

public interface IDataClient {

    List<Instrument> getInstruments(Set<String> symbols, String category);

    /**
     * @deprecated The endpoint /market-data/stocks/bars/get is no longer available.
     * Use {@link #getBatchBars(List, String, String, int)} instead.
     */
    @Deprecated
    default List<Bar> getBars(String symbol, String category, String timespan) {
        return getBars(symbol, category, timespan, 200);
    }

    /**
     * @deprecated The endpoint /market-data/stocks/bars/get is no longer available.
     * Use {@link #getBatchBars(List, String, String, int)} instead.
     */
    @Deprecated
    List<Bar> getBars(String symbol, String category, String timespan, int count);

    /**
     * Get batch bars for multiple symbols.
     */
    BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count);

    /**
     * @deprecated The endpoint /market-data/stocks/bars/get is no longer available.
     * Use {@link #getBatchBars(List, String, String, int, Boolean, List)} instead.
     */
    @Deprecated
    List<Bar> getBars(String symbol, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions);

    /**
     * Get bars with time range filter.
     *
     * @param symbol           Security symbol
     * @param category         Security type
     * @param timespan         Timespan
     * @param count            Number of bars
     * @param realTimeRequired Whether real-time data is required
     * @param tradingSessions  Trading sessions
     * @param startTime        Start time in milliseconds timestamp (optional)
     * @param endTime          End time in milliseconds timestamp (optional)
     * @return List of bars
     * @deprecated The endpoint /market-data/stocks/bars/get is no longer available.
     * Use {@link #getBatchBars(List, String, String, int, Boolean, List, Long, Long)} instead.
     */
    @Deprecated
    List<Bar> getBars(String symbol, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions, Long startTime, Long endTime);

    /**
     * Get batch bars for multiple symbols.
     */
    BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions);

    /**
     * Get batch bars for multiple symbols with time range filter.
     *
     * @param symbols          List of security symbols
     * @param category         Security type
     * @param timespan         Timespan
     * @param count            Number of bars
     * @param realTimeRequired Whether real-time data is required
     * @param tradingSessions  Trading sessions
     * @param startTime        Start time in milliseconds timestamp (optional)
     * @param endTime          End time in milliseconds timestamp (optional)
     * @return Batch bar response
     */
    BatchBarResponse getBatchBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, List<String> tradingSessions, Long startTime, Long endTime);

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

    List<FootprintResponse> getFootprint(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, String tradingSessions);

    List<NBar> getFuturesBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired);

    DepthOfBook getFuturesDepth(String symbol, String category, String depth);

    List<Snapshot> getFuturesSnapshots(Set<String> symbols, String category);

    Tick getFutureTicks(String symbol, String category, int count);

    List<OptionBars> getOptionBars(List<String> symbols, String category, String timespan, int count, Boolean realTimeRequired);

    OptionTick getOptionTicks(String symbol, String category, int count);

    List<OptionSnapshot> getOptionSnapshots(Set<String> symbols, String category);

    @Deprecated
    List<FuturesProduct> getFuturesProducts(String category);

    List<FuturesProduct> getFuturesProductsV2(String category, String productClassId);

    @Deprecated
    List<FuturesInstrument> getFuturesInstruments(Set<String> symbols, String category);

    List<FuturesInstrument> getFuturesInstrumentsV2(String category, Set<String> symbols, String code, String status);

    @Deprecated
    List<FuturesInstrument> getFuturesInstrumentsByCode(String code, String category, String contractType);

    List<FuturesProductClass> getFuturesProductClasses(String category);

    List<FootprintResponse> getFuturesFootprint(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired, String tradingSessions);

    List<Snapshot> getCryptoSnapshots(Set<String> symbols, String category);

    List<NBar> getCryptoBars(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired);

    /**
     * @deprecated Use {@link #getInstrumentsV2(InstrumentQueryParam)} instead, which supports paginationKey-based pagination.
     */
    @Deprecated
    List<StockInstrumentDetail> getInstrumentsV1(InstrumentQueryParam param);

    /**
     * PaginationKey-based variant of {@link #getInstrumentsV1(InstrumentQueryParam)}.
     * Set {@link InstrumentQueryParam#setPaginationKey} to null for the first request;
     * use the returned paginationKey for subsequent pages. A null paginationKey in the
     * response indicates there are no more pages.
     *
     * @param param query parameters (category required; symbols/status/subCategory/paginationKey optional)
     * @return paginated result containing stock instrument list and next pagination key
     */
    PaginatedResult<StockInstrumentDetail> getInstrumentsV2(InstrumentQueryParam param);

    /**
     * @deprecated Use {@link #getCryptoInstrumentV2(InstrumentQueryParam)} instead, which supports paginationKey-based pagination.
     */
    @Deprecated
    List<CryptoInstrumentDetail> getCryptoInstrument(InstrumentQueryParam param);

    /**
     * PaginationKey-based variant of {@link #getCryptoInstrument(InstrumentQueryParam)}.
     *
     * @param param query parameters (category required; symbols/status/paginationKey optional)
     * @return paginated result containing crypto instrument list and next pagination key
     */
    PaginatedResult<CryptoInstrumentDetail> getCryptoInstrumentV2(InstrumentQueryParam param);

    List<EventCategories> getEventCategories();

    List<EventSeries> getEventSeriesList(String category, Set<String> symbols, String lastSeriesId, int pageSize);

    /**
     * Overloaded {@link #getEventSeriesList(String, Set, String, int)} using paginationKey-based pagination.
     * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
     *
     * @param category      category filter (optional)
     * @param symbols       symbol filter (optional)
     * @param paginationKey pagination key from previous response, null for first page
     * @return paginated result containing event series list and next pagination key
     */
    PaginatedResult<EventSeries> getEventSeriesList(String category, Set<String> symbols, String paginationKey);

    List<EventEvents> getEventEvents(String seriesSymbol, Set<String> symbols, String status);

    /**
     * @deprecated Use {@link #getEventInstrumentsListV1(EventInstrumentParam)} instead, which supports paginationKey-based pagination.
     */
    @Deprecated
    List<EventMarket> getEventInstrumentsList(EventInstrumentParam eventInstrumentParam);

    /**
     * PaginationKey-based variant of {@link #getEventInstrumentsList(EventInstrumentParam)}.
     * Set {@link EventInstrumentParam#setPaginationKey} to null for the first request;
     * use the returned paginationKey for subsequent pages.
     *
     * @param param query parameters (seriesSymbol required; eventSymbol/symbols/expirationDateAfter/paginationKey optional)
     * @return paginated result containing event market list and next pagination key
     */
    PaginatedResult<EventMarket> getEventInstrumentsListV1(EventInstrumentParam param);

    List<EventSnapshot> getEventSnapshot(Set<String> symbols, String category);

    EventDepth getEventDepth(String symbol, String category, String depth);

    List<EventBars> getEventBars(Set<String> symbols, String category, String timespan, int count, Boolean realTimeRequired);

    EventTick getEventTick(String symbol, String category, int count);

    // ==================== Company Profile & Analyst APIs ====================

    /**
     * Get company profile information.
     *
     * @param symbol   Security symbol, e.g., AAPL
     * @param category Security type. Possible values: US_STOCK
     * @return Company profile information
     */
    CompanyProfile getCompanyProfile(String symbol, String category);

    /**
     * Get analyst target price for a security.
     *
     * @param symbol   Security symbol, e.g., AAPL
     * @param category Security type. Possible values: US_STOCK
     * @return Analyst target price information
     */
    AnalystTargetPrice getAnalystTargetPrice(String symbol, String category);

    /**
     * Get analyst rating for a security.
     *
     * @param symbol   Security symbol, e.g., AAPL
     * @param category Security type. Possible values: US_STOCK
     * @return Analyst rating information
     */
    AnalystRating getAnalystRating(String symbol, String category);

    // ==================== Watchlist APIs ====================

    /**
     * Get all watchlists for the current user.
     *
     * @return List of watchlists sorted by sort order in descending order
     */
    List<Watchlist> getWatchlists();

    /**
     * Create a new watchlist.
     * Maximum 20 watchlists can be created (shared with retail).
     *
     * @param name Watchlist name
     * @param sort Sort order number (optional)
     * @return Response containing the new watchlist_id
     */
    WatchlistCreateResponse createWatchlist(String name, Integer sort);

    /**
     * Update an existing watchlist's properties.
     *
     * @param watchlistId Watchlist unique identifier
     * @param name        New watchlist name (optional)
     * @param sort        New sort order number (optional)
     */
    void updateWatchlist(String watchlistId, String name, Integer sort);

    /**
     * Delete a watchlist and all instruments in it.
     * This operation is irreversible.
     *
     * @param watchlistId Watchlist unique identifier
     */
    void deleteWatchlist(String watchlistId);

    /**
     * Get all instruments in a watchlist.
     *
     * @param watchlistId Watchlist unique identifier
     * @return Response containing watchlist_id and list of instruments
     */
    WatchlistInstrumentsResponse getWatchlistInstruments(String watchlistId);

    /**
     * Add instruments to a watchlist.
     * Maximum 1000 instruments total across all watchlists.
     *
     * @param watchlistId Watchlist unique identifier
     * @param instruments List of instruments to add
     */
    void addWatchlistInstruments(String watchlistId, List<WatchlistInstrumentParam> instruments);

    /**
     * Remove instruments from a watchlist.
     *
     * @param watchlistId Watchlist unique identifier
     * @param instruments List of instruments to remove
     */
    void removeWatchlistInstruments(String watchlistId, List<WatchlistInstrumentParam> instruments);

    /**
     * Update the sort order of instruments in a watchlist.
     *
     * @param watchlistId Watchlist unique identifier
     * @param instruments List of instruments to update with new sort order
     */
    void updateWatchlistInstruments(String watchlistId, List<WatchlistInstrumentParam> instruments);

    // ==================== Fundamentals APIs ====================

    /**
     * Get capital flow data for a stock.
     *
     * @param symbol   Security symbol
     * @param category Security type (e.g., US_STOCK, HK_STOCK)
     * @param count    Number of records (optional, default 5, range 1-5)
     * @return List of capital flow data by date
     */
    List<CapitalFlow> getCapitalFlow(String symbol, String category, Integer count);

    /**
     * Get industry comparison data for a stock.
     *
     * @param symbol   Security symbol
     * @param category Security type
     * @param sortBy   Sort field (optional, default EPS_TTM, enum: EPS_TTM/NAPS/DPS_TTM/ROE/DEBT_TO_ASSETS/NET_MARGIN/DIV_YIELD_TTM/PE_TTM/PB_RATIO)
     * @return Industry comparison data
     */
    IndustryComparison getIndustryComparison(String symbol, String category, String sortBy);

    /**
     * Get SEC filings for a stock.
     *
     * @param symbol   Security symbol
     * @param category Security type (only US_STOCK supported)
     * @return SEC filings data
     */
    SecFilings getSecFilings(String symbol, String category);

    /**
     * Get earnings calendar for a stock.
     *
     * @param symbol   Security symbol
     * @param category Security type (supports US/HK/CN/JP_STOCK)
     * @return List of earnings calendar entries
     */
    List<EarningsCalendar> getEarningsCalendar(String symbol, String category);

    /**
     * Get dividend calendar for a stock.
     *
     * @param symbol   Security symbol
     * @param category Security type (supports US/HK/CN/JP_STOCK)
     * @return List of dividend calendar entries
     */
    List<DividendCalendar> getDividendCalendar(String symbol, String category);

    /**
     * Get forecast EPS data for a stock.
     * Returns the historical actual EPS of the most recent four disclosed fiscal periods,
     * as well as the latest analyst consensus estimated EPS (if any).
     * The list is sorted in ascending chronological order, with a maximum of 5 entries.
     *
     * @param symbol   Security symbol (single symbol only)
     * @param category Security type (supports US_STOCK/HK_STOCK/CN_STOCK)
     * @return List of forecast EPS entries
     */
    List<ForecastEps> getForecastEps(String symbol, String category);

    /**
     * Get fund splits information.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @return List of fund split entries
     */
    List<FundSplit> getFundSplits(String symbol, String category);

    /**
     * Get fund rating information.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @return List of fund rating entries
     */
    List<FundRating> getFundRating(String symbol, String category);

    /**
     * Get fund performance data.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @return Fund performance data
     */
    FundPerformance getFundPerformance(String symbol, String category);

    /**
     * Get fund net value (NAV) data.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @param lastDate Last date for pagination (optional)
     * @param count    Number of records (optional, default 5, max 20)
     * @return List of fund net value entries
     */
    List<FundNetValue> getFundNetValue(String symbol, String category, String lastDate, Integer count);

    /**
     * Get fund holdings data.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @return List of fund holding entries
     */
    List<FundHolding> getFundHoldings(String symbol, String category);

    /**
     * Get fund files/documents.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @return List of fund file entries
     */
    List<FundFile> getFundFiles(String symbol, String category);

    /**
     * Get fund dividend history.
     *
     * @param symbol    Fund symbol
     * @param category  Security type
     * @param pageIndex Page number (optional, default 1)
     * @param pageSize  Page size (optional, default 10, max 20)
     * @return List of fund dividend entries
     * @deprecated Use {@link #getFundDividends(String, String, String)} instead, which supports paginationKey-based pagination.
     */
    @Deprecated
    List<FundDividend> getFundDividends(String symbol, String category, Integer pageIndex, Integer pageSize);

    /**
     * Overloaded {@link #getFundDividends(String, String, Integer, Integer)} using paginationKey-based pagination.
     * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
     *
     * @param symbol        fund symbol
     * @param category      security type
     * @param paginationKey pagination key from previous response, null for first page
     * @return paginated result containing fund dividend list and next pagination key
     */
    PaginatedResult<FundDividend> getFundDividends(String symbol, String category, String paginationKey);

    /**
     * Get fund brief information.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @return Fund brief information
     */
    FundBrief getFundBrief(String symbol, String category);

    /**
     * Get fund allocation data.
     *
     * @param symbol   Fund symbol
     * @param category Security type
     * @return List of fund allocation entries
     */
    List<FundAllocation> getFundAllocation(String symbol, String category);

    /**
     * Get financial indicators for a stock.
     *
     * @param symbol   Security symbol
     * @param category Security type
     * @param type     Report type (optional, default QUARTERLY, enum: ANNUAL/QUARTERLY)
     * @param count    Number of records (optional, default 5, max 20)
     * @return Financial indicators data
     */
    FinancialIndicators getFinancialsIndicators(String symbol, String category, String type, Integer count);

    /**
     * Get income statement data.
     *
     * @param symbol   Security symbol
     * @param category Security type
     * @param type     Report type (optional, default QUARTERLY, enum: ANNUAL/QUARTERLY)
     * @param count    Number of records (optional, default 5, max 20)
     * @return List of income statement entries
     */
    List<FinancialIncome> getFinancialsIncome(String symbol, String category, String type, Integer count);

    /**
     * Get cash flow statement data.
     *
     * @param symbol   Security symbol
     * @param category Security type
     * @param type     Report type (optional, default QUARTERLY, enum: ANNUAL/QUARTERLY)
     * @param count    Number of records (optional, default 5, max 20)
     * @return List of cash flow statement entries
     */
    List<FinancialCashflow> getFinancialsCashflow(String symbol, String category, String type, Integer count);

    /**
     * Get balance sheet data.
     *
     * @param symbol   Security symbol
     * @param category Security type
     * @param type     Report type (optional, default QUARTERLY, enum: ANNUAL/QUARTERLY)
     * @param count    Number of records (optional, default 5, max 20)
     * @return List of balance sheet entries
     */
    List<FinancialBalanceSheet> getFinancialsBalanceSheet(String symbol, String category, String type, Integer count);

    /**
     * Get financial alert data.
     *
     * @param symbol   Security symbol
     * @param category Security type
     * @return Financial alert data
     */
    FinancialAlert getFinancialsAlert(String symbol, String category);

    // ==================== Screener APIs ====================

    /**
     * Get stock top gainers or losers ranking by price change percentage.
     *
     * @param rankType  Time period for ranking (e.g., DAY_1, WEEK_52)
     * @param category  Security market category (e.g., US_STOCK)
     * @param sortBy    Secondary sort field
     * @param pageIndex Page number, starting from 1 (optional)
     * @param pageSize  Number of records per page (optional)
     * @param direction Sort direction: ASC for losers, DESC for gainers (optional)
     * @return Response containing has_more flag and list of ranked stocks
     * @deprecated Use {@link #getGainersLosers(String, String, String, String)} instead. The ranking list is no longer paginated and returns the top 200 records.
     */
    @Deprecated
    ScreenerResponse getGainersLosers(String rankType, String category, String sortBy,
                                       Integer pageIndex, Integer pageSize, String direction);

    /**
     * Get stock top gainers or losers ranking by price change percentage.
     * The ranking list is not paginated and returns the top 200 records.
     *
     * @param rankType  Time period for ranking (optional, default D1)
     * @param category  Security market category (required, e.g., US_STOCK)
     * @param sortBy    Sort field (optional, default CHANGE_RATIO)
     * @param direction Sort direction: ASC for losers, DESC for gainers (optional, default DESC)
     * @return List of ranked stocks (top 200)
     */
    List<ScreenerStock> getGainersLosers(String rankType, String category, String sortBy, String direction);

    /**
     * Get most actively traded stocks ranking.
     *
     * @param category  Security market category (e.g., US_STOCK)
     * @param rankType  Activity metric for ranking (e.g., VOLUME, TURNOVER) (optional)
     * @param sortBy    Secondary sort field (optional)
     * @param pageIndex Page number, starting from 1 (optional)
     * @param pageSize  Number of records per page (optional)
     * @param direction Sort direction (optional, defaults to DESC)
     * @return Response containing has_more flag and list of ranked stocks
     * @deprecated Use {@link #getMostActive(String, String, String, String)} instead. The ranking list is no longer paginated and returns the top 200 records.
     */
    @Deprecated
    ScreenerResponse getMostActive(String category, String rankType, String sortBy,
                                    Integer pageIndex, Integer pageSize, String direction);

    /**
     * Get most actively traded stocks ranking.
     * The ranking list is not paginated and returns the top 200 records.
     *
     * @param category  Security market category (required, e.g., US_STOCK)
     * @param rankType  Activity metric for ranking (optional, default VOLUME)
     * @param sortBy    Sort field (optional, default VOLUME)
     * @param direction Sort direction (optional, default DESC)
     * @return List of ranked stocks (top 200)
     */
    List<ScreenerStock> getMostActive(String category, String rankType, String sortBy, String direction);

    /**
     * Get market sectors data.
     *
     * @param category  Security market category (e.g., US_STOCK)
     * @param aggType   Aggregation type (optional, default MARKET_VALUE, enum: MARKET_VALUE/VOLUME)
     * @param period    Time period (optional, default D1, enum: D1/D5/M01/M03)
     * @param pageIndex Page number (optional)
     * @param pageSize  Number of records per page (optional)
     * @param direction Sort direction (optional, enum: ASC/DESC)
     * @return List of market sectors
     * @deprecated Use {@link #getMarketSectors(String, String, String, String, String)} instead, which supports paginationKey-based pagination.
     */
    @Deprecated
    List<MarketSector> getMarketSectors(String category, String aggType, String period,
                                            Integer pageIndex, Integer pageSize, String direction);

    /**
     * Overloaded {@link #getMarketSectors(String, String, String, Integer, Integer, String)} using paginationKey-based pagination.
     * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
     *
     * @param category      security market category (required)
     * @param aggType       aggregation type (optional)
     * @param period        time period (optional)
     * @param direction     sort direction (optional)
     * @param paginationKey pagination key from previous response, null for first page
     * @return paginated result containing market sector list and next pagination key
     */
    PaginatedResult<MarketSector> getMarketSectors(String category, String aggType, String period,
                                                   String direction, String paginationKey);

    /**
     * Get market sectors detail data.
     *
     * @param sectorId  Sector identifier (required)
     * @param category  Security market category (required)
     * @param period    Time period (optional, default D1)
     * @param pageIndex Page number (optional)
     * @param pageSize  Number of records per page (optional)
     * @param sortBy    Sort field (optional, default CHANGE_RATIO)
     * @param direction Sort direction (optional, enum: ASC/DESC)
     * @return Market sector detail with stock list
     */
    MarketSectorDetail getMarketSectorsDetail(String sectorId, String category, String period,
                                             Integer pageIndex, Integer pageSize, String sortBy, String direction);

    /**
     * Get high dividend stocks.
     *
     * @param category  Security market category (required)
     * @param sortBy    Sort field (optional, default YIELD)
     * @param pageIndex Page number (optional)
     * @param pageSize  Number of records per page (optional)
     * @param direction Sort direction (optional, enum: ASC/DESC)
     * @return High dividend response with stock list
     * @deprecated Use {@link #getHighDividend(String, String, String)} instead. The ranking list is no longer paginated and returns the top 200 records.
     */
    @Deprecated
    HighDividendResponse getHighDividend(String category, String sortBy,
                                      Integer pageIndex, Integer pageSize, String direction);

    /**
     * Get high dividend stocks.
     * The ranking list is not paginated and returns the top 200 records.
     *
     * @param category  Security market category (required)
     * @param sortBy    Sort field (optional, default YIELD)
     * @param direction Sort direction (optional, default DESC)
     * @return List of high dividend stocks (top 200)
     */
    List<HighDividendStock> getHighDividend(String category, String sortBy, String direction);

    /**
     * Get 52-week high/low stocks.
     *
     * @param rankType  Rank type (optional, enum: NEW_HIGH/NEAR_HIGH/NEW_LOW/NEAR_LOW)
     * @param category  Security market category (required)
     * @param sortBy    Sort field (optional, default CHANGE_RATIO_52W)
     * @param pageIndex Page number (optional)
     * @param pageSize  Number of records per page (optional)
     * @param direction Sort direction (optional, enum: ASC/DESC)
     * @return 52-week high/low response with stock list
     * @deprecated Use {@link #get52Whl(String, String, String, String)} instead. The ranking list is no longer paginated and returns the top 200 records.
     */
    @Deprecated
    FiftyTwoWeekResponse get52Whl(String rankType, String category, String sortBy,
                               Integer pageIndex, Integer pageSize, String direction);

    /**
     * Get 52-week high/low stocks.
     * The ranking list is not paginated and returns the top 200 records.
     *
     * @param rankType  Rank type (optional, enum: NEW_HIGH/NEAR_HIGH/NEW_LOW/NEAR_LOW)
     * @param category  Security market category (required)
     * @param sortBy    Sort field (optional, default CHANGE_RATIO_52W)
     * @param direction Sort direction (optional, enum: ASC/DESC)
     * @return List of 52-week high/low stocks (top 200)
     */
    List<FiftyTwoWeekStock> get52Whl(String rankType, String category, String sortBy, String direction);

    // ==================== NOII APIs ====================

    /**
     * Get NOII (Net Order Imbalance Indicator) bar data.
     * Provides imbalance information during NASDAQ opening/closing auctions.
     *
     * @param symbol              Security symbol (single query only)
     * @param category            Security type, currently only supports US_STOCK
     * @param imbalanceActionType Imbalance action type: PRE_OPEN (opening) or PRE_CLOSE (closing)
     * @return List of NOII bar data
     */
    List<NoiiBar> getNoiiBars(String symbol, String category, String imbalanceActionType);

    /**
     * Get NOII (Net Order Imbalance Indicator) snapshot data.
     * Provides the latest NOII data during NASDAQ opening/closing auctions.
     * Updated every 5 seconds during auction periods:
     * - Opening auction: 9:28 - 9:30 AM ET (2 minutes)
     * - Closing auction: 3:50 - 4:00 PM ET (10 minutes)
     *
     * @param symbol              Security symbol (single query only)
     * @param category            Security type, currently only supports US_STOCK
     * @param imbalanceActionType Imbalance action type: PRE_OPEN (opening) or PRE_CLOSE (closing)
     * @return NOII snapshot data
     */
    NoiiSnapshot getNoiiSnapshot(String symbol, String category, String imbalanceActionType);


    // ==================== Option Instrument APIs ====================

    /**
     * Query option contract list by various filters such as underlying symbols, status, expiration date, etc.
     *
     * @param param Query parameters including category (required, US_OPTION),
     *              underlying symbols, status, expiration date range, option type, style,
     *              strike price range, pagination, etc.
     * @return List of option contracts matching the query criteria
     * @deprecated Use {@link #getOptionContractsV2(OptionContractQueryParam)} instead, which supports paginationKey-based pagination.
     */
    @Deprecated
    List<OptionContract> getOptionContracts(OptionContractQueryParam param);

    /**
     * PaginationKey-based variant of {@link #getOptionContracts(OptionContractQueryParam)}.
     * Set {@link OptionContractQueryParam#setPaginationKey} to null for the first request;
     * use the returned paginationKey for subsequent pages.
     *
     * @param param query parameters including category (required) and other filters plus paginationKey
     * @return paginated result containing option contract list and next pagination key
     */
    PaginatedResult<OptionContract> getOptionContractsV2(OptionContractQueryParam param);
}
