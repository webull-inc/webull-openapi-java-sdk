package com.webull.openapi.samples.watchlist;

import com.webull.openapi.core.common.dict.Category;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.DataClient;
import com.webull.openapi.data.quotes.domain.Watchlist;
import com.webull.openapi.data.quotes.domain.WatchlistCreateResponse;
import com.webull.openapi.data.quotes.domain.WatchlistInstrumentParam;
import com.webull.openapi.data.quotes.domain.WatchlistInstrumentsResponse;
import com.webull.openapi.samples.config.Env;

import java.util.ArrayList;
import java.util.List;

public class WatchlistClient {

    private static final Logger logger = LoggerFactory.getLogger(WatchlistClient.class);

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
        DataClient dataClient = new DataClient(apiConfig);

        // Get all watchlists
        List<Watchlist> watchlists = dataClient.getWatchlists();
        logger.info("Watchlists: {}", watchlists);

        // Create a new watchlist (max 20 watchlists, shared with retail)
        WatchlistCreateResponse createResponse = dataClient.createWatchlist("Tech Stocks", 1);
        logger.info("Created watchlist: {}", createResponse);

        String watchlistId = createResponse.getWatchlistId();
        if (watchlistId != null) {
            // Update the watchlist
            dataClient.updateWatchlist(watchlistId, "My Favorites", 2);
            logger.info("Updated watchlist");

            // Add instruments to the watchlist (max 1000 instruments total)
            List<WatchlistInstrumentParam> instrumentsToAdd = new ArrayList<>();
            instrumentsToAdd.add(new WatchlistInstrumentParam("AAPL", Category.US_STOCK.name(), 1));
            instrumentsToAdd.add(new WatchlistInstrumentParam("TSLA", Category.US_STOCK.name(), 2));
            dataClient.addWatchlistInstruments(watchlistId, instrumentsToAdd);
            logger.info("Added instruments to watchlist");

            // Get instruments in the watchlist
            WatchlistInstrumentsResponse instrumentsResponse = dataClient.getWatchlistInstruments(watchlistId);
            logger.info("Watchlist instruments: {}", instrumentsResponse);

            // Update instruments sort order
            List<WatchlistInstrumentParam> instrumentsToUpdate = new ArrayList<>();
            instrumentsToUpdate.add(new WatchlistInstrumentParam("AAPL", Category.US_STOCK.name(), 3));
            instrumentsToUpdate.add(new WatchlistInstrumentParam("TSLA", Category.US_STOCK.name(), 1));
            dataClient.updateWatchlistInstruments(watchlistId, instrumentsToUpdate);
            logger.info("Updated instruments sort order");

            // Remove instruments from the watchlist
            List<WatchlistInstrumentParam> instrumentsToRemove = new ArrayList<>();
            instrumentsToRemove.add(new WatchlistInstrumentParam("AAPL", Category.US_STOCK.name()));
            instrumentsToRemove.add(new WatchlistInstrumentParam("TSLA", Category.US_STOCK.name()));
            dataClient.removeWatchlistInstruments(watchlistId, instrumentsToRemove);
            logger.info("Removed instruments from watchlist");

            // Delete the watchlist (irreversible)
            dataClient.deleteWatchlist(watchlistId);
            logger.info("Deleted watchlist");
        }
    }
}
