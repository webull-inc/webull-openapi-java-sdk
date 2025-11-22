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
package com.webull.openapi.trade.http;

import com.webull.openapi.trade.request.v2.OptionOrder;
import com.webull.openapi.trade.request.v2.TradeOrder;
import com.webull.openapi.trade.response.TradeCalendar;
import com.webull.openapi.trade.response.v2.Account;
import com.webull.openapi.trade.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.response.v2.OrderHistory;
import com.webull.openapi.trade.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.response.v2.TradeOrderResponse;

import java.util.List;
import java.util.Map;

public interface ITradeV2Client {

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    List<Account> listAccount();

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    AccountBalanceInfo balanceAccount(String accountId);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    List<AccountPositionsInfo> positionsAccount(String accountId);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    @Deprecated
    List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate, String lastClientOrderId);

    /**
     * This interface is currently supported only for Webull HK.
     * Support for other regions will be available in future updates.
     */

    @Deprecated
    List<OrderHistory> openOrders(String accountId, Integer pageSize, String lastOrderId);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate,
                                  String lastClientOrderId, String lastOrderId);

    /**
     * This interface is currently supported only for Webull HK.
     * Support for other regions will be available in future updates.
     */
    List<OrderHistory> openOrders(String accountId, Integer pageSize, String lastClientOrderId,
                                  String lastOrderId);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    OrderHistory getOrderDetails(String accountId, String clientOrderId);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    PreviewOrderResponse previewOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeOrderResponse placeOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeOrderResponse replaceOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeOrderResponse cancelOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently supported only for Webull HK and Webull US.
     * Support for other regions will be available in future updates.
     */
    List<TradeCalendar> getTradeCalendar(String market, String start, String end);

    /**
     * This is an optional feature; you can still make a request without setting it.
     * If set, you can specify certain headers to perform specific operations.
     * Note: If you set a header, call removeCustomHeaders to clean up the header after the request is completed.
     *
     * Currently supported header keys and functions:
     *      Key：category {@link com.webull.openapi.core.common.dict.Category}
     *      Function: Frequency limit rules, please refer to the document for details. currently only supports Hong Kong
     *
     * @param headersMap
     */
    void addCustomHeaders(Map<String, String> headersMap);

    /**
     * Clearing headers
     */
    void removeCustomHeaders();

}
