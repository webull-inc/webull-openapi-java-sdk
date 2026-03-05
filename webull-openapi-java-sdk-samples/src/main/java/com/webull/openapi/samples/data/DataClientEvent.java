package com.webull.openapi.samples.data;

import com.webull.openapi.core.common.dict.Category;
import com.webull.openapi.core.common.dict.Timespan;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.data.quotes.api.IDataClient;
import com.webull.openapi.data.quotes.domain.*;
import com.webull.openapi.samples.config.Env;

import java.util.*;

public class DataClientEvent {

    private static final Logger logger = LoggerFactory.getLogger(DataClientEvent.class);

    public static void main(String[] args) {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                //.endpoint("<api_endpoint>")
                /*
                 * The tokenDir parameter can be used to specify the directory for storing the 2FA token. Both absolute and relative paths are supported and this option has the highest priority.
                 * Alternatively, the storage directory can be configured via an environment variable with the key WEBULL_OPENAPI_TOKEN_DIR, which also supports both absolute and relative paths.
                 * If neither is specified, the default configuration will be used, and the token will be stored at conf/token.txt under the current working directory.
                 */
                //.tokenDir("conf_custom_relative")
                .build();
        IDataClient dataClient = new com.webull.openapi.data.DataClient(apiConfig);
        List<EventCategories> eventCategories = dataClient.getEventCategories();
        logger.info("Event categories: {}", eventCategories);

        if (CollectionUtils.isEmpty(eventCategories)) {
            return;
        }

        List<EventSeries> eventSeriesList = dataClient.getEventSeriesList(eventCategories.get(0).getCategoryCode(), null, null, 500);
        logger.info("Event Series: {}", eventSeriesList);

        if (CollectionUtils.isEmpty(eventSeriesList)) {
            return;
        }

        List<EventEvents> eventEvents = dataClient.getEventEvents(eventSeriesList.get(0).getSymbol(), null, "active");
        logger.info("Event eventEvents: {}", eventEvents);

        EventInstrumentParam eventInstrumentParam = new EventInstrumentParam();
        eventInstrumentParam.setSeriesSymbol(eventSeriesList.get(0).getSymbol());
        List<EventMarket> eventInstrumentsList = dataClient.getEventInstrumentsList(eventInstrumentParam);
        logger.info("Event Instruments: {}", eventInstrumentsList);

        if (CollectionUtils.isEmpty(eventInstrumentsList)) {
            return;
        }

        String symbol = eventInstrumentsList.get(0).getSymbol();
        Set<String> symbols = new HashSet<>();
        symbols.add(symbol);
        List<EventSnapshot> eventSnapshot = dataClient.getEventSnapshot(symbols, Category.US_EVENT.name());
        logger.info("Event Snapshot: {}", eventSnapshot);

        EventDepth eventDepth = dataClient.getEventDepth(symbol, Category.US_EVENT.name(), "10");
        logger.info("Event Depth: {}", eventDepth);

        List<EventBars> eventBars = dataClient.getEventBars(symbols, Category.US_EVENT.name(), Timespan.M1.name(), 200, false);
        logger.info("Event Bars: {}", eventBars);

        EventTick eventTick = dataClient.getEventTick(symbol, Category.US_EVENT.name(), 200);
        logger.info("Event Tick: {}", eventTick);
    }
}
