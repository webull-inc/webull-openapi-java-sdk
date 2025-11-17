/*
 * Copyright 2022 Webull Technologies Pte. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.trade;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.core.common.Headers;
import com.webull.openapi.core.common.Region;
import com.webull.openapi.core.common.Versions;
import com.webull.openapi.core.common.dict.InstrumentSuperType;
import com.webull.openapi.core.common.dict.TickerType;
import com.webull.openapi.core.context.RequestContextHolder;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.http.HttpRequest;
import com.webull.openapi.core.http.common.HttpMethod;
import com.webull.openapi.core.http.initializer.ClientInitializer;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.trade.http.ITradeV2Client;
import com.webull.openapi.trade.request.v2.OptionOrder;
import com.webull.openapi.trade.request.v2.OptionOrderItemLeg;
import com.webull.openapi.trade.request.v2.TradeOrder;
import com.webull.openapi.trade.request.v2.TradeOrderItem;
import com.webull.openapi.trade.response.TradeCalendar;
import com.webull.openapi.trade.response.v2.Account;
import com.webull.openapi.trade.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.response.v2.OrderHistory;
import com.webull.openapi.trade.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.response.v2.TradeOrderResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TradeClientV2 implements ITradeV2Client {

    private static final String ACCOUNT_ID_ARG = "accountId";
    private static final String TRADE_ORDER_ARG = "tradeOrder";
    private static final String OPTION_ORDER_ARG = "optionOrder";
    private static final String NEW_ORDERS_ARG = "newOrders";
    private static final String MODIFY_ORDERS_ARG = "modifyOrders";
    private static final String CLIENT_ORDER_ID_ARG = "clientOrderId";

    private static final String PAGE_SIZE_PARAM = "page_size";
    private static final String START_TIME_PARAM = "start_date";
    private static final String END_TIME_PARAM = "end_date";
    private static final String LAST_CLIENT_ORDER_ID_PARAM = "last_client_order_id";
    private static final String LAST_ORDER_ID_PARAM = "last_order_id";
    private static final String ACCOUNT_ID_PARAM = "account_id";
    private static final String CLIENT_ORDER_ID_PARAM = "client_order_id";
    private static final String MARKET_ARG = "market";
    private static final String START_ARG = "start";
    private static final String END_ARG = "end";
    private static final String MARKET_PARAM = MARKET_ARG;
    private static final String START_PARAM = START_ARG;
    private static final String END_PARAM = END_ARG;

    private static final String CLIENT_COMBO_ORDER_ID_PARAM = "client_combo_order_id";
    private static final String NEW_ORDERS_PARAM = "new_orders";
    private static final String MODIFY_ORDERS_PARAM = "modify_orders";


    private final Region region;
    private final HttpApiClient apiClient;

    public TradeClientV2(HttpApiConfig config) {
        this(new HttpApiClient(config));
    }

    public TradeClientV2(HttpApiClient apiClient) {
        this.region = Region.of(apiClient.getConfig().getRegionId())
                .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER,
                        "Must set region id which defined in " + Region.class.getName() + " when using this service."));
        this.apiClient = apiClient;
        ClientInitializer.init(apiClient);
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public List<Account> listAccount() {
        HttpRequest request = new HttpRequest("/openapi/account/list", Versions.V2, HttpMethod.GET);
        return apiClient.request(request).responseType(new TypeToken<List<Account>>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public AccountBalanceInfo balanceAccount(String accountId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/assets/balance", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(params);
        return apiClient.request(request).responseType(AccountBalanceInfo.class).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public List<AccountPositionsInfo> positionsAccount(String accountId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/assets/positions", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<AccountPositionsInfo>>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/trade/stock/order/preview", Versions.V2, HttpMethod.POST);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(NEW_ORDERS_PARAM, tradeOrder.getNewOrders());
        if(StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(new TypeToken<PreviewOrderResponse>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
        Assert.notEmpty(NEW_ORDERS_ARG, tradeOrder.getNewOrders());
        HttpRequest request = new HttpRequest("/openapi/trade/stock/order/place", Versions.V2, HttpMethod.POST);
        addCustomHeadersFromOrder(request, tradeOrder);
        addCustomHeaderFromContext(request);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(NEW_ORDERS_PARAM, tradeOrder.getNewOrders());
        if(StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
        Assert.notEmpty(MODIFY_ORDERS_ARG, tradeOrder.getModifyOrders());
        HttpRequest request = new HttpRequest("/openapi/trade/stock/order/replace", Versions.V2, HttpMethod.POST);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(MODIFY_ORDERS_PARAM, tradeOrder.getModifyOrders());
        if(StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
        Assert.notBlank(CLIENT_ORDER_ID_ARG, tradeOrder.getClientOrderId());
        HttpRequest request = new HttpRequest("/openapi/trade/stock/order/cancel", Versions.V2, HttpMethod.POST);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, tradeOrder.getClientOrderId());
        if(StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {}.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */

    @Deprecated
    @Override
    public List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate, String lastClientOrderId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/trade/order/history", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastClientOrderId)) {
            params.put(LAST_CLIENT_ORDER_ID_PARAM, lastClientOrderId);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            params.put(START_TIME_PARAM, startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            params.put(END_TIME_PARAM, endDate);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {}.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK.
     * Support for other regions will be available in future updates.
     */
    @Deprecated
    @Override
    public List<OrderHistory> openOrders(String accountId, Integer pageSize, String lastOrderId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/trade/order/open", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastOrderId)) {
            params.put(LAST_ORDER_ID_PARAM, lastOrderId);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {}.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate,
                                         String lastClientOrderId, String lastOrderId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/trade/order/history", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastClientOrderId)) {
            params.put(LAST_CLIENT_ORDER_ID_PARAM, lastClientOrderId);
        }
        if (StringUtils.isNotEmpty(lastOrderId)) {
            params.put(LAST_ORDER_ID_PARAM, lastOrderId);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            params.put(START_TIME_PARAM, startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            params.put(END_TIME_PARAM, endDate);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {}.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK.
     * Support for other regions will be available in future updates.
     */
    @Override
    public List<OrderHistory> openOrders(String accountId, Integer pageSize,
                                         String lastClientOrderId, String lastOrderId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/trade/order/open", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastClientOrderId)) {
            params.put(LAST_CLIENT_ORDER_ID_PARAM, lastClientOrderId);
        }
        if (StringUtils.isNotEmpty(lastOrderId)) {
            params.put(LAST_ORDER_ID_PARAM, lastOrderId);
        }

        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {}.getType()).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public OrderHistory getOrderDetails(String accountId, String clientOrderId) {
        Assert.notBlank(Arrays.asList(ACCOUNT_ID_ARG, CLIENT_ORDER_ID_ARG), accountId, clientOrderId);
        HttpRequest request = new HttpRequest("/openapi/trade/order/detail", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
        request.setQuery(params);
        return apiClient.request(request).responseType(OrderHistory.class).doAction();
    }

    private void addCustomHeadersFromOrder(HttpRequest request, TradeOrder tradeOrder) {
        if(Objects.isNull(tradeOrder)
                || CollectionUtils.isEmpty(tradeOrder.getNewOrders())
                || Objects.isNull(tradeOrder.getNewOrders().get(0))){
            return;
        }
        TradeOrderItem item = tradeOrder.getNewOrders().get(0);

        List<String> categoryList = Arrays.asList( item.getMarket(), TickerType.STOCK.name());
        String category = StringUtils.join(categoryList, "_");
        if (StringUtils.isNotBlank(category)) {
            request.getHeaders().put(Headers.CATEGORY_KEY, category);
        }
    }

    private void addCustomHeadersFromOrder(HttpRequest request, OptionOrder optionOrder) {
        if(CollectionUtils.isEmpty(optionOrder.getNewOrders())
                || Objects.isNull(optionOrder.getNewOrders().get(0))
                || CollectionUtils.isEmpty(optionOrder.getNewOrders().get(0).getLegs())){
            return;
        }
        OptionOrderItemLeg item = optionOrder.getNewOrders().get(0).getLegs().stream().
                filter(v-> Objects.nonNull(v) && Objects.equals(InstrumentSuperType.OPTION.name(), v.getInstrumentType()))
                .findFirst().orElse(null);
        if(Objects.isNull(item)){
            return;
        }
        List<String> categoryList = Arrays.asList( item.getMarket(), item.getInstrumentType());
        String category = StringUtils.join(categoryList, "_");
        if (StringUtils.isNotBlank(category)) {
            request.getHeaders().put(Headers.CATEGORY_KEY, category);
        }
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public PreviewOrderResponse previewOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notEmpty(NEW_ORDERS_ARG, optionOrder.getNewOrders());
        HttpRequest request = new HttpRequest("/openapi/trade/option/order/preview", Versions.V2, HttpMethod.POST);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(NEW_ORDERS_PARAM, optionOrder.getNewOrders());
        if(StringUtils.isNotBlank(optionOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, optionOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(PreviewOrderResponse.class).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public TradeOrderResponse placeOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notEmpty(NEW_ORDERS_ARG, optionOrder.getNewOrders());
        HttpRequest request = new HttpRequest("/openapi/trade/option/order/place", Versions.V2, HttpMethod.POST);
        addCustomHeadersFromOrder(request, optionOrder);
        addCustomHeaderFromContext(request);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(NEW_ORDERS_PARAM, optionOrder.getNewOrders());
        if(StringUtils.isNotBlank(optionOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, optionOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(TradeOrderResponse.class).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public TradeOrderResponse replaceOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notEmpty(MODIFY_ORDERS_ARG, optionOrder.getModifyOrders());
        HttpRequest request = new HttpRequest("/openapi/trade/option/order/replace", Versions.V2, HttpMethod.POST);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(MODIFY_ORDERS_PARAM, optionOrder.getModifyOrders());
        if(StringUtils.isNotBlank(optionOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, optionOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(TradeOrderResponse.class).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public TradeOrderResponse cancelOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notBlank(CLIENT_ORDER_ID_ARG, optionOrder.getClientOrderId());
        HttpRequest request = new HttpRequest("/openapi/trade/option/order/cancel", Versions.V2, HttpMethod.POST);

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, optionOrder.getClientOrderId());
        if(StringUtils.isNotBlank(optionOrder.getClientComboOrderId())){
            params.put(CLIENT_COMBO_ORDER_ID_PARAM, optionOrder.getClientComboOrderId());
        }
        request.setBody(params);

        return apiClient.request(request).responseType(TradeOrderResponse.class).doAction();
    }

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Override
    public List<TradeCalendar> getTradeCalendar(String market, String start, String end) {
        Assert.notBlank(Arrays.asList(MARKET_ARG, START_ARG, END_ARG), market, start, end);
        HttpRequest request = new HttpRequest("/openapi/trade/calendar", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(MARKET_PARAM, market);
        params.put(START_PARAM, start);
        params.put(END_PARAM, end);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<TradeCalendar>>() {}.getType()).doAction();
    }

    @Override
    public void addCustomHeaders(Map<String, String> headersMap) {
        if(Objects.isNull(headersMap) || headersMap.isEmpty()){
            return;
        }
        RequestContextHolder.get().putAll(headersMap);
    }

    @Override
    public void removeCustomHeaders() {
        RequestContextHolder.clear();
    }

    private void addCustomHeaderFromContext(HttpRequest request){
        try{
            Map<String, String> headersMap =  RequestContextHolder.get();
            if(Objects.isNull(headersMap) || headersMap.isEmpty()){
                return;
            }

            request.getHeaders().putAll(headersMap);
        }finally {
            RequestContextHolder.clear();
        }

    }

}
