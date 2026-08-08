package com.webull.openapi.samples.trade;

import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.TradeClientV3;
import com.webull.openapi.trade.response.v3.Account;
import com.webull.openapi.trade.response.v3.OrderExecution;

import java.util.List;

public class OrderExecutionClient {

    private static final Logger logger = LoggerFactory.getLogger(OrderExecutionClient.class);

    public static void main(String[] args) {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
            .appKey(Env.APP_KEY)
            .appSecret(Env.APP_SECRET)
            .regionId(Env.REGION_ID)
//                 .endpoint("<optional_api_endpoint>")
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

        // Get order executions filtered by date range
        List<OrderExecution> filteredExecutions = apiService.getOrderExecutions(
            accountId, null, "2026-06-20", "2026-06-22", null, null);
        logger.info("filteredOrderExecutions: {}", filteredExecutions);

        // Get order executions for a specific order
        List<OrderExecution> orderSpecific = apiService.getOrderExecutions(
            accountId, "<your_client_order_id>", null, null, null, null);
        logger.info("orderSpecificExecutions: {}", orderSpecific);

        // Cursor-based pagination example
        if (CollectionUtils.isNotEmpty(filteredExecutions)) {
            String lastId = filteredExecutions.get(filteredExecutions.size() - 1).getExecutionId();
            List<OrderExecution> nextPage = apiService.getOrderExecutions(
                accountId, null, null, null, lastId, 10);
            logger.info("nextPageOrderExecutions: {}", nextPage);
        }
    }
}
