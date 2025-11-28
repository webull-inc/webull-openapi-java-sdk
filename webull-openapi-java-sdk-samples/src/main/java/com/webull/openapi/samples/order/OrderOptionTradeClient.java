package com.webull.openapi.samples.order;

import com.webull.openapi.core.common.dict.*;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.request.v2.OptionOrder;
import com.webull.openapi.trade.request.v2.OptionOrderItem;
import com.webull.openapi.trade.request.v2.OptionOrderItemLeg;
import com.webull.openapi.trade.response.v2.OrderHistory;
import com.webull.openapi.trade.response.v2.TradeOrderResponse;

import java.util.ArrayList;
import java.util.List;


public class OrderOptionTradeClient {
    private static final Logger logger = LoggerFactory.getLogger(OrderOptionTradeClient.class);

    public static void main(String[] args) {
        OrderOptionTradeClient orderOptionTradeClient = new OrderOptionTradeClient();
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
        String accountId = "#{accountId}";        String clientOrderId = GUID.get();
        // build place option order params
        OptionOrder optionOrder = orderOptionTradeClient.buildOptionPlaceParams(clientOrderId);
        TradeOrderResponse tradeOrderResponse = apiService.placeOption(accountId, optionOrder);
        logger.info("Place option order response: {}", tradeOrderResponse);

        // get option order detail
        OrderHistory orderDetails = apiService.getOrderDetails(accountId, clientOrderId);
        logger.info("Order details response: {}", orderDetails);

        OptionOrder replaceOptionOrder = orderOptionTradeClient.buildReplaceOptionPlaceParams(clientOrderId);
        TradeOrderResponse replaceResponse = apiService.replaceOption(accountId, replaceOptionOrder);
        logger.info("Replace option order response: {}", replaceResponse);

        // get option order detail
        OrderHistory orderDetails2= apiService.getOrderDetails(accountId, clientOrderId);
        logger.info("Order details response: {}", orderDetails2);

        // cancel order
        OptionOrder cancelOption = new OptionOrder();
        cancelOption.setClientOrderId(clientOrderId);
        TradeOrderResponse orderResponse = apiService.cancelOption(accountId, cancelOption);
        logger.info("Option order cancel response: {}", orderResponse);

    }

    /**
     * build your option stock place params
     * @param clientOrderId
     * @return option order place params
     */
    private OptionOrder buildOptionPlaceParams(String clientOrderId) {
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
        optionOrderItem.setClientOrderId(clientOrderId);
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
        return optionOrder;
    }

    /**
     * build your option stock place params
     * @param clientOrderId
     * @return option order place params
     */
    private OptionOrder buildReplaceOptionPlaceParams(String clientOrderId) {
        OptionOrder replaceOptionOrder = new OptionOrder();
        replaceOptionOrder.setClientOrderId(clientOrderId);
        List<OptionOrderItem> modifyOrders = new ArrayList<>();
        replaceOptionOrder.setModifyOrders(modifyOrders);
        OptionOrderItem optionOrderItem = new OptionOrderItem();
        modifyOrders.add(optionOrderItem);
        optionOrderItem.setClientOrderId(clientOrderId);
        optionOrderItem.setQuantity("2");
        return replaceOptionOrder;
    }
}
