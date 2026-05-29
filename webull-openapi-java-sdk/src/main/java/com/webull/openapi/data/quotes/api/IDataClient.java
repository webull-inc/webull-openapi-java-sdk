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
     */
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

    List<StockInstrumentDetail> getInstrumentsV1(InstrumentQueryParam param);

    List<CryptoInstrumentDetail> getCryptoInstrument(InstrumentQueryParam param);

    List<EventCategories> getEventCategories();

    List<EventSeries> getEventSeriesList(String category, Set<String> symbols, String lastSeriesId, int pageSize);

    List<EventEvents> getEventEvents(String seriesSymbol, Set<String> symbols, String status);

    List<EventMarket> getEventInstrumentsList(EventInstrumentParam eventInstrumentParam);

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
     */
    ScreenerResponse getGainersLosers(String rankType, String category, String sortBy,
                                       Integer pageIndex, Integer pageSize, String direction);

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
     */
    ScreenerResponse getMostActive(String category, String rankType, String sortBy,
                                    Integer pageIndex, Integer pageSize, String direction);

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

}
