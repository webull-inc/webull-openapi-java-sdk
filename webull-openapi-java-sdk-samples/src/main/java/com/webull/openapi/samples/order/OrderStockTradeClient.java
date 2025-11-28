package com.webull.openapi.samples.order;

import com.webull.openapi.core.common.Region;
import com.webull.openapi.core.common.dict.*;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.request.v2.TradeOrder;
import com.webull.openapi.trade.request.v2.TradeOrderItem;
import com.webull.openapi.trade.response.v2.OrderHistory;
import com.webull.openapi.trade.response.v2.TradeOrderResponse;

import java.util.ArrayList;
import java.util.List;

public class OrderStockTradeClient {
    private static final Logger logger = LoggerFactory.getLogger(OrderStockTradeClient.class);

    public static void main(String[] args) {
        OrderStockTradeClient orderStockTradeClient = new OrderStockTradeClient();
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

        // get accountId from account_list interface , you can look @see com.webull.openapi.samples.account.AccountList
        String accountId = "#{accountId}";        String clientOrderId = GUID.get();
        com.webull.openapi.trade.TradeClientV2 apiService = new com.webull.openapi.trade.TradeClientV2(apiConfig);

        // build place us order params
        TradeOrder tradeOrder = orderStockTradeClient.buildPlaceUSStockParams(clientOrderId);
        // build place HK order params
        // TradeOrder tradeOrder = orderStockTradeClient.buildPlaceHKStockParams(clientOrderId);
        // place order
        TradeOrderResponse placeOrderResp = apiService.placeOrder(accountId,tradeOrder);
        logger.info("Place order response: {}", placeOrderResp);

        // get order detail
        OrderHistory orderDetail = apiService.getOrderDetails(accountId,clientOrderId);
        logger.info("Order details response: {}", orderDetail);

        // replace order
        TradeOrder modifyTradeOrder = orderStockTradeClient.buildReplaceOrderParams(clientOrderId);
        TradeOrderResponse modifyOrderResponse = apiService.replaceOrder(accountId, modifyTradeOrder);
        logger.info("Order modify response: {}", modifyOrderResponse);

        // query order detail after replace order
        OrderHistory orderDetail1 = apiService.getOrderDetails(accountId, clientOrderId);
        logger.info("Order orderDetail response after replace order: {}", orderDetail1);

        // cancel order
        TradeOrder cancelOrder = new TradeOrder();
        cancelOrder.setClientOrderId(clientOrderId);
        TradeOrderResponse cancelOrderResponse = apiService.cancelOrder(accountId, cancelOrder);
        logger.info("Order cancel order response: {}", cancelOrderResponse);

        // query order detail after cancel order
        OrderHistory orderDetail2 = apiService.getOrderDetails(accountId, clientOrderId);
        logger.info("Order orderDetail response after cancel: {}", orderDetail2.getOrders().get(0).getStatus());


    }

    /**
     * build your place order object
     *
     * @param clientOrderId
     * @return
     */
    private TradeOrder buildPlaceUSStockParams(String clientOrderId) {
        TradeOrder tradeOrder = new TradeOrder();
        List<TradeOrderItem> newOrders = new ArrayList<>();
        TradeOrderItem placeOne = new TradeOrderItem();
        placeOne.setClientOrderId(clientOrderId);
        // WebullUS need set combo_type, because WebullUS support combo order
         placeOne.setComboType(ComboType.NORMAL.name());
        newOrders.add(placeOne);
        placeOne.setSymbol("BULL");
        placeOne.setInstrumentType(InstrumentSuperType.EQUITY.name());
        placeOne.setMarket("US");
        placeOne.setOrderType(OrderType.LIMIT.name());
        placeOne.setQuantity("1");
        placeOne.setLimitPrice("5");
        placeOne.setSupportTradingSession("ALL");
        placeOne.setSide(OrderSide.BUY.name());
        placeOne.setTimeInForce(OrderTIF.DAY.name());
        placeOne.setEntrustType(EntrustType.QTY.name());
        tradeOrder.setNewOrders(newOrders);
        return tradeOrder;
    }

    /**
     * build hk stock place params
     * Only for Webull HK's Client
     * @param clientOrderId
     * @return
     */
    private TradeOrder buildPlaceHKStockParams(String clientOrderId) {
        TradeOrder tradeOrder = new TradeOrder();
        List<TradeOrderItem> newOrders = new ArrayList<>();
        TradeOrderItem placeOne = new TradeOrderItem();
        placeOne.setClientOrderId(clientOrderId);
        // WebullUS need set combo_type, because WebullUS support combo order
        placeOne.setComboType(ComboType.NORMAL.name());
        newOrders.add(placeOne);
        placeOne.setSymbol("00700");
        placeOne.setInstrumentType(InstrumentSuperType.EQUITY.name());
        placeOne.setMarket("HK");
        placeOne.setOrderType(OrderType.ENHANCED_LIMIT.name());
        placeOne.setQuantity("100");
        placeOne.setLimitPrice("610");
        placeOne.setSide(OrderSide.BUY.name());
        placeOne.setTimeInForce(OrderTIF.DAY.name());
        placeOne.setEntrustType(EntrustType.QTY.name());
        tradeOrder.setNewOrders(newOrders);
        return tradeOrder;
    }

    /**
     * build your replace order params
     * @param clientOrderId
     * @return replace order object
     */
    private TradeOrder buildReplaceOrderParams(String clientOrderId) {
        TradeOrder replaceTradeOrder = new TradeOrder();
        List<TradeOrderItem> modifyOrders = new ArrayList<>();
        TradeOrderItem modifyOne = new TradeOrderItem();
        modifyOne.setClientOrderId(clientOrderId);
        modifyOne.setLimitPrice("5");
        modifyOne.setQuantity("2");
        modifyOrders.add(modifyOne);
        replaceTradeOrder.setModifyOrders(modifyOrders);
        return replaceTradeOrder;
    }

}