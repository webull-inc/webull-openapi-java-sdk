package com.webull.openapi.samples.screener;

import com.webull.openapi.core.common.dict.ActiveRankType;
import com.webull.openapi.core.common.dict.Category;
import com.webull.openapi.core.common.dict.ScreenerOrderBy;
import com.webull.openapi.core.common.dict.ScreenerRankType;
import com.webull.openapi.core.common.dict.SortDirection;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.data.DataClient;
import com.webull.openapi.data.quotes.domain.ScreenerResponse;
import com.webull.openapi.samples.config.Env;

public class ScreenerClient {

    private static final Logger logger = LoggerFactory.getLogger(ScreenerClient.class);

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

        // Get top gainers for today (stocks with highest price increase)
        ScreenerResponse gainers = dataClient.getGainersLosers(
                ScreenerRankType.DAY_1.name(),
                Category.US_STOCK.name(),
                ScreenerOrderBy.CHANGE_RATIO.name(),
                1,
                10,
                SortDirection.DESC.name()
        );
        logger.info("Top gainers (day): {}", gainers);

        // Get top losers for today (stocks with highest price decrease)
        ScreenerResponse losers = dataClient.getGainersLosers(
                ScreenerRankType.DAY_1.name(),
                Category.US_STOCK.name(),
                ScreenerOrderBy.CHANGE_RATIO.name(),
                1,
                10,
                SortDirection.ASC.name()
        );
        logger.info("Top losers (day): {}", losers);

        // Get pre-market movers
        ScreenerResponse preMarketMovers = dataClient.getGainersLosers(
                ScreenerRankType.PRE_MARKET.name(),
                Category.US_STOCK.name(),
                ScreenerOrderBy.CHANGE_RATIO.name(),
                1,
                20,
                SortDirection.DESC.name()
        );
        logger.info("Pre-market movers: {}", preMarketMovers);

        // Get 52-week top performers
        ScreenerResponse week52TopPerformers = dataClient.getGainersLosers(
                ScreenerRankType.WEEK_52.name(),
                Category.US_STOCK.name(),
                ScreenerOrderBy.CHANGE_RATIO.name(),
                1,
                10,
                SortDirection.DESC.name()
        );
        logger.info("52-week top performers: {}", week52TopPerformers);

        // Get most active stocks by volume
        ScreenerResponse mostActiveByVolume = dataClient.getMostActive(
                Category.US_STOCK.name(),
                ActiveRankType.VOLUME.name(),
                ScreenerOrderBy.VOLUME.name(),
                1,
                10,
                SortDirection.DESC.name()
        );
        logger.info("Most active by volume: {}", mostActiveByVolume);

        // Get stocks with unusual trading activity (high relative volume)
        ScreenerResponse unusualVolume = dataClient.getMostActive(
                Category.US_STOCK.name(),
                ActiveRankType.RELATIVE_VOLUME_10D.name(),
                ScreenerOrderBy.RELATIVE_VOLUME_10D.name(),
                1,
                10,
                SortDirection.DESC.name()
        );
        logger.info("Unusual volume activity: {}", unusualVolume);

        // Get most active stocks by turnover amount
        ScreenerResponse mostActiveByTurnover = dataClient.getMostActive(
                Category.US_STOCK.name(),
                ActiveRankType.TURNOVER.name(),
                ScreenerOrderBy.TURNOVER.name(),
                1,
                10,
                SortDirection.DESC.name()
        );
        logger.info("Most active by turnover: {}", mostActiveByTurnover);

        // Get stocks with high price amplitude
        ScreenerResponse highAmplitude = dataClient.getMostActive(
                Category.US_STOCK.name(),
                ActiveRankType.AMPLITUDE.name(),
                ScreenerOrderBy.AMPLITUDE.name(),
                1,
                10,
                SortDirection.DESC.name()
        );
        logger.info("High amplitude stocks: {}", highAmplitude);
    }
}
