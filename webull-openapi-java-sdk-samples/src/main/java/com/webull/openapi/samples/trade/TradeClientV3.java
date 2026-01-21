package com.webull.openapi.samples.trade;

import com.webull.openapi.core.common.dict.*;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.request.v3.OptionOrderItemLeg;
import com.webull.openapi.trade.request.v3.TradeOrder;
import com.webull.openapi.trade.request.v3.TradeOrderItem;
import com.webull.openapi.trade.response.v3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TradeClientV3 {
    private static final Logger logger = LoggerFactory.getLogger(TradeClientV3.class);
    private static final String FUTURES = "FUTURES";
    private static final String CRYPTO = "CRYPTO";

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

        AccountIds accountIds = initAccount(apiService);
        if (CollectionUtils.isNotEmpty(accountIds.getSecurityAccountIds())) {
            runEquityNormalOrderExample(apiService, accountIds.getSecurityAccountIds().get(0));

            runEquityBatchOrderExample(apiService, accountIds.getSecurityAccountIds().get(0));

            runEquityComboOrderExample(apiService, accountIds.getSecurityAccountIds().get(0));

            runOptionOrderExample(apiService, accountIds.getSecurityAccountIds().get(0));

            runAlgoComboOrderExample(apiService, accountIds.getSecurityAccountIds().get(0));

        }

        if (CollectionUtils.isNotEmpty(accountIds.getCryptoAccountIds())) {
            runCryptoOrderExample(apiService, accountIds.getCryptoAccountIds().get(0));
        }

        if (CollectionUtils.isNotEmpty(accountIds.getFuturesAccountIds())) {
            runFutureOrderExample(apiService, accountIds.getFuturesAccountIds().get(0));
        }
    }

    private static AccountIds initAccount(com.webull.openapi.trade.TradeClientV3 apiService) {
        List<Account> accounts = apiService.listAccount();
        logger.info("Accounts: {}", accounts);

        AccountIds accountIds = new AccountIds();
        if (CollectionUtils.isNotEmpty(accounts)) {
            for (Account account : accounts) {
                if (account == null || StringUtils.isBlank(account.getAccountClass())) {
                    continue;
                }

                String accountClass = account.getAccountClass();
                String accountId = account.getAccountId();
                if (FUTURES.equalsIgnoreCase(accountClass)) {
                    accountIds.setFuturesAccountId(accountId);
                } else if (CRYPTO.equalsIgnoreCase(accountClass)) {
                    accountIds.setCryptoAccountId(accountId);
                } else {
                    accountIds.setSecuritiesAccountId(accountId);
                }

                AccountBalanceInfo accountBalanceInfo =
                    apiService.balanceAccount(accountId);
                logger.info("accountBalance: {}", accountBalanceInfo);

                List<AccountPositionsInfo> accountPositionsInfos =
                    apiService.positionsAccount(accountId);
                logger.info("accountPositions: {}", accountPositionsInfos);
            }
        }
        return accountIds;
    }

    private static void runEquityBatchOrderExample(
        com.webull.openapi.trade.TradeClientV3 apiService,
        String accountId
    ) throws InterruptedException {
        TradeOrder newBatchOrder = new TradeOrder();
        List<TradeOrderItem> batchOrders = new ArrayList<>();
        TradeOrderItem normalEquityOrder = new TradeOrderItem();
        normalEquityOrder.setClientOrderId(GUID.get());
        normalEquityOrder.setComboType(ComboType.NORMAL.name());
        normalEquityOrder.setSymbol("AAPL");
        normalEquityOrder.setInstrumentType(InstrumentSuperType.EQUITY.name());
        normalEquityOrder.setMarket(Markets.US.name());
        normalEquityOrder.setOrderType(OrderType.LIMIT.name());
        normalEquityOrder.setQuantity("1");
        normalEquityOrder.setLimitPrice("280");
        normalEquityOrder.setSupportTradingSession("CORE");
        normalEquityOrder.setSide(OrderSide.BUY.name());
        normalEquityOrder.setTimeInForce(OrderTIF.DAY.name());
        normalEquityOrder.setEntrustType(EntrustType.QTY.name());
        batchOrders.add(normalEquityOrder);
        newBatchOrder.setBatchOrders(batchOrders);

        TradeBatchPlaceResponse placeNormalEquityResponse =
                apiService.batchPlaceOrder(accountId, newBatchOrder);
        logger.info("batchPlaceNormalEquityResponse:{}", placeNormalEquityResponse);
    }

    private static void runEquityNormalOrderExample(
            com.webull.openapi.trade.TradeClientV3 apiService,
            String accountId
    ) throws InterruptedException {
        TradeOrder newNormalEquityOrder = new TradeOrder();
        List<TradeOrderItem> newNormalEquityOrders = new ArrayList<>();

        TradeOrderItem normalEquityOrder = new TradeOrderItem();
        newNormalEquityOrders.add(normalEquityOrder);
        normalEquityOrder.setClientOrderId(GUID.get());
        normalEquityOrder.setComboType(ComboType.NORMAL.name());
        normalEquityOrder.setSymbol("AAPL");
        normalEquityOrder.setInstrumentType(InstrumentSuperType.EQUITY.name());
        normalEquityOrder.setMarket(Markets.US.name());
        normalEquityOrder.setOrderType(OrderType.LIMIT.name());
        normalEquityOrder.setQuantity("1");
        normalEquityOrder.setLimitPrice("280");
        normalEquityOrder.setSupportTradingSession("N");
        normalEquityOrder.setSide(OrderSide.BUY.name());
        normalEquityOrder.setTimeInForce(OrderTIF.GTC.name());
        normalEquityOrder.setEntrustType(EntrustType.QTY.name());
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
        replaceEquityOrder.setLimitPrice("275");
        replaceEquityOrder.setQuantity("2");
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

    private static void runEquityComboOrderExample(
        com.webull.openapi.trade.TradeClientV3 apiService,
        String accountId
    ) throws InterruptedException {
        TradeOrder newComboEquityOrder = new TradeOrder();
        List<TradeOrderItem> newComboEquityOrders = new ArrayList<>();

        TradeOrderItem comboEquityMaster = new TradeOrderItem();
        comboEquityMaster.setClientOrderId(GUID.get());
        comboEquityMaster.setComboType(ComboType.MASTER.name());
        comboEquityMaster.setSymbol("F");
        comboEquityMaster.setInstrumentType(InstrumentSuperType.EQUITY.name());
        comboEquityMaster.setMarket(Markets.US.name());
        comboEquityMaster.setOrderType(OrderType.LIMIT.name());
        comboEquityMaster.setQuantity("1");
        comboEquityMaster.setLimitPrice("10.5");
        comboEquityMaster.setSupportTradingSession("N");
        comboEquityMaster.setSide(OrderSide.BUY.name());
        comboEquityMaster.setTimeInForce(OrderTIF.DAY.name());
        comboEquityMaster.setEntrustType(EntrustType.QTY.name());

        TradeOrderItem comboEquityStopProfit = new TradeOrderItem();
        comboEquityStopProfit.setClientOrderId(GUID.get());
        comboEquityStopProfit.setComboType(ComboType.STOP_PROFIT.name());
        comboEquityStopProfit.setSymbol("F");
        comboEquityStopProfit.setInstrumentType(InstrumentSuperType.EQUITY.name());
        comboEquityStopProfit.setMarket(Markets.US.name());
        comboEquityStopProfit.setOrderType(OrderType.LIMIT.name());
        comboEquityStopProfit.setQuantity("1");
        comboEquityStopProfit.setLimitPrice("11.5");
        comboEquityStopProfit.setSupportTradingSession("N");
        comboEquityStopProfit.setSide(OrderSide.SELL.name());
        comboEquityStopProfit.setTimeInForce(OrderTIF.DAY.name());
        comboEquityStopProfit.setEntrustType(EntrustType.QTY.name());

        TradeOrderItem comboEquityStopLoss = new TradeOrderItem();
        comboEquityStopLoss.setClientOrderId(GUID.get());
        comboEquityStopLoss.setComboType(ComboType.STOP_LOSS.name());
        comboEquityStopLoss.setSymbol("F");
        comboEquityStopLoss.setInstrumentType(InstrumentSuperType.EQUITY.name());
        comboEquityStopLoss.setMarket(Markets.US.name());
        comboEquityStopLoss.setOrderType(OrderType.STOP_LOSS.name());
        comboEquityStopLoss.setQuantity("1");
        comboEquityStopLoss.setStopPrice("9.5");
        comboEquityStopLoss.setSupportTradingSession("N");
        comboEquityStopLoss.setSide(OrderSide.SELL.name());
        comboEquityStopLoss.setTimeInForce(OrderTIF.DAY.name());
        comboEquityStopLoss.setEntrustType(EntrustType.QTY.name());

        newComboEquityOrders.add(comboEquityMaster);
        newComboEquityOrders.add(comboEquityStopProfit);
        newComboEquityOrders.add(comboEquityStopLoss);
        newComboEquityOrder.setNewOrders(newComboEquityOrders);

        PreviewOrderResponse previewComboEquityResponse =
            apiService.previewOrder(accountId, newComboEquityOrder);
        logger.info("previewComboEquityResponse: {}", previewComboEquityResponse);

        TradeOrderResponse placeComboEquityResponse =
            apiService.placeOrder(accountId, newComboEquityOrder);
        logger.info("placeComboEquityResponse: {}", placeComboEquityResponse);
        doSleep();

        TradeOrder replaceComboEquityOrder = new TradeOrder();
        List<TradeOrderItem> replaceComboEquityOrders = new ArrayList<>();

        TradeOrderItem replaceEquityMaster = new TradeOrderItem();
        replaceEquityMaster.setClientOrderId(comboEquityMaster.getClientOrderId());
        replaceEquityMaster.setLimitPrice("10.8");
        replaceEquityMaster.setQuantity("2");

        TradeOrderItem replaceEquityStopProfit = new TradeOrderItem();
        replaceEquityStopProfit.setClientOrderId(comboEquityStopProfit.getClientOrderId());
        replaceEquityStopProfit.setLimitPrice("11.8");
        replaceEquityStopProfit.setQuantity("2");

        TradeOrderItem replaceEquityStopLoss = new TradeOrderItem();
        replaceEquityStopLoss.setClientOrderId(comboEquityStopLoss.getClientOrderId());
        replaceEquityStopLoss.setStopPrice("9.8");
        replaceEquityStopLoss.setQuantity("2");

        replaceComboEquityOrders.add(replaceEquityMaster);
        replaceComboEquityOrders.add(replaceEquityStopProfit);
        replaceComboEquityOrders.add(replaceEquityStopLoss);
        replaceComboEquityOrder.setModifyOrders(replaceComboEquityOrders);

        TradeOrderResponse replaceComboEquityResponse =
            apiService.replaceOrder(accountId, replaceComboEquityOrder);
        logger.info("replaceComboEquityResponse: {}", replaceComboEquityResponse);
        doSleep();

        TradeOrder cancelComboEquityOrder = new TradeOrder();
        cancelComboEquityOrder.setClientOrderId(comboEquityMaster.getClientOrderId());
        TradeOrderResponse cancelComboEquityResponse =
            apiService.cancelOrder(accountId, cancelComboEquityOrder);
        logger.info("cancelComboEquityResponse: {}", cancelComboEquityResponse);

        List<OrderHistory> listComboOrdersResponse =
            apiService.listOrders(accountId, 10, null, null, null);
        logger.info("listComboOrdersResponse: {}", listComboOrdersResponse);

        OrderHistory masterOrderDetailResponse =
            apiService.getOrderDetails(accountId, comboEquityMaster.getClientOrderId());
        logger.info("masterOrderDetailResponse: {}", masterOrderDetailResponse);
    }

    private static void runOptionOrderExample(
        com.webull.openapi.trade.TradeClientV3 apiService,
        String accountId
    ) throws InterruptedException {
        OptionOrderItemLeg normalOptionOrderLeg = new OptionOrderItemLeg();
        normalOptionOrderLeg.setSide(OrderSide.BUY.name());
        normalOptionOrderLeg.setQuantity("1");
        normalOptionOrderLeg.setSymbol("AAPL");
        normalOptionOrderLeg.setStrikePrice("250");
        normalOptionOrderLeg.setOptionExpireDate("2025-12-19");
        normalOptionOrderLeg.setInstrumentType(InstrumentSuperType.OPTION.name());
        normalOptionOrderLeg.setOptionType(OptionType.CALL.name());
        normalOptionOrderLeg.setMarket(Markets.US.name());
        List<OptionOrderItemLeg> normalOtionOrderLegList = new ArrayList<>();
        normalOtionOrderLegList.add(normalOptionOrderLeg);

        TradeOrderItem normalOptionOrder = new TradeOrderItem();
        normalOptionOrder.setClientOrderId(GUID.get());
        normalOptionOrder.setComboType(ComboType.NORMAL.name());
        normalOptionOrder.setOptionStrategy(OptionStrategy.SINGLE.name());
        normalOptionOrder.setSide(OrderSide.BUY.name());
        normalOptionOrder.setOrderType(OrderType.LIMIT.name());
        normalOptionOrder.setTimeInForce(OrderTIF.GTC.name());
        normalOptionOrder.setLimitPrice("20.5");
        normalOptionOrder.setQuantity("1");
        normalOptionOrder.setEntrustType(EntrustType.QTY.name());
        normalOptionOrder.setLegs(normalOtionOrderLegList);

        List<TradeOrderItem> newNormalOptionOrders = new ArrayList<>();
        newNormalOptionOrders.add(normalOptionOrder);
        TradeOrder newNormalOptionOrder = new TradeOrder();
        newNormalOptionOrder.setNewOrders(newNormalOptionOrders);

        logger.info("previewNormalOptionRequest: {}", newNormalOptionOrder);
        PreviewOrderResponse previewNormalOptionResponse =
            apiService.previewOrder(accountId, newNormalOptionOrder);
        logger.info("previewNormalOptionResponse: {}", previewNormalOptionResponse);

        logger.info("placeNormalOptionRequest: {}", newNormalOptionOrder);
        TradeOrderResponse placeNormalOptionResponse =
            apiService.placeOrder(accountId, newNormalOptionOrder);
        logger.info("placeNormalOptionResponse: {}", placeNormalOptionResponse);
        doSleep();

        OrderHistory orderDetailResponse =
            apiService.getOrderDetails(accountId, normalOptionOrder.getClientOrderId());
        logger.info("orderDetailResponse: {}", orderDetailResponse);

        String legId = null;
        if (Objects.nonNull(orderDetailResponse)
            && CollectionUtils.isNotEmpty(orderDetailResponse.getOrders())
            && Objects.nonNull(orderDetailResponse.getOrders().get(0))
            && CollectionUtils.isNotEmpty(orderDetailResponse.getOrders().get(0).getLegs())
            && Objects.nonNull(orderDetailResponse.getOrders().get(0).getLegs().get(0))) {
            legId = orderDetailResponse.getOrders().get(0).getLegs().get(0).getId();
        }
        logger.info("orderDetailResponse leg id: {}", legId);

        if (StringUtils.isNotBlank(legId)) {
            OptionOrderItemLeg replaceOptionOrderLeg = new OptionOrderItemLeg();
            replaceOptionOrderLeg.setQuantity("2");
            replaceOptionOrderLeg.setId(legId);
            List<OptionOrderItemLeg> replaceOptionOrderLegs = new ArrayList<>();
            replaceOptionOrderLegs.add(replaceOptionOrderLeg);

            TradeOrderItem replaceOptionOrder = new TradeOrderItem();
            replaceOptionOrder.setClientOrderId(normalOptionOrder.getClientOrderId());
            replaceOptionOrder.setLimitPrice("20");
            replaceOptionOrder.setQuantity("2");
            replaceOptionOrder.setLegs(replaceOptionOrderLegs);

            List<TradeOrderItem> replaceNormalOptionOrders = new ArrayList<>();
            replaceNormalOptionOrders.add(replaceOptionOrder);
            TradeOrder replaceNormalOptionOrder = new TradeOrder();
            replaceNormalOptionOrder.setModifyOrders(replaceNormalOptionOrders);

            logger.info("replaceNormalOptionRequest: {}", replaceNormalOptionOrder);
            TradeOrderResponse replaceNormalOptionResponse =
                apiService.replaceOrder(accountId, replaceNormalOptionOrder);
            logger.info("replaceNormalOptionResponse: {}", replaceNormalOptionResponse);
            doSleep();
        }

        TradeOrder cancelNormalOptionOrder = new TradeOrder();
        cancelNormalOptionOrder.setClientOrderId(normalOptionOrder.getClientOrderId());
        logger.info("cancelNormalOptionRequest: {}", cancelNormalOptionOrder);
        TradeOrderResponse cancelNormalOptionResponse =
            apiService.cancelOrder(accountId, cancelNormalOptionOrder);
        logger.info("cancelNormalOptionResponse: {}", cancelNormalOptionResponse);

        List<OrderHistory> optionHistoryResponse =
            apiService.listOrders(accountId, 10, null, null, null);
        logger.info("optionHistoryResponse: {}", optionHistoryResponse);

        OrderHistory optionDetailResponse =
            apiService.getOrderDetails(accountId, normalOptionOrder.getClientOrderId());
        logger.info("optionDetailResponse: {}", optionDetailResponse);
    }

    private static void runCryptoOrderExample(
        com.webull.openapi.trade.TradeClientV3 apiService,
        String accountId
    ) throws InterruptedException {
        TradeOrder newNormalCryptoOrder = new TradeOrder();
        List<TradeOrderItem> newNormalCryptoOrders = new ArrayList<>();

        TradeOrderItem normalCryptoOrder = new TradeOrderItem();
        newNormalCryptoOrders.add(normalCryptoOrder);
        normalCryptoOrder.setClientOrderId(GUID.get());
        normalCryptoOrder.setComboType(ComboType.NORMAL.name());
        normalCryptoOrder.setSymbol("BTCUSD");
        normalCryptoOrder.setInstrumentType(InstrumentSuperType.CRYPTO.name());
        normalCryptoOrder.setMarket(Markets.US.name());
        normalCryptoOrder.setOrderType(OrderType.LIMIT.name());
        normalCryptoOrder.setQuantity("1");
        normalCryptoOrder.setLimitPrice("80000");
        normalCryptoOrder.setSide(OrderSide.BUY.name());
        normalCryptoOrder.setTimeInForce(OrderTIF.DAY.name());
        normalCryptoOrder.setEntrustType(EntrustType.QTY.name());
        newNormalCryptoOrder.setNewOrders(newNormalCryptoOrders);

        TradeOrderResponse placeNormalCryptoResponse =
            apiService.placeOrder(accountId, newNormalCryptoOrder);
        logger.info("placeNormalCryptoResponse: {}", placeNormalCryptoResponse);
        doSleep();

        TradeOrder cancelNormalCryptoOrder = new TradeOrder();
        cancelNormalCryptoOrder.setClientOrderId(placeNormalCryptoResponse.getClientOrderId());
        TradeOrderResponse cancelNormalCryptoResponse =
            apiService.cancelOrder(accountId, cancelNormalCryptoOrder);
        logger.info("cancelNormalCryptoResponse: {}", cancelNormalCryptoResponse);

        List<OrderHistory> listOrdersResponse =
            apiService.listOrders(accountId, 10, null, null, null);
        logger.info("listOrdersResponse: {}", listOrdersResponse);

        List<OrderHistory> openOrdersResponse =
            apiService.openOrders(accountId, 10, null);
        logger.info("openOrdersResponse: {}", openOrdersResponse);

        OrderHistory orderDetailResponse =
            apiService.getOrderDetails(accountId, normalCryptoOrder.getClientOrderId());
        logger.info("orderDetailResponse: {}", orderDetailResponse);
    }

    private static void runFutureOrderExample(com.webull.openapi.trade.TradeClientV3 apiService, String accountId) {
        TradeOrder newFuturesOrder = new TradeOrder();
        List<TradeOrderItem> newFuturesOrders = new ArrayList<>();
        TradeOrderItem normalFuturesOrder = new TradeOrderItem();
        newFuturesOrders.add(normalFuturesOrder);

        normalFuturesOrder.setClientOrderId(GUID.get());
        normalFuturesOrder.setComboType(ComboType.NORMAL.name());
        normalFuturesOrder.setSymbol("ESZ5");
        normalFuturesOrder.setInstrumentType(InstrumentSuperType.FUTURES.name());
        normalFuturesOrder.setMarket(Markets.US.name());
        normalFuturesOrder.setOrderType(OrderType.LIMIT.name());
        normalFuturesOrder.setQuantity("1");
        normalFuturesOrder.setLimitPrice("46000");
        normalFuturesOrder.setSide(OrderSide.BUY.name());
        normalFuturesOrder.setTimeInForce(OrderTIF.DAY.name());
        normalFuturesOrder.setEntrustType(EntrustType.QTY.name());
        newFuturesOrder.setNewOrders(newFuturesOrders);
        logger.info("newFuturesOrder: {}", newFuturesOrder);

        // Preview Futures Order
        PreviewOrderResponse previewFuturesResponse = apiService.previewOrder(accountId, newFuturesOrder);
        logger.info("previewFuturesResponse: {}", previewFuturesResponse);

        // Place Futures Order
        TradeOrderResponse placeFuturesResponse = apiService.placeOrder(accountId, newFuturesOrder);
        logger.info("placeFuturesResponse: {}", placeFuturesResponse);

        // Replace Futures Order
        TradeOrder replaceFuturesOrder = new TradeOrder();
        List<TradeOrderItem> replaceFuturesOrders = new ArrayList<>();
        TradeOrderItem replaceFuturesOrderItem = new TradeOrderItem();
        replaceFuturesOrders.add(replaceFuturesOrderItem);

        replaceFuturesOrderItem.setClientOrderId(normalFuturesOrder.getClientOrderId());
        replaceFuturesOrderItem.setLimitPrice("46000");
        replaceFuturesOrderItem.setQuantity("2");
        replaceFuturesOrder.setModifyOrders(replaceFuturesOrders);
        logger.info("replaceFuturesOrder: {}", replaceFuturesOrder);
        TradeOrderResponse replaceFuturesResponse = apiService.replaceOrder(accountId, replaceFuturesOrder);
        logger.info("replaceFuturesResponse: {}", replaceFuturesResponse);

        // Cancel Futures Order
        TradeOrder cancelFuturesOrder = new TradeOrder();
        cancelFuturesOrder.setClientOrderId(normalFuturesOrder.getClientOrderId());
        TradeOrderResponse tradeOrderResponse = apiService.cancelOrder(accountId, cancelFuturesOrder);
        logger.info("cancelNormalFuturesResponse: {}", tradeOrderResponse);

        // List Futures Orders
        List<OrderHistory> listOrdersResponse = apiService.listOrders(accountId, 10, null, null, null);
        logger.info("listOrdersResponse: {}", listOrdersResponse);

        // Open Futures Orders
        List<OrderHistory> openOrdersResponse = apiService.openOrders(accountId, 10, null);
        logger.info("openOrdersResponse: {}", openOrdersResponse);

        // Get Futures Order Details
        OrderHistory orderDetailResponse = apiService.getOrderDetails(accountId, normalFuturesOrder.getClientOrderId());
        logger.info("orderDetailResponse: {}", orderDetailResponse);
    }

    private static void runAlgoComboOrderExample(
            com.webull.openapi.trade.TradeClientV3 apiService,
            String accountId
    ) throws InterruptedException {
        TradeOrder newComboEquityOrder = new TradeOrder();
        List<TradeOrderItem> newComboEquityOrders = new ArrayList<>();

        TradeOrderItem algoOrderItem = new TradeOrderItem();
        algoOrderItem.setClientOrderId(GUID.get());
        algoOrderItem.setComboType(ComboType.NORMAL.name());
        algoOrderItem.setSymbol("AAPL");
        algoOrderItem.setInstrumentType(InstrumentSuperType.EQUITY.name());
        algoOrderItem.setMarket(Markets.US.name());
        algoOrderItem.setOrderType(OrderType.LIMIT.name());
        algoOrderItem.setQuantity("1");
        algoOrderItem.setLimitPrice("200");
        algoOrderItem.setSupportTradingSession("CORE");
        algoOrderItem.setSide(OrderSide.BUY.name());
        algoOrderItem.setTimeInForce(OrderTIF.DAY.name());
        algoOrderItem.setEntrustType(EntrustType.QTY.name());
        algoOrderItem.setAlgoType(AlgoType.POV.name());
        algoOrderItem.setTargetVolPercent("10");
        algoOrderItem.setAlgoStartTime("16:00:00");
        algoOrderItem.setAlgoEndTime("23:00:00");
        newComboEquityOrders.add(algoOrderItem);
        newComboEquityOrder.setNewOrders(newComboEquityOrders);

        PreviewOrderResponse previewAlgoOrderResponse =
                apiService.previewOrder(accountId, newComboEquityOrder);
        logger.info("previewAlgoResponse: {}", previewAlgoOrderResponse);

        TradeOrderResponse placeAlgoOrderResponse =
                apiService.placeOrder(accountId, newComboEquityOrder);
        logger.info("placeAlgoOrderResponse: {}", placeAlgoOrderResponse);
        doSleep();

        TradeOrder replaceAlgoOrder = new TradeOrder();
        List<TradeOrderItem> replaceAlgoOrders = new ArrayList<>();

        TradeOrderItem replaceAlgoOrderItem = new TradeOrderItem();
        replaceAlgoOrderItem.setClientOrderId(algoOrderItem.getClientOrderId());
        algoOrderItem.setAlgoEndTime("23:30:00");
        replaceAlgoOrders.add(replaceAlgoOrderItem);
        replaceAlgoOrder.setModifyOrders(replaceAlgoOrders);

        TradeOrderResponse replaceAlgoResponse =
                apiService.replaceOrder(accountId, replaceAlgoOrder);
        logger.info("replaceAlgoResponse: {}", replaceAlgoResponse);
        doSleep();

        TradeOrder cancelAlgoOrder = new TradeOrder();
        cancelAlgoOrder.setClientOrderId(algoOrderItem.getClientOrderId());
        TradeOrderResponse cancelAlgoOrderResponse =
                apiService.cancelOrder(accountId, cancelAlgoOrder);
        logger.info("cancelAlgoOrderResponse: {}", cancelAlgoOrderResponse);

        List<OrderHistory> listComboOrdersResponse =
                apiService.listOrders(accountId, 10, null, null, null);
        logger.info("listComboOrdersResponse: {}", listComboOrdersResponse);

        OrderHistory masterOrderDetailResponse =
                apiService.getOrderDetails(accountId, algoOrderItem.getClientOrderId());
        logger.info("masterOrderDetailResponse: {}", masterOrderDetailResponse);
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

    public static class AccountIds {
        private final List<String> securityAccountIds = new ArrayList<>();
        private final List<String> futuresAccountIds = new ArrayList<>();
        private final List<String> cryptoAccountIds = new ArrayList<>();

        public List<String> getSecurityAccountIds() {
            return securityAccountIds;
        }

        public void setSecuritiesAccountId(String accountId) {
            this.securityAccountIds.add(accountId);
        }

        public List<String> getFuturesAccountIds() {
            return futuresAccountIds;
        }

        public void setFuturesAccountId(String accountId) {
            this.futuresAccountIds.add(accountId);
        }

        public List<String> getCryptoAccountIds() {
            return cryptoAccountIds;
        }

        public void setCryptoAccountId(String accountId) {
            this.cryptoAccountIds.add(accountId);
        }
    }

}