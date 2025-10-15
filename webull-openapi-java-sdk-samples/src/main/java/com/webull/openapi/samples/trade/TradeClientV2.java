package com.webull.openapi.samples.trade;

import com.webull.openapi.core.common.dict.Category;
import com.webull.openapi.core.common.dict.ComboType;
import com.webull.openapi.core.common.dict.EntrustType;
import com.webull.openapi.core.common.dict.InstrumentSuperType;
import com.webull.openapi.core.common.dict.Markets;
import com.webull.openapi.core.common.dict.OptionStrategy;
import com.webull.openapi.core.common.dict.OptionType;
import com.webull.openapi.core.common.dict.OrderSide;
import com.webull.openapi.core.common.dict.OrderTIF;
import com.webull.openapi.core.common.dict.OrderType;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.request.v2.OptionOrder;
import com.webull.openapi.trade.request.v2.OptionOrderItem;
import com.webull.openapi.trade.request.v2.OptionOrderItemLeg;
import com.webull.openapi.trade.request.v2.TradeOrder;
import com.webull.openapi.trade.request.v2.TradeOrderItem;
import com.webull.openapi.trade.response.v2.Account;
import com.webull.openapi.trade.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.response.v2.OrderHistory;
import com.webull.openapi.trade.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.response.v2.TradeOrderResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webull.openapi.core.common.Headers.CATEGORY_KEY;

public class TradeClientV2 {
    private static final Logger logger = LoggerFactory.getLogger(TradeClientV2.class);

    public static void main(String[] args) throws InterruptedException {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                // .endpoint("<api_endpoint>")
                .build();
        com.webull.openapi.trade.TradeClientV2 apiService = new com.webull.openapi.trade.TradeClientV2(apiConfig);

        // get account list
        List<Account> accounts = apiService.listAccount();
        logger.info("Accounts: {}", accounts);

        String accountId = null;
        if (CollectionUtils.isNotEmpty(accounts)) {
            accountId = accounts.get(0).getAccountId();
        }

        AccountBalanceInfo accountBalanceInfo = apiService.balanceAccount(accountId);
        logger.info("balanceBase: {}", accountBalanceInfo);

        List<AccountPositionsInfo> accountPositionsInfos = apiService.positionsAccount(accountId);
        logger.info("accountPositions: {}", accountPositionsInfos);

        TradeOrder tradeOrder = new TradeOrder();
        List<TradeOrderItem> newOrders = new ArrayList<>();
        String clientOrderId = GUID.get();

        TradeOrderItem placeOne = new TradeOrderItem();
        newOrders.add(placeOne);
        placeOne.setSymbol("AAPL");
        placeOne.setInstrumentType(InstrumentSuperType.EQUITY.name());
        placeOne.setMarket(Markets.US.name());
        placeOne.setOrderType(OrderType.LIMIT.name());
        placeOne.setQuantity("1");
        placeOne.setLimitPrice("190");
        placeOne.setSupportTradingSession("N");
        placeOne.setSide(OrderSide.BUY.name());
        placeOne.setTimeInForce(OrderTIF.GTC.name());
        placeOne.setEntrustType(EntrustType.QTY.name());
        placeOne.setClientOrderId(clientOrderId);
//            placeOne.setSenderSubId("123456");
//            List<NoPartyId> noPartyIds = new ArrayList<>();
//            NoPartyId partyId = new NoPartyId();
//            partyId.setPartyId("BNG144.666555");
//            partyId.setPartyIdSource("D");
//            partyId.setPartyRole("3");
//            noPartyIds.add(partyId);
//            placeOne.setNoPartyIds(noPartyIds);

        // only jp need input accountTaxType
        // placeOne.setAccountTaxType(AccountTaxType.GENERAL.name());
        tradeOrder.setNewOrders(newOrders);

        logger.info("previewOrderRequest: {}", tradeOrder);
        PreviewOrderResponse previewOrderResponse = apiService.previewOrder(accountId, tradeOrder);
        logger.info("previewOrderResponse: {}", previewOrderResponse);

        // This is an optional feature; you can still make a request without setting it.
        Map<String,String> customHeadersMap = new HashMap<>();
        customHeadersMap.put(CATEGORY_KEY, Category.US_STOCK.name());
        apiService.addCustomHeaders(customHeadersMap);
        logger.info("placeOrderRequest: {}", tradeOrder);
        TradeOrderResponse tradePlaceOrderResponse = apiService.placeOrder(accountId, tradeOrder);
        apiService.removeCustomHeaders();
        logger.info("placeOrderResponse: {}", tradePlaceOrderResponse);
        doSleep();

        TradeOrder modifyTradeOrder = new TradeOrder();
        List<TradeOrderItem> modifyOrders = new ArrayList<>();
        TradeOrderItem modifyOne = new TradeOrderItem();
        modifyOne.setClientOrderId(tradePlaceOrderResponse.getClientOrderId());
        modifyOne.setLimitPrice("180");
        modifyOne.setQuantity("2");
        modifyOrders.add(modifyOne);
        modifyTradeOrder.setModifyOrders(modifyOrders);
        logger.info("replaceOrderRequest: {}", tradeOrder);
        TradeOrderResponse tradeReplaceOrderResponse = apiService.replaceOrder(accountId, modifyTradeOrder);
        logger.info("replaceOrderResponse: {}", tradeReplaceOrderResponse);
        doSleep();

        TradeOrder cancelTradeOrder = new TradeOrder();
        cancelTradeOrder.setClientOrderId(tradePlaceOrderResponse.getClientOrderId());
        logger.info("cancelOrderRequest: {}", cancelTradeOrder);
        TradeOrderResponse tradeCancelOrderResponse = apiService.cancelOrder(accountId, cancelTradeOrder);
        logger.info("cancelOrderResponse: {}", tradeCancelOrderResponse);
        doSleep();

        List<OrderHistory> listOrdersResponse = apiService.listOrders(accountId, 10, "2024-09-25", null, null);
        logger.info("listOrdersResponse: {}", listOrdersResponse);

        List<OrderHistory> openOrdersResponse = apiService.openOrders(accountId, 10, null);
        logger.info("openOrdersResponse: {}", openOrdersResponse);

        OrderHistory orderDetailResponse = apiService.getOrderDetails(accountId, clientOrderId);
        logger.info("orderDetailResponse: {}", orderDetailResponse);




        // Options
        String optionClientOrderId = GUID.get();
        OptionOrderItemLeg optionOrderItemLeg = new OptionOrderItemLeg();
        optionOrderItemLeg.setSide(OrderSide.BUY.name());
        optionOrderItemLeg.setQuantity("1");
        optionOrderItemLeg.setSymbol("AAPL");
        optionOrderItemLeg.setStrikePrice("250");
        optionOrderItemLeg.setInitExpDate("2025-12-19");
        optionOrderItemLeg.setInstrumentType(InstrumentSuperType.OPTION.name());
        optionOrderItemLeg.setOptionType(OptionType.CALL.name());
        optionOrderItemLeg.setMarket(Markets.US.name());
        List<OptionOrderItemLeg> optionOrderItemLegList = new ArrayList<>();
        optionOrderItemLegList.add(optionOrderItemLeg);
        OptionOrderItem optionOrderItem = new OptionOrderItem();
        optionOrderItem.setClientOrderId(optionClientOrderId);
        optionOrderItem.setComboType(ComboType.NORMAL.name());
        optionOrderItem.setOptionStrategy(OptionStrategy.SINGLE.name());
        optionOrderItem.setSide(OrderSide.BUY.name());
        optionOrderItem.setOrderType(OrderType.LIMIT.name());
        optionOrderItem.setTimeInForce(OrderTIF.GTC.name());
        optionOrderItem.setLimitPrice("2");
        optionOrderItem.setQuantity("1");
        optionOrderItem.setEntrustType(EntrustType.QTY.name());
        optionOrderItem.setOrders(optionOrderItemLegList);
        List<OptionOrderItem> optionOrderItemList = new ArrayList<>();
        optionOrderItemList.add(optionOrderItem);
        OptionOrder optionOrder = new OptionOrder();
        optionOrder.setNewOrders(optionOrderItemList);

        logger.info("previewOptionRequest: {}", optionOrder);
        PreviewOrderResponse previewOptionResponse = apiService.previewOption(accountId, optionOrder);
        logger.info("previewOptionResponse: {}", previewOptionResponse);

        logger.info("placeOptionRequest: {}", optionOrder);
        // This is an optional feature; you can still make a request without setting it.
        Map<String,String> optionCustomHeadersMap = new HashMap<>();
        optionCustomHeadersMap.put(CATEGORY_KEY, Category.US_OPTION.name());
        apiService.addCustomHeaders(optionCustomHeadersMap);
        TradeOrderResponse placeOptionResponse = apiService.placeOption(accountId, optionOrder);
        apiService.removeCustomHeaders();
        logger.info("placeOptionResponse: {}", placeOptionResponse);
        doSleep();

        // This code is only applicable for single-leg options modification operations.
        OptionOrderItemLeg optionReplaceItemLeg = new OptionOrderItemLeg();
        optionReplaceItemLeg.setQuantity("2");
        optionReplaceItemLeg.setClientOrderId(optionOrderItem.getClientOrderId());
        List<OptionOrderItemLeg> optionReplaceItemLegList = new ArrayList<>();
        optionReplaceItemLegList.add(optionReplaceItemLeg);
        OptionOrderItem optionReplaceItem = new OptionOrderItem();
        optionReplaceItem.setClientOrderId(optionOrderItem.getClientOrderId());
        optionReplaceItem.setLimitPrice("3");
        optionReplaceItem.setQuantity("2");
        optionReplaceItem.setOrders(optionReplaceItemLegList);
        List<OptionOrderItem> optionReplaceItemList = new ArrayList<>();
        optionReplaceItemList.add(optionReplaceItem);
        OptionOrder optionReplace = new OptionOrder();
        optionReplace.setModifyOrders(optionReplaceItemList);
        logger.info("replaceOptionRequest: {}", optionReplace);
        TradeOrderResponse replaceOptionResponse = apiService.replaceOption(accountId, optionReplace);
        logger.info("replaceOptionResponse: {}", replaceOptionResponse);
        doSleep();

        OptionOrder cancelTradeOption = new OptionOrder();
        cancelTradeOption.setClientOrderId(optionOrderItem.getClientOrderId());
        logger.info("cancelOptionRequest: {}", cancelTradeOption);
        TradeOrderResponse cancelOptionResponse = apiService.cancelOption(accountId, cancelTradeOption);
        logger.info("cancelOptionResponse: {}", cancelOptionResponse);
        doSleep();

        listOrdersResponse = apiService.listOrders(accountId, 10, "2024-09-25", null, null);
        logger.info("listOrdersResponse: {}", listOrdersResponse);

        OrderHistory optionDetailResponse = apiService.getOrderDetails(accountId, optionOrderItem.getClientOrderId());
        logger.info("optionDetailResponse: {}", optionDetailResponse);

    }

    private static void doSleep() throws InterruptedException {

        long ticker = 5;
        int waitTime = 1;
        long startTime = System.currentTimeMillis();
        while (true) {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            if (elapsed >= ticker) {
                logger.info("Wait completed, start next step...");
                break;
            }
            logger.info("Waiting {} seconds before starting the next step... (elapsed {}s / {}s)", waitTime, elapsed, ticker);
            Thread.sleep(waitTime * 1000L);
        }

    }

}