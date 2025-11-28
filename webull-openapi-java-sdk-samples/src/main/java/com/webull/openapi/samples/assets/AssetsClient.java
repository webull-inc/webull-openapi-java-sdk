package com.webull.openapi.samples.assets;

import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.response.v2.AccountPositionsInfo;

import java.util.List;

public class AssetsClient {
    private static final Logger logger = LoggerFactory.getLogger(AssetsClient.class);

    public static void main(String[] args) throws InterruptedException {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)           // <your_app_key>
                .appSecret(Env.APP_SECRET)    //"<your_app_secret>"
                .regionId(Env.REGION_ID)     //<your_region_id> @see com.webull.openapi.core.common.Region
                /*
                 * Webull HK: PRD env host: api.webull.hk. Test env host: api.sanbox.webull.hk
                 * Webull US: PRD env host: api.webull.com; Test env host: us-openapi-alb.uat.webullbroker.com
                 */
                .endpoint("<api_endpoint>")
                .build();
        com.webull.openapi.trade.TradeClientV2 apiService = new com.webull.openapi.trade.TradeClientV2(apiConfig);

        // get accountId from account_list interface , you can look @see com.webull.openapi.samples.account.AccountList
        String accountId = "#{accountId}";
        // get account balance information
        AccountBalanceInfo balanceInfo = apiService.balanceAccount(accountId);
        logger.info("BalanceInfo: {}", balanceInfo);

        List<AccountPositionsInfo> positionsInfos = apiService.positionsAccount(accountId);
        logger.info("PositionsInfos: {}", positionsInfos);

    }

}