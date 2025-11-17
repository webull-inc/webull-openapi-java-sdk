package com.webull.openapi.samples.trade;

import com.webull.openapi.core.common.Region;
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
import com.webull.openapi.core.utils.StringUtils;
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
import java.util.Objects;

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
        logger.info("accountBalance: {}", accountBalanceInfo);

        List<AccountPositionsInfo> accountPositionsInfos = apiService.positionsAccount(accountId);
        logger.info("accountPositions: {}", accountPositionsInfos);

        TradeOrder tradeOrder = new TradeOrder();
        List<TradeOrderItem> newOrders = new ArrayList<>();

        TradeOrderItem placeOne = new TradeOrderItem();
        String clientOrderId = GUID.get();
        placeOne.setClientOrderId(clientOrderId);
        placeOne.setComboType(ComboType.NORMAL.name());
        newOrders.add(placeOne);
        placeOne.setSymbol("AAPL");
        placeOne.setInstrumentType(InstrumentSuperType.EQUITY.name());
        placeOne.setMarket(Region.us.name().toUpperCase());
        placeOne.setOrderType(OrderType.LIMIT.name());
        placeOne.setQuantity("1");
        placeOne.setLimitPrice("280");
        placeOne.setSupportTradingSession("N");
        placeOne.setSide(OrderSide.BUY.name());
        placeOne.setTimeInForce(OrderTIF.GTC.name());
        placeOne.setEntrustType(EntrustType.QTY.name());
        placeOne.setSenderSubId("123456");
            /*List<NoPartyId> noPartyIds = new ArrayList<>();
            NoPartyId partyId = new NoPartyId();
            partyId.setPartyId("BNG144.666555");
            partyId.setPartyIdSource("D");
            partyId.setPartyRole("3");
            noPartyIds.add(partyId);
            placeOne.setNoPartyIds(noPartyIds);*/

        // only jp need input accountTaxType
        // placeOne.setAccountTaxType(AccountTaxType.GENERAL.name());
        tradeOrder.setNewOrders(newOrders);

        PreviewOrderResponse previewOrderResponse = apiService.previewOrder(accountId, tradeOrder);
        logger.info("previewOrderResponse: {}", previewOrderResponse);

        // This is an optional feature; you can still make a request without setting it.
        Map<String,String> customHeadersMap = new HashMap<>();
        customHeadersMap.put(CATEGORY_KEY, Category.US_STOCK.name());
        apiService.addCustomHeaders(customHeadersMap);
        TradeOrderResponse placeOrderResponse = apiService.placeOrder(accountId, tradeOrder);
        apiService.removeCustomHeaders();
        logger.info("placeOrderResponse: {}", placeOrderResponse);
        Thread.sleep(3000L);

        TradeOrder modifyTradeOrder = new TradeOrder();
        List<TradeOrderItem> modifyOrders = new ArrayList<>();
        TradeOrderItem modifyOne = new TradeOrderItem();
        modifyOne.setClientOrderId(placeOrderResponse.getClientOrderId());
        modifyOne.setLimitPrice("275");
        modifyOne.setQuantity("2");
        modifyOrders.add(modifyOne);
        modifyTradeOrder.setModifyOrders(modifyOrders);
        TradeOrderResponse replaceOrderResponse = apiService.replaceOrder(accountId, modifyTradeOrder);
        logger.info("replaceOrderResponse: {}", replaceOrderResponse);
        Thread.sleep(3000L);

        TradeOrder cancelTradeOrder = new TradeOrder();
        cancelTradeOrder.setClientOrderId(placeOrderResponse.getClientOrderId());
        TradeOrderResponse cancelOrderResponse = apiService.cancelOrder(accountId, cancelTradeOrder);
        logger.info("cancelOrderResponse: {}", cancelOrderResponse);

        List<OrderHistory> listOrdersResponse = apiService.listOrders(accountId, 10, null, null, null, null);
        logger.info("listOrdersResponse: {}", listOrdersResponse);

            /*List<OrderHistory> openOrdersResponse = apiService.openOrders(accountId, 10, null, null);
            logger.info("openOrdersResponse: {}", openOrdersResponse);*/

        OrderHistory orderDetailResponse = apiService.getOrderDetails(accountId, clientOrderId);
        logger.info("orderDetailResponse: {}", orderDetailResponse);




        // Example of combination order if supported in your region.
        TradeOrder comboOrder = new TradeOrder();
        List<TradeOrderItem> newComboOrders = new ArrayList<>();

        TradeOrderItem comboMaster = new TradeOrderItem();
        String masterClientOrderId = GUID.get();
        comboMaster.setClientOrderId(masterClientOrderId);
        comboMaster.setComboType(ComboType.MASTER.name());
        comboMaster.setSymbol("F");
        comboMaster.setInstrumentType(InstrumentSuperType.EQUITY.name());
        comboMaster.setMarket("US");
        comboMaster.setOrderType(OrderType.LIMIT.name());
        comboMaster.setQuantity("1");
        comboMaster.setLimitPrice("10.5");
        comboMaster.setSupportTradingSession("N");
        comboMaster.setSide(OrderSide.BUY.name());
        comboMaster.setTimeInForce(OrderTIF.DAY.name());
        comboMaster.setEntrustType(EntrustType.QTY.name());

        TradeOrderItem comboStopProfit = new TradeOrderItem();
        String stopProfitClientOrderId = GUID.get();
        comboStopProfit.setClientOrderId(stopProfitClientOrderId);
        comboStopProfit.setComboType(ComboType.STOP_PROFIT.name());
        comboStopProfit.setSymbol("F");
        comboStopProfit.setInstrumentType(InstrumentSuperType.EQUITY.name());
        comboStopProfit.setMarket("US");
        comboStopProfit.setOrderType(OrderType.LIMIT.name());
        comboStopProfit.setQuantity("1");
        comboStopProfit.setLimitPrice("11.5");
        comboStopProfit.setSupportTradingSession("N");
        comboStopProfit.setSide(OrderSide.SELL.name());
        comboStopProfit.setTimeInForce(OrderTIF.DAY.name());
        comboStopProfit.setEntrustType(EntrustType.QTY.name());

        TradeOrderItem comboStopLoss = new TradeOrderItem();
        String stopLossClientOrderId = GUID.get();
        comboStopLoss.setClientOrderId(stopLossClientOrderId);
        comboStopLoss.setComboType(ComboType.STOP_LOSS.name());
        comboStopLoss.setSymbol("F");
        comboStopLoss.setInstrumentType(InstrumentSuperType.EQUITY.name());
        comboStopLoss.setMarket("US");
        comboStopLoss.setOrderType(OrderType.STOP_LOSS.name());
        comboStopLoss.setQuantity("1");
        comboStopLoss.setStopPrice("9.5");
        comboStopLoss.setSupportTradingSession("N");
        comboStopLoss.setSide(OrderSide.SELL.name());
        comboStopLoss.setTimeInForce(OrderTIF.DAY.name());
        comboStopLoss.setEntrustType(EntrustType.QTY.name());

        newComboOrders.add(comboMaster);
        newComboOrders.add(comboStopProfit);
        newComboOrders.add(comboStopLoss);
        comboOrder.setNewOrders(newComboOrders);

        PreviewOrderResponse previewComboOrderResponse = apiService.previewOrder(accountId, comboOrder);
        logger.info("previewComboOrderResponse: {}", previewComboOrderResponse);

        TradeOrderResponse placeComboOrderResponse = apiService.placeOrder(accountId, comboOrder);
        logger.info("placeComboOrderResponse: {}", placeComboOrderResponse);
        Thread.sleep(3000L);

        TradeOrder modifyComboOrder = new TradeOrder();
        List<TradeOrderItem> modifyComboOrders = new ArrayList<>();

        TradeOrderItem modifyMaster = new TradeOrderItem();
        modifyMaster.setClientOrderId(masterClientOrderId);
        modifyMaster.setLimitPrice("10.8");
        modifyMaster.setQuantity("2");
        TradeOrderItem modifyStopProfit = new TradeOrderItem();
        modifyStopProfit.setClientOrderId(stopProfitClientOrderId);
        modifyStopProfit.setLimitPrice("11.8");
        modifyStopProfit.setQuantity("2");
        TradeOrderItem modifyStopLoss = new TradeOrderItem();
        modifyStopLoss.setClientOrderId(stopLossClientOrderId);
        modifyStopLoss.setStopPrice("9.8");
        modifyStopLoss.setQuantity("2");

        modifyComboOrders.add(modifyMaster);
        modifyComboOrders.add(modifyStopProfit);
        modifyComboOrders.add(modifyStopLoss);
        modifyComboOrder.setModifyOrders(modifyComboOrders);

        TradeOrderResponse replaceComboOrderResponse = apiService.replaceOrder(accountId, modifyComboOrder);
        logger.info("replaceComboOrderResponse: {}", replaceComboOrderResponse);
        Thread.sleep(3000L);

        TradeOrder cancelComboOrder = new TradeOrder();
        cancelComboOrder.setClientOrderId(masterClientOrderId);
        TradeOrderResponse cancelComboOrderResponse = apiService.cancelOrder(accountId, cancelComboOrder);
        logger.info("cancelComboOrderResponse: {}", cancelComboOrderResponse);

        List<OrderHistory> listComboOrdersResponse = apiService.listOrders(accountId, 10, null, null,null, null);
        logger.info("listComboOrdersResponse: {}", listComboOrdersResponse);

        OrderHistory masterOrderDetailResponse = apiService.getOrderDetails(accountId, masterClientOrderId);
        logger.info("masterOrderDetailResponse: {}", masterOrderDetailResponse);


        // Options
        OptionOrderItemLeg optionOrderItemLeg = new OptionOrderItemLeg();
        optionOrderItemLeg.setSide(OrderSide.BUY.name());
        optionOrderItemLeg.setQuantity("1");
        optionOrderItemLeg.setSymbol("AAPL");
        optionOrderItemLeg.setStrikePrice("250");
        optionOrderItemLeg.setOptionExpireDate("2025-12-19");
        optionOrderItemLeg.setInstrumentType(InstrumentSuperType.OPTION.name());
        optionOrderItemLeg.setOptionType(OptionType.CALL.name());
        optionOrderItemLeg.setMarket(Markets.US.name());
        List<OptionOrderItemLeg> optionOrderItemLegList = new ArrayList<>();
        optionOrderItemLegList.add(optionOrderItemLeg);

        OptionOrderItem optionOrderItem = new OptionOrderItem();
        optionOrderItem.setClientOrderId(GUID.get());
        optionOrderItem.setComboType(ComboType.NORMAL.name());
        optionOrderItem.setOptionStrategy(OptionStrategy.SINGLE.name());
        optionOrderItem.setSide(OrderSide.BUY.name());
        optionOrderItem.setOrderType(OrderType.LIMIT.name());
        optionOrderItem.setTimeInForce(OrderTIF.GTC.name());
        optionOrderItem.setLimitPrice("20.5");
        optionOrderItem.setQuantity("1");
        optionOrderItem.setEntrustType(EntrustType.QTY.name());
        optionOrderItem.setLegs(optionOrderItemLegList);
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
        Thread.sleep(3000L);

        // replace for Webull HK
        // This code is only applicable for single-leg options modification operations.
        /*OptionOrderItem optionReplaceItem = new OptionOrderItem();
        optionReplaceItem.setClientOrderId(optionOrderItem.getClientOrderId());
        optionReplaceItem.setLimitPrice("3");
        optionReplaceItem.setQuantity("2");
        List<OptionOrderItem> optionReplaceItemList = new ArrayList<>();
        optionReplaceItemList.add(optionReplaceItem);
        OptionOrder optionReplace = new OptionOrder();
        optionReplace.setModifyOrders(optionReplaceItemList);*/


        // replace for Webull US
        // If it is a multi-leg option, you need to manually match it to the corresponding sub-leg orderId.
        orderDetailResponse = apiService.getOrderDetails(accountId, optionOrderItem.getClientOrderId());
        logger.info("orderDetailResponse: {}", orderDetailResponse);
        String id = null;
        if(Objects.nonNull(orderDetailResponse) && CollectionUtils.isNotEmpty(orderDetailResponse.getOrders())
                && Objects.nonNull(orderDetailResponse.getOrders().get(0)) && CollectionUtils.isNotEmpty(orderDetailResponse.getOrders().get(0).getLegs())
                && Objects.nonNull(orderDetailResponse.getOrders().get(0).getLegs().get(0))){
            id = orderDetailResponse.getOrders().get(0).getLegs().get(0).getId();
        }
        logger.info("orderDetailResponse id: {}", id);

        if(StringUtils.isNotBlank(id)){
            OptionOrderItemLeg optionReplaceItemLeg = new OptionOrderItemLeg();
            optionReplaceItemLeg.setQuantity("2");
            optionReplaceItemLeg.setId(id);
            List<OptionOrderItemLeg> optionReplaceItemLegList = new ArrayList<>();
            optionReplaceItemLegList.add(optionReplaceItemLeg);

            OptionOrderItem optionReplaceItem = new OptionOrderItem();
            optionReplaceItem.setClientOrderId(optionOrderItem.getClientOrderId());
            optionReplaceItem.setLimitPrice("20");
            optionReplaceItem.setQuantity("2");
            optionReplaceItem.setLegs(optionReplaceItemLegList);
            List<OptionOrderItem> optionReplaceItemList = new ArrayList<>();
            optionReplaceItemList.add(optionReplaceItem);
            OptionOrder optionReplace = new OptionOrder();
            optionReplace.setModifyOrders(optionReplaceItemList);
            logger.info("replaceOptionRequest: {}", optionReplace);
            TradeOrderResponse replaceOptionResponse = apiService.replaceOption(accountId, optionReplace);
            logger.info("replaceOptionResponse: {}", replaceOptionResponse);
            Thread.sleep(3000L);
        }

        OptionOrder cancelTradeOption = new OptionOrder();
        cancelTradeOption.setClientOrderId(optionOrderItem.getClientOrderId());
        logger.info("cancelOptionRequest: {}", cancelTradeOption);
        TradeOrderResponse cancelOptionResponse = apiService.cancelOption(accountId, cancelTradeOption);
        logger.info("cancelOptionResponse: {}", cancelOptionResponse);

        List<OrderHistory> optionHistoryResponse = apiService.listOrders(accountId, 10, null, null,null, null);
        logger.info("optionHistoryResponse: {}", optionHistoryResponse);

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