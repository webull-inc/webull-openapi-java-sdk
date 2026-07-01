package com.webull.openapi.samples.trade;

import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.TradeClientV3;
import com.webull.openapi.trade.response.v3.Account;
import com.webull.openapi.trade.response.v3.Activity;

import java.util.List;

public class CashActivityClient {

    private static final Logger logger = LoggerFactory.getLogger(CashActivityClient.class);

    public static void main(String[] args) {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
            .appKey(Env.APP_KEY)
            .appSecret(Env.APP_SECRET)
            .regionId(Env.REGION_ID)
            .build();
        TradeClientV3 apiService = new TradeClientV3(apiConfig);

        // Get account
        List<Account> accounts = apiService.listAccount();
        logger.info("Accounts: {}", accounts);
        String accountId = null;
        if (CollectionUtils.isNotEmpty(accounts)) {
            accountId = accounts.get(0).getAccountId();
        }
        if (StringUtils.isBlank(accountId)) {
            logger.info("Account id is empty.");
            return;
        }

        // Get all cash activities with default page size
        List<Activity> activities = apiService.getCashActivities(accountId, null, null, null, null, null);
        logger.info("cashActivities: {}", activities);

        // Get cash activities filtered by type and time range
        List<Activity> filteredActivities = apiService.getCashActivities(
            accountId, "TRADE", "2026-06-30T12:00:00.000Z", "2026-07-03T12:00:00.000Z", null, 20);
        logger.info("filteredCashActivities: {}", filteredActivities);

        // Cursor-based pagination example
        if (CollectionUtils.isNotEmpty(activities)) {
            String lastId = activities.get(activities.size() - 1).getId();
            List<Activity> nextPage = apiService.getCashActivities(
                accountId, null, null, null, lastId, 10);
            logger.info("nextPageCashActivities: {}", nextPage);
        }
    }
}
