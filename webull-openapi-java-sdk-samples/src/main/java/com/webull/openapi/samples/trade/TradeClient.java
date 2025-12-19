package com.webull.openapi.samples.trade;

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
import com.webull.openapi.trade.request.StockOrder;
import com.webull.openapi.trade.request.v2.OptionOrder;
import com.webull.openapi.trade.request.v2.OptionOrderItem;
import com.webull.openapi.trade.request.v2.OptionOrderItemLeg;
import com.webull.openapi.trade.response.Account;
import com.webull.openapi.trade.response.AccountDetail;
import com.webull.openapi.trade.response.AccountPositions;
import com.webull.openapi.trade.response.BalanceBase;
import com.webull.openapi.trade.response.InstrumentInfo;
import com.webull.openapi.trade.response.Order;
import com.webull.openapi.trade.response.OrderResponse;
import com.webull.openapi.trade.response.Orders;
import com.webull.openapi.trade.response.TradeCalendar;
import com.webull.openapi.trade.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.response.v2.TradeOrderResponse;

import java.util.ArrayList;
import java.util.List;

public class TradeClient {

    private static final Logger logger = LoggerFactory.getLogger(TradeClient.class);

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
        com.webull.openapi.trade.TradeClient apiService = new com.webull.openapi.trade.TradeClient(apiConfig);

        // get account list
        List<Account> accounts = apiService.getAccountList("");
        logger.info("Accounts: {}", accounts);
        String accountId = null;

        if (CollectionUtils.isNotEmpty(accounts)) {
            accountId = accounts.get(0).getAccountId();
        }
        if (StringUtils.isBlank(accountId)) {
            logger.info("Account id is empty.");
            return;
        }

        List<TradeCalendar> result = apiService.getTradeCalendar("HK", "2025-07-26","2025-07-28");
        logger.info("Trade calendar: {}", result);

        // get account balance
        BalanceBase accountBalance = apiService.getAccountBalance(accountId, "");
        logger.info("Account balance: {}", accountBalance);

        AccountDetail accountDetail = apiService.getAccountDetail(accountId);
        logger.info("Account detail: {}", accountDetail);

        // get account positions
        AccountPositions accountPositions = apiService.getAccountPositions(accountId, 10, "");
        logger.info("Account positions: {}", accountPositions);

        // place order & replace order
        String clientOrderId = GUID.get();
        StockOrder stockOrder = new StockOrder();
        stockOrder.setClientOrderId(clientOrderId);
        stockOrder.setInstrumentId("913256135");
        stockOrder.setSide(OrderSide.BUY.name());
        stockOrder.setTif(OrderTIF.GTC.name());
        stockOrder.setLimitPrice("190");
        stockOrder.setOrderType(OrderType.LIMIT.name());
        stockOrder.setQty("1");
        stockOrder.setExtendedHoursTrading(false);

        logger.info("clientOrderId: {}", clientOrderId);

        OrderResponse placeOrderResponse = apiService.placeOrder(accountId, stockOrder);
        logger.info("Place order: {}", placeOrderResponse);
        doSleep();

        OrderResponse replaceOrderResponse = apiService.replaceOrder(accountId, stockOrder);
        logger.info("Replace order: {}", replaceOrderResponse);
        doSleep();

        OrderResponse cancelOrderResponse = apiService.cancelOrder(accountId, clientOrderId);
        logger.info("Cancel order: {}", cancelOrderResponse);

        // day orders
        Orders<? extends Order> dayOrders = apiService.getDayOrders(accountId, 10, "");
        logger.info("Day orders: {}", dayOrders);

        // opened orders
        Orders<? extends Order> openedOrders = apiService.getOpenedOrders(accountId, 10, "");
        logger.info("Opened orders: {}", openedOrders);

        // order detail
        Order orderDetail = apiService.getOrderDetails(accountId, clientOrderId);
        logger.info("Order detail: {}", orderDetail);

        // instrument info
        InstrumentInfo instrumentInfo = apiService.getTradeInstrument("913256135");
        logger.info("Instrument info: {}", instrumentInfo);

        // trade calendar
        List<TradeCalendar> tradeCalendars = apiService.getTradeCalendar(Markets.US.name(), "2023-01-01", "2023-01-10");
        logger.info("Trade calendars: {}", tradeCalendars);

        // security info
        InstrumentInfo securityInfo = apiService.getSecurityInfo("AAPL", Markets.US.name(), InstrumentSuperType.OPTION.name(), "CALL_OPTION", "3400", "2025-12-19" );
        logger.info("Security info: {}", securityInfo);

        // Options
        // For option order inquiries, please use the V2 query interface: TradeClientV2.getOrderDetails(accountId, clientOrderId).
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
        optionOrderItem.setClientOrderId(GUID.get());
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
        TradeOrderResponse placeOptionResponse = apiService.placeOption(accountId, optionOrder);
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
