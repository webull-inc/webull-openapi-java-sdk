package com.webull.openapi.samples.data;

import com.webull.openapi.core.common.dict.Category;
import com.webull.openapi.core.common.dict.ContractType;
import com.webull.openapi.core.common.dict.Timespan;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.quotes.api.IDataClient;
import com.webull.openapi.data.quotes.domain.*;
import com.webull.openapi.samples.config.Env;

import java.util.*;

public class DataClient {

    private static final Logger logger = LoggerFactory.getLogger(DataClient.class);

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

        // get all crypto instruments
        InstrumentQueryParam instrumentQueryParam = new InstrumentQueryParam();
        instrumentQueryParam.setCategory(Category.US_CRYPTO.name());
        List<CryptoInstrumentDetail> cryptoInstruments = dataClient.getCryptoInstrument(instrumentQueryParam);
        logger.info("Crypto instruments(all): {}", cryptoInstruments);
        // get all crypto instruments
        HashSet<String> cryptoSymbols = new HashSet<>();
        cryptoSymbols.add("BTCUSD");
        instrumentQueryParam.setSymbols(cryptoSymbols);
        cryptoInstruments = dataClient.getCryptoInstrument(instrumentQueryParam);
        logger.info("Crypto instruments: {}", cryptoInstruments);

        // get crypto bars
        List<NBar> cryptoBars = dataClient.getCryptoBars(cryptoSymbols,Category.US_CRYPTO.name(), Timespan.M1.name(), 100, true);
        logger.info("Crypto bars: {}", cryptoBars);

        //get crypto snapshot
        List<Snapshot> cryptoSnapshot = dataClient.getCryptoSnapshots(cryptoSymbols,Category.US_CRYPTO.name());
        logger.info("Crypto snapshot: {}", cryptoSnapshot);

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

        List<FootprintResponse> footprint = dataClient.getFootprint(symbols, Category.US_STOCK.name(), Timespan.S5.name(), 200, null, null);
        logger.info("Footprint: {}", footprint);

        DepthOfBook futureDepth = dataClient.getFuturesDepth("SILZ5", Category.US_FUTURES.name(), "1");
        logger.info("Futures depth: {}", futureDepth);

        Tick futureTicks = dataClient.getFutureTicks("SILZ5", Category.US_FUTURES.name(), 100);
        logger.info("Futures Ticks: {}", futureTicks);

        Set<String> futuresSymbols = new HashSet<>();
        futuresSymbols.add("ESZ5");
        futuresSymbols.add("6BM6");
        List<Snapshot> futureSnapshots = dataClient.getFuturesSnapshots(futuresSymbols, Category.US_FUTURES.name());
        logger.info("Futures Snapshots: {}", futureSnapshots);

        List<NBar> futureBars = dataClient.getFuturesBars(new ArrayList<>(futuresSymbols), Category.US_FUTURES.name(), Timespan.M1.name(), 10, false);
        logger.info("Futures Bars: {}", futureBars);

        List<FuturesProduct> futuresProducts = dataClient.getFuturesProducts(Category.US_FUTURES.name());
        logger.info("Futures Products: {}", futuresProducts);

        List<FuturesInstrument> futuresInstruments = dataClient.getFuturesInstruments(futuresSymbols, Category.US_FUTURES.name());
        logger.info("Futures Instruments: {}", futuresInstruments);

        List<FuturesInstrument> futuresInstrumentsByCode = dataClient.getFuturesInstrumentsByCode("ES", Category.US_FUTURES.name(), ContractType.MONTHLY.name());
        logger.info("Futures Instruments By Code: {}", futuresInstrumentsByCode);

        List<FootprintResponse> futuresFootprint = dataClient.getFuturesFootprint(futuresSymbols, Category.US_FUTURES.name(), Timespan.S5.name(), 200, null, null);
        logger.info("FuturesFootprint: {}", futuresFootprint);

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
