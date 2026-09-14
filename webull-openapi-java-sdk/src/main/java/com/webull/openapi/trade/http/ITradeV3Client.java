package com.webull.openapi.trade.http;

import com.webull.openapi.trade.request.TransfersActivitiesRequest;
import com.webull.openapi.trade.request.v3.TradeOrder;
import com.webull.openapi.trade.response.PaginatedResult;
import com.webull.openapi.trade.response.TradeCalendar;
import com.webull.openapi.trade.response.Transfer;
import com.webull.openapi.trade.response.TransferDetail;
import com.webull.openapi.trade.response.v3.*;

import java.util.List;

public interface ITradeV3Client {

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	List<Account> listAccount();

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	AccountBalanceInfo balanceAccount(String accountId);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	List<AccountPositionsInfo> positionsAccount(String accountId);

	/**
	 * This interface is currently supported only for Webull JP.
	 * Support for other regions will be available in future updates.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @deprecated Use {@link #positionDetailsAccount(String, String, String)} instead, which supports paginationKey-based pagination.
	 */
	@Deprecated
	List<AccountPositionDetailsInfo> positionDetailsAccount(String accountId, String instrumentId, Integer pageSize, String lastId);

	/**
	 * Get position details with paginationKey-based pagination.
	 * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
	 * A null paginationKey in the response indicates there are no more pages.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @param accountId the account ID
	 * @param instrumentId the instrument ID
	 * @param paginationKey pagination key from previous response, null for first page
	 * @return paginated result containing position detail list and next pagination key
	 */
	PaginatedResult<AccountPositionDetailsInfo> positionDetailsAccount(String accountId, String instrumentId, String paginationKey);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently supported only for Webull US.
     * Support for other regions will be available in future updates.
     */
    TradeBatchPlaceResponse batchPlaceOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @deprecated Use {@link #listOrders(String, String, String, String)} instead, which supports paginationKey-based pagination.
	 */
	@Deprecated
	List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate,
	                              String lastClientOrderId);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @deprecated Use {@link #openOrders(String, String)} instead, which supports paginationKey-based pagination.
	 */
	@Deprecated
	List<OrderHistory> openOrders(String accountId, Integer pageSize, String lastClientOrderId);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, Webull SG, Webull TH, Webull AU, Webull MY, Webull UK, Webull JP, Webull BR, Webull MX, Webull ZA, and Webull EU.
	 * Support for other regions will be available in future updates.
	 */
	OrderHistory getOrderDetails(String accountId, String clientOrderId);

	/**
	 * This interface is currently supported only for Webull HK, Webull US, and Webull JP.
	 * Support for other regions will be available in future updates.
	 */
	List<TradeCalendar> getTradeCalendar(String market, String start, String end);

	/**
	 * Get cash activities for an account with optional filters.
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @deprecated Use {@link #getCashActivities(String, String, String, String, String)} instead, which supports paginationKey-based pagination.
	 */
	@Deprecated
	List<Activity> getCashActivities(String accountId, String activityTypes, String startTime, String endTime,
			String lastActivityId, Integer pageSize);

    /**
     * Get order executions for an account with optional filters.
     * This interface is currently supported only for Webull HK.
     * Support for other regions will be available in future updates.
     * This path distinguishes the legacy and new pagination protocols via the x-version header.
     *
     * @deprecated Use {@link #getOrderExecutions(String, String, String, String, String)} instead, which supports paginationKey-based pagination.
     */
    @Deprecated
    List<OrderExecution> getOrderExecutions(String accountId, String clientOrderId, String startDate, String endDate, String lastExecutionId, Integer pageSize);

	/**
	 * List historical orders with paginationKey-based pagination.
	 * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @param accountId the account ID
	 * @param startDate start date filter (optional)
	 * @param endDate end date filter (optional)
	 * @param paginationKey pagination key from previous response, null for first page
	 * @return paginated result containing order history list and next pagination key
	 */
	PaginatedResult<OrderHistory> listOrders(String accountId, String startDate, String endDate, String paginationKey);

	/**
	 * List open orders with paginationKey-based pagination.
	 * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @param accountId the account ID
	 * @param paginationKey pagination key from previous response, null for first page
	 * @return paginated result containing open order list and next pagination key
	 */
	PaginatedResult<OrderHistory> openOrders(String accountId, String paginationKey);

	/**
	 * Get cash activities with paginationKey-based pagination.
	 * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @param accountId the account ID
	 * @param activityTypes activity type filter (optional)
	 * @param startTime start time filter (optional)
	 * @param endTime end time filter (optional)
	 * @param paginationKey pagination key from previous response, null for first page
	 * @return paginated result containing activity list and next pagination key
	 */
	PaginatedResult<Activity> getCashActivities(String accountId, String activityTypes, String startTime,
			String endTime, String paginationKey);

	/**
	 * List transfer records with paginationKey-based pagination.
	 * Routes to ACATS or crypto transfer downstream by account type; results are ordered by create time descending.
	 * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
	 * A null paginationKey in the response indicates there are no more pages.
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 *
	 * @param request the request containing accountId (required) and optional filters:
	 *                transferMethod (comma-separated, e.g. ACATS,CRYPTO_TRANSFER),
	 *                direction (INCOMING or OUTGOING),
	 *                status (comma-separated: PENDING, COMPLETED, REJECTED, FAILED, CANCELLED),
	 *                acatsTransferTypes (comma-separated: FULL, PARTIAL, RESIDUAL, RECLAIM, OTHER),
	 *                startTime / endTime (ISO8601 UTC transfer create time range),
	 *                paginationKey (from previous response, null for first page)
	 * @return paginated result containing transfer list and next pagination key
	 * @throws com.webull.openapi.core.exception.ClientException if the region is not Webull US
	 */
	PaginatedResult<Transfer> listTransfersActivities(TransfersActivitiesRequest request);

	/**
	 * Get a single transfer record by account ID and transfer ID.
	 * This interface is currently supported only for Webull US.
	 * Support for other regions will be available in future updates.
	 *
	 * @param accountId the account ID (required)
	 * @param transferId the transfer record ID (required)
	 * @return the transfer detail record, including contra broker, cash and position entries
	 * @throws com.webull.openapi.core.exception.ClientException if the region is not Webull US
	 */
	TransferDetail getTransferActivity(String accountId, String transferId);

	/**
	 * Get order executions with paginationKey-based pagination.
	 * Pass null for paginationKey on the first request; use the returned paginationKey for subsequent pages.
	 * This path distinguishes the legacy and new pagination protocols via the x-version header.
	 *
	 * @param accountId the account ID
	 * @param clientOrderId client order ID filter (optional)
	 * @param startDate start date filter (optional)
	 * @param endDate end date filter (optional)
	 * @param paginationKey pagination key from previous response, null for first page
	 * @return paginated result containing order execution list and next pagination key
	 */
	PaginatedResult<OrderExecution> getOrderExecutions(String accountId, String clientOrderId, String startDate,
			String endDate, String paginationKey);

}
