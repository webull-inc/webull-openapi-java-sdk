package com.webull.openapi.samples.account;

import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.response.v2.Account;

import java.util.List;

public class AccountList {
    private static final Logger logger = LoggerFactory.getLogger(AccountList.class);

    public static void main(String[] args) throws InterruptedException {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)              // <your_app_key>
                .appSecret(Env.APP_SECRET)        //"<your_app_secret>"
                .regionId(Env.REGION_ID)          //<your_region_id> @see com.webull.openapi.core.common.Region
                /*
                 * Webull HK: PRD env host: api.webull.hk. Test env host: api.sanbox.webull.hk
                 * Webull US: PRD env host: api.webull.com; Test env host: us-openapi-alb.uat.webullbroker.com
                 */
                .endpoint("<api_endpoint>")
                .build();

        com.webull.openapi.trade.TradeClientV2 apiService = new com.webull.openapi.trade.TradeClientV2(apiConfig);

        // get account list
        List<Account> accounts = apiService.listAccount();
        logger.info("Accounts: {}", accounts);

    }
}