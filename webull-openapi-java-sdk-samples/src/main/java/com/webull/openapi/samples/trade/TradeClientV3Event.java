package com.webull.openapi.samples.trade;

import com.webull.openapi.core.common.dict.*;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.request.v3.TradeOrder;
import com.webull.openapi.trade.request.v3.TradeOrderItem;
import com.webull.openapi.trade.response.v3.*;

import java.util.ArrayList;
import java.util.List;

public class TradeClientV3Event {
    private static final Logger logger = LoggerFactory.getLogger(TradeClientV3Event.class);
    private static final String EVENTS_CASH = "EVENTS_CASH";

    public static void main(String[] args) throws InterruptedException {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                // .endpoint("<api_endpoint>")
                /*
                 * The tokenDir parameter can be used to specify the directory for storing the 2FA token. Both absolute and relative paths are supported and this option has the highest priority.
                 * Alternatively, the storage directory can be configured via an environment variable with the key WEBULL_OPENAPI_TOKEN_DIR, which also supports both absolute and relative paths.
                 * If neither is specified, the default configuration will be used, and the token will be stored at conf/token.txt under the current working directory.
                 */
                //.tokenDir("conf_custom_relative")
                .build();
        com.webull.openapi.trade.TradeClientV3 apiService =
                new com.webull.openapi.trade.TradeClientV3(apiConfig);

        List<Account> accounts = apiService.listAccount();
        String accountId = null;
        if (CollectionUtils.isNotEmpty(accounts)) {
            accountId = accounts.stream()
                    .filter(account -> account != null && StringUtils.isNotBlank(account.getAccountClass()))
                    .filter(account -> EVENTS_CASH.equalsIgnoreCase(account.getAccountClass()))
                    .findFirst()
                    .map(Account::getAccountId)
                    .orElse(null);
        }
        if (StringUtils.isBlank(accountId)) {
            logger.info("event accountId is null");
            return;
        }
        AccountBalanceInfo accountBalanceInfo =
                apiService.balanceAccount(accountId);
        logger.info("accountBalance: {}", accountBalanceInfo);

        List<AccountPositionsInfo> accountPositionsInfos =
                apiService.positionsAccount(accountId);
        logger.info("accountPositions: {}", accountPositionsInfos);

        TradeOrder newNormalEquityOrder = new TradeOrder();
        List<TradeOrderItem> newNormalEquityOrders = new ArrayList<>();

        TradeOrderItem normalEquityOrder = new TradeOrderItem();
        newNormalEquityOrders.add(normalEquityOrder);
        normalEquityOrder.setComboType(ComboType.NORMAL.name());
        normalEquityOrder.setClientOrderId(GUID.get());
        normalEquityOrder.setInstrumentType(InstrumentSuperType.EVENT.name());
        normalEquityOrder.setMarket(Markets.US.name());
        normalEquityOrder.setSymbol("KXFEDDECISION-26SEP-C25");
        normalEquityOrder.setOrderType(OrderType.LIMIT.name());
        normalEquityOrder.setEntrustType(EntrustType.QTY.name());
        normalEquityOrder.setTimeInForce(OrderTIF.DAY.name());
        normalEquityOrder.setSide(OrderSide.BUY.name());
        normalEquityOrder.setQuantity("1");
        normalEquityOrder.setLimitPrice("0.1");
        normalEquityOrder.setEventOutcome("yes");
        newNormalEquityOrder.setNewOrders(newNormalEquityOrders);

        PreviewOrderResponse previewNormalEquityResponse =
                apiService.previewOrder(accountId, newNormalEquityOrder);
        logger.info("previewNormalEquityResponse: {}", previewNormalEquityResponse);

        TradeOrderResponse placeNormalEquityResponse =
                apiService.placeOrder(accountId, newNormalEquityOrder);
        logger.info("placeNormalEquityResponse: {}", placeNormalEquityResponse);
        doSleep();

        TradeOrder replaceNormalEquityOrder = new TradeOrder();
        List<TradeOrderItem> replaceNormalEquityOrders = new ArrayList<>();
        TradeOrderItem replaceEquityOrder = new TradeOrderItem();
        replaceEquityOrder.setClientOrderId(normalEquityOrder.getClientOrderId());
        replaceEquityOrder.setQuantity("10");
        replaceEquityOrder.setLimitPrice("0.1");
        replaceNormalEquityOrders.add(replaceEquityOrder);
        replaceNormalEquityOrder.setModifyOrders(replaceNormalEquityOrders);

        TradeOrderResponse replaceNormalEquityResponse =
                apiService.replaceOrder(accountId, replaceNormalEquityOrder);
        logger.info("replaceNormalEquityResponse: {}", replaceNormalEquityResponse);
        doSleep();

        TradeOrder cancelNormalEquityOrder = new TradeOrder();
        cancelNormalEquityOrder.setClientOrderId(normalEquityOrder.getClientOrderId());
        TradeOrderResponse cancelNormalEquityResponse =
                apiService.cancelOrder(accountId, cancelNormalEquityOrder);
        logger.info("cancelNormalEquityResponse: {}", cancelNormalEquityResponse);

        List<OrderHistory> listOrdersResponse =
                apiService.listOrders(accountId, 10, null, null, null);
        logger.info("listOrdersResponse: {}", listOrdersResponse);

        List<OrderHistory> openOrdersResponse =
                apiService.openOrders(accountId, 10, null);
        logger.info("openOrdersResponse: {}", openOrdersResponse);

        OrderHistory orderDetailResponse =
                apiService.getOrderDetails(accountId, normalEquityOrder.getClientOrderId());
        logger.info("orderDetailResponse: {}", orderDetailResponse);
    }

    private static void doSleep() throws InterruptedException {
        final int totalSeconds = 5;
        final int intervalSeconds = 1;

        for (int elapsed = intervalSeconds; elapsed <= totalSeconds; elapsed += intervalSeconds) {
            Thread.sleep(intervalSeconds * 1000L);
            logger.info(
                    "Waiting {} seconds before starting the next step... (elapsed {}s / {}s)",
                    intervalSeconds, elapsed, totalSeconds
            );
        }

        logger.info("Wait completed, start next step...");
    }

}