package com.webull.openapi.samples.data;

import com.webull.openapi.core.common.dict.Category;
import com.webull.openapi.core.common.dict.Timespan;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.quotes.api.IDataClient;
import com.webull.openapi.data.quotes.domain.Bar;
import com.webull.openapi.data.quotes.domain.BatchBarResponse;
import com.webull.openapi.data.quotes.domain.Instrument;
import com.webull.openapi.data.quotes.domain.Quote;
import com.webull.openapi.data.quotes.domain.Snapshot;
import com.webull.openapi.data.quotes.domain.Tick;
import com.webull.openapi.samples.config.Env;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataClient {

    private static final Logger logger = LoggerFactory.getLogger(DataClient.class);

    public static void main(String[] args) {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                // .endpoint("<api_endpoint>")
                .build();
        IDataClient dataClient = new com.webull.openapi.data.DataClient(apiConfig);

        Set<String> symbols = new HashSet<>();
        symbols.add("AAPL");
        symbols.add("TSLA");

        List<String> tradingSessions = new ArrayList<>();
        tradingSessions.add("PRE");
        tradingSessions.add("RTH");
        tradingSessions.add("ATH");
        //tradingSessions.add("OVN");

        // get instruments
        List<Instrument> instruments = dataClient.getInstruments(symbols, Category.US_STOCK.name());
        logger.info("Instruments: {}", instruments);

        // get bars
        List<Bar> bars = dataClient.getBars("AAPL", Category.US_STOCK.name(), Timespan.M1.name(), 10, true, tradingSessions);
        logger.info("Bars: {}", bars);

        // get bars with symbols
        BatchBarResponse batchBars = dataClient.getBatchBars(new ArrayList<>(symbols), Category.US_STOCK.name(), Timespan.M1.name(), 10, true, tradingSessions);
        logger.info("Batch bars: {}", batchBars);

        // get snapshots
        List<Snapshot> snapshots = dataClient.getSnapshots(symbols, Category.US_STOCK.name(), true, false);
        logger.info("Snapshots: {}", snapshots);

        // get quotes
        Quote quote = dataClient.getQuote("AAPL", Category.US_STOCK.name(), "1", false);
        logger.info("Quote: {}", quote);

        // get ticks
        Tick tick = dataClient.getTicks("AAPL", Category.US_STOCK.name(), 100, tradingSessions);
        logger.info("Tick: {}", tick);

//        // get end of day market
//        List<EodBars> eodBars = IDataClient.getEodBars(instrumentIds, "2023-01-01", 10);
//        logger.info("Eod bars: {}", eodBars);
//
//        // get corp action
//        CorpActionRequest corpActionReq = new CorpActionRequest();
//        corpActionReq.setEventTypes(eventTypes);
//        corpActionReq.setInstrumentIds(instrumentIds);
//        List<CorpAction> corpAction = IDataClient.getCorpAction(corpActionReq);
//        logger.info("Corp action: {}", corpAction);
    }
}
