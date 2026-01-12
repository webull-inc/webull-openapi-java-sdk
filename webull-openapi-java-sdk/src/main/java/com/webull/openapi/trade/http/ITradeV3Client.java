package com.webull.openapi.trade.http;

import com.webull.openapi.trade.request.v3.TradeOrder;
import com.webull.openapi.trade.response.TradeCalendar;
import com.webull.openapi.trade.response.v3.*;

import java.util.List;

public interface ITradeV3Client {

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	List<Account> listAccount();

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	AccountBalanceInfo balanceAccount(String accountId);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	List<AccountPositionsInfo> positionsAccount(String accountId);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently supported only for Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeBatchPlaceResponse batchPlaceOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate,
	                              String lastClientOrderId);

	/**
	 * This interface is currently supported only for Webull HK.
	 * Support for other regions will be available in future updates.
	 */
	List<OrderHistory> openOrders(String accountId, Integer pageSize, String lastClientOrderId);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	OrderHistory getOrderDetails(String accountId, String clientOrderId);

	/**
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 */
	List<TradeCalendar> getTradeCalendar(String market, String start, String end);

}
