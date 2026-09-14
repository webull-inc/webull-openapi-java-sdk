package com.webull.openapi.trade;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.core.common.Headers;
import com.webull.openapi.core.common.Region;
import com.webull.openapi.core.common.Versions;
import com.webull.openapi.core.common.dict.InstrumentSuperType;
import com.webull.openapi.core.exception.ClientException;
import com.webull.openapi.core.exception.ErrorCode;
import com.webull.openapi.core.http.HttpApiClient;
import com.webull.openapi.core.http.HttpApiConfig;
import com.webull.openapi.core.http.HttpRequest;
import com.webull.openapi.core.http.common.HttpMethod;
import com.webull.openapi.core.http.initializer.ClientInitializer;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.CollectionUtils;
import com.webull.openapi.core.utils.StringUtils;
import com.webull.openapi.trade.http.ITradeV3Client;
import com.webull.openapi.trade.request.v3.*;
import com.webull.openapi.trade.request.TransfersActivitiesRequest;
import com.webull.openapi.trade.response.PaginatedResult;
import com.webull.openapi.trade.response.TradeCalendar;
import com.webull.openapi.trade.response.Transfer;
import com.webull.openapi.trade.response.TransferDetail;
import com.webull.openapi.trade.response.v3.*;

import java.util.*;

public class TradeClientV3 implements ITradeV3Client {

	private static final String ACCOUNT_ID_ARG = "accountId";
	private static final String REQUEST_ARG = "request";
	private static final String TRADE_ORDER_ARG = "tradeOrder";
	private static final String NEW_ORDERS_ARG = "newOrders";
    private static final String BATCH_ORDERS_ARG = "batchOrders";
	private static final String MODIFY_ORDERS_ARG = "modifyOrders";
	private static final String CLIENT_ORDER_ID_ARG = "clientOrderId";
	private static final String INSTRUMENT_ID_ARG = "instrumentId";
	private static final String TRANSFER_ID_ARG = "transferId";

	private static final String PAGE_SIZE_PARAM = "page_size";
	private static final String START_DATE_PARAM = "start_date";
	private static final String END_DATE_PARAM = "end_date";
    private static final String START_TIME_PARAM = "start_time";
    private static final String END_TIME_PARAM = "end_time";

	private static final String LAST_CLIENT_ORDER_ID_PARAM = "last_client_order_id";
	private static final String INSTRUMENT_ID_PARAM = "instrument_id";
	private static final String LAST_ID_PARAM = "last_id";
	private static final String LAST_ORDER_ID_PARAM = "last_order_id";
	private static final String ACCOUNT_ID_PARAM = "account_id";
	private static final String CLIENT_ORDER_ID_PARAM = "client_order_id";

	private static final String CLIENT_COMBO_ORDER_ID_PARAM = "client_combo_order_id";
	private static final String NEW_ORDERS_PARAM = "new_orders";
    private static final String BATCH_ORDERS_PARAM = "batch_orders";
	private static final String MODIFY_ORDERS_PARAM = "modify_orders";

	private static final String ACTIVITY_TYPES_PARAM = "activity_types";
	private static final String START_TIME_ACTIVITY_PARAM = "start_time";
	private static final String END_TIME_ACTIVITY_PARAM = "end_time";
	private static final String LAST_ACTIVITY_ID_PARAM = "last_activity_id";

    private static final String LAST_EXECUTION_ID_PARAM = "last_execution_id";

	private static final String TRANSFER_METHOD_PARAM = "transfer_method";
	private static final String TRANSFER_ID_PARAM = "transfer_id";
	private static final String DIRECTION_PARAM = "direction";
	private static final String STATUS_PARAM = "status";
	private static final String ACATS_TRANSFER_TYPES_PARAM = "acats_transfer_types";

	private static final String PAGINATION_KEY_PARAM = "pagination_key";

	private final Region region;
	private final HttpApiClient apiClient;

	public TradeClientV3(HttpApiConfig config) {
		this(new HttpApiClient(config));
	}

	public TradeClientV3(HttpApiClient apiClient) {
		this.region = Region.of(apiClient.getConfig().getRegionId())
			.orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER,
				"Must set region id which defined in " + Region.class.getName() + " when using this service."));
		this.apiClient = apiClient;
		ClientInitializer.init(apiClient);
	}

	@Override
	public List<Account> listAccount() {
		HttpRequest request = new HttpRequest("/trading/accounts/list", Versions.V3, HttpMethod.GET);
		return apiClient.request(request).responseType(new TypeToken<List<Account>>() {
		}.getType()).doAction();
	}

	@Override
	public AccountBalanceInfo balanceAccount(String accountId) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/assets/balances/get", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		request.setQuery(params);
		return apiClient.request(request).responseType(AccountBalanceInfo.class).doAction();
	}

	@Override
	public List<AccountPositionsInfo> positionsAccount(String accountId) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/assets/positions/list", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<List<AccountPositionsInfo>>() {
		}.getType()).doAction();
	}

	@Override
	@Deprecated
	public List<AccountPositionDetailsInfo> positionDetailsAccount(String accountId, String instrumentId, Integer pageSize, String lastId) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		Assert.notBlank(INSTRUMENT_ID_ARG, instrumentId);

		HttpRequest request = new HttpRequest("/trading/assets/positions/get", Versions.V2, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(INSTRUMENT_ID_PARAM, instrumentId);
		params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
		if (StringUtils.isNotEmpty(lastId)) {
			params.put(LAST_ID_PARAM, lastId);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<List<AccountPositionDetailsInfo>>() {
		}.getType()).doAction();
	}

	@Override
	public PaginatedResult<AccountPositionDetailsInfo> positionDetailsAccount(String accountId, String instrumentId, String paginationKey) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		Assert.notBlank(INSTRUMENT_ID_ARG, instrumentId);

		HttpRequest request = new HttpRequest("/trading/assets/positions/get", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(INSTRUMENT_ID_PARAM, instrumentId);
		if (StringUtils.isNotEmpty(paginationKey)) {
			params.put(PAGINATION_KEY_PARAM, paginationKey);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<PaginatedResult<AccountPositionDetailsInfo>>() {
		}.getType()).doAction();
	}

	@Override
	public PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/orders/preview", Versions.V3, HttpMethod.POST);

		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(NEW_ORDERS_PARAM, tradeOrder.getNewOrders());
		if (StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())) {
			params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
		}
		request.setBody(params);

		return apiClient.request(request).responseType(new TypeToken<PreviewOrderResponse>() {
		}.getType()).doAction();
	}

	@Override
	public TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
		Assert.notEmpty(NEW_ORDERS_ARG, tradeOrder.getNewOrders());
		HttpRequest request = new HttpRequest("/trading/orders/place", Versions.V3, HttpMethod.POST);
		addCustomHeadersFromOrder(request, tradeOrder.getNewOrders());

		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(NEW_ORDERS_PARAM, tradeOrder.getNewOrders());
		if (StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())) {
			params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
		}
		request.setBody(params);

		return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
		}.getType()).doAction();
	}

    @Override
    public TradeBatchPlaceResponse batchPlaceOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
        Assert.notEmpty(BATCH_ORDERS_ARG, tradeOrder.getBatchOrders());
        HttpRequest request = new HttpRequest("/trading/orders/batch-place", Versions.V3, HttpMethod.POST);
        addCustomHeadersFromOrder(request, tradeOrder.getBatchOrders());

        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(BATCH_ORDERS_PARAM, tradeOrder.getBatchOrders());
        request.setBody(params);

        return apiClient.request(request).responseType(new TypeToken<TradeBatchPlaceResponse>() {
        }.getType()).doAction();
    }

    @Override
	public TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
		Assert.notEmpty(MODIFY_ORDERS_ARG, tradeOrder.getModifyOrders());
		HttpRequest request = new HttpRequest("/trading/orders/replace", Versions.V3, HttpMethod.POST);

		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(MODIFY_ORDERS_PARAM, tradeOrder.getModifyOrders());
		if (StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())) {
			params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
		}
		request.setBody(params);

		return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
		}.getType()).doAction();
	}

	@Override
	public TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
		Assert.notBlank(CLIENT_ORDER_ID_ARG, tradeOrder.getClientOrderId());
		HttpRequest request = new HttpRequest("/trading/orders/cancel", Versions.V3, HttpMethod.POST);

		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(CLIENT_ORDER_ID_PARAM, tradeOrder.getClientOrderId());
		if (StringUtils.isNotBlank(tradeOrder.getClientComboOrderId())) {
			params.put(CLIENT_COMBO_ORDER_ID_PARAM, tradeOrder.getClientComboOrderId());
		}
		request.setBody(params);

		return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
		}.getType()).doAction();
	}

	@Override
	@Deprecated
	public List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate, String lastClientOrderId) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/orders/historical-orders/list", Versions.V2, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
		if (StringUtils.isNotEmpty(lastClientOrderId)) {
			params.put(LAST_CLIENT_ORDER_ID_PARAM, lastClientOrderId);
		}
		if (StringUtils.isNotEmpty(startDate)) {
			params.put(START_DATE_PARAM, startDate);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			params.put(END_DATE_PARAM, endDate);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {
		}.getType()).doAction();
	}

	@Override
	@Deprecated
	public List<OrderHistory> openOrders(String accountId, Integer pageSize, String lastClientOrderId) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/orders/open-orders/list", Versions.V2, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
		if (StringUtils.isNotEmpty(lastClientOrderId)) {
			params.put(LAST_CLIENT_ORDER_ID_PARAM, lastClientOrderId);
		}

		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {
		}.getType()).doAction();
	}

	@Override
	public OrderHistory getOrderDetails(String accountId, String clientOrderId) {
		Assert.notBlank(Arrays.asList(ACCOUNT_ID_ARG, CLIENT_ORDER_ID_ARG), accountId, clientOrderId);
		HttpRequest request = new HttpRequest("/trading/orders/get", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
		request.setQuery(params);
		return apiClient.request(request).responseType(OrderHistory.class).doAction();
	}

	@Override
	public List<TradeCalendar> getTradeCalendar(String market, String start, String end) {
		return Collections.emptyList();
	}

	@Override
	@Deprecated
	public List<Activity> getCashActivities(String accountId, String activityTypes, String startTime, String endTime,
			String lastActivityId, Integer pageSize) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/activities/cash-activities/list", Versions.V2, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
		if (StringUtils.isNotEmpty(activityTypes)) {
			params.put(ACTIVITY_TYPES_PARAM, activityTypes);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			params.put(START_TIME_ACTIVITY_PARAM, startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			params.put(END_TIME_ACTIVITY_PARAM, endTime);
		}
		if (StringUtils.isNotEmpty(lastActivityId)) {
			params.put(LAST_ACTIVITY_ID_PARAM, lastActivityId);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<List<Activity>>() {
		}.getType()).doAction();
	}

    @Override
    @Deprecated
    public List<OrderExecution> getOrderExecutions(String accountId, String clientOrderId, String startDate, String endDate, String lastExecutionId, Integer pageSize) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);

        HttpRequest request = new HttpRequest("/trading/orders/executions/list", Versions.V2, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 20 : pageSize);
        if (StringUtils.isNotBlank(startDate)) {
            params.put(START_DATE_PARAM, startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            params.put(END_DATE_PARAM, endDate);
        }
        if (StringUtils.isNotBlank(lastExecutionId)){
            params.put(LAST_EXECUTION_ID_PARAM, lastExecutionId);
        }
        if (StringUtils.isNotBlank(clientOrderId)){
            params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<OrderExecution>>() {
        }.getType()).doAction();
    }

    @Override
	public PaginatedResult<OrderHistory> listOrders(String accountId, String startTime, String endTime, String paginationKey) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/orders/historical-orders/list", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		if (StringUtils.isNotEmpty(startTime)) {
			params.put(START_TIME_PARAM, startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			params.put(END_TIME_PARAM, endTime);
		}
		if (StringUtils.isNotEmpty(paginationKey)) {
			params.put(PAGINATION_KEY_PARAM, paginationKey);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<PaginatedResult<OrderHistory>>() {
		}.getType()).doAction();
	}

	@Override
	public PaginatedResult<OrderHistory> openOrders(String accountId, String paginationKey) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/orders/open-orders/list", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		if (StringUtils.isNotEmpty(paginationKey)) {
			params.put(PAGINATION_KEY_PARAM, paginationKey);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<PaginatedResult<OrderHistory>>() {
		}.getType()).doAction();
	}

	@Override
	public PaginatedResult<Activity> getCashActivities(String accountId, String activityTypes, String startTime,
			String endTime, String paginationKey) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/activities/cash-activities/list", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		if (StringUtils.isNotEmpty(activityTypes)) {
			params.put(ACTIVITY_TYPES_PARAM, activityTypes);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			params.put(START_TIME_ACTIVITY_PARAM, startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			params.put(END_TIME_ACTIVITY_PARAM, endTime);
		}
		if (StringUtils.isNotEmpty(paginationKey)) {
			params.put(PAGINATION_KEY_PARAM, paginationKey);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<PaginatedResult<Activity>>() {
		}.getType()).doAction();
	}

	@Override
	public PaginatedResult<Transfer> listTransfersActivities(TransfersActivitiesRequest request) {
		assertUsRegion("listTransfersActivities");
		Assert.notNull(REQUEST_ARG, request);
		Assert.notBlank(ACCOUNT_ID_ARG, request.getAccountId());
		HttpRequest httpRequest = new HttpRequest("/trading/activities/transfer-activities/list", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, request.getAccountId());
		if (StringUtils.isNotEmpty(request.getTransferMethod())) {
			params.put(TRANSFER_METHOD_PARAM, request.getTransferMethod());
		}
		if (StringUtils.isNotEmpty(request.getDirection())) {
			params.put(DIRECTION_PARAM, request.getDirection());
		}
		if (StringUtils.isNotEmpty(request.getStatus())) {
			params.put(STATUS_PARAM, request.getStatus());
		}
		if (StringUtils.isNotEmpty(request.getAcatsTransferTypes())) {
			params.put(ACATS_TRANSFER_TYPES_PARAM, request.getAcatsTransferTypes());
		}
		if (StringUtils.isNotEmpty(request.getStartTime())) {
			params.put(START_TIME_PARAM, request.getStartTime());
		}
		if (StringUtils.isNotEmpty(request.getEndTime())) {
			params.put(END_TIME_PARAM, request.getEndTime());
		}
		if (StringUtils.isNotEmpty(request.getPaginationKey())) {
			params.put(PAGINATION_KEY_PARAM, request.getPaginationKey());
		}
		httpRequest.setQuery(params);
		return apiClient.request(httpRequest).responseType(new TypeToken<PaginatedResult<Transfer>>() {
		}.getType()).doAction();
	}

	@Override
	public TransferDetail getTransferActivity(String accountId, String transferId) {
		assertUsRegion("getTransferActivity");
		Assert.notBlank(Arrays.asList(ACCOUNT_ID_ARG, TRANSFER_ID_ARG), accountId, transferId);
		HttpRequest request = new HttpRequest("/trading/activities/transfer-activities/get", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		params.put(TRANSFER_ID_PARAM, transferId);
		request.setQuery(params);
		return apiClient.request(request).responseType(TransferDetail.class).doAction();
	}

	@Override
	public PaginatedResult<OrderExecution> getOrderExecutions(String accountId, String clientOrderId, String startDate,
			String endDate, String paginationKey) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/trading/orders/executions/list", Versions.V3, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		if (StringUtils.isNotBlank(startDate)) {
			params.put(START_DATE_PARAM, startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			params.put(END_DATE_PARAM, endDate);
		}
		if (StringUtils.isNotBlank(clientOrderId)) {
			params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
		}
		if (StringUtils.isNotEmpty(paginationKey)) {
			params.put(PAGINATION_KEY_PARAM, paginationKey);
		}
		request.setQuery(params);
		return apiClient.request(request).responseType(new TypeToken<PaginatedResult<OrderExecution>>() {
		}.getType()).doAction();
	}

	private void assertUsRegion(String operation) {
		if (region != Region.us) {
			throw new ClientException(ErrorCode.NOT_SUPPORT,
				operation + " is currently supported only for Webull US region, current region is "
					+ region.name() + ".");
		}
	}

    private void addCustomHeadersFromOrder(HttpRequest request, List<TradeOrderItem> orders) {
		if (CollectionUtils.isEmpty(orders)
			|| Objects.isNull(orders.get(0))) {
			return;
		}
        TradeOrderItem tradeOrderItem = orders.get(0);
		if(CollectionUtils.isEmpty(tradeOrderItem.getLegs())){
			List<String> categoryList = Arrays.asList(tradeOrderItem.getMarket(), tradeOrderItem.getInstrumentType());
			String category = StringUtils.join(categoryList, "_");
			if (StringUtils.isNotBlank(category)) {
				request.getHeaders().put(Headers.CATEGORY_KEY, category);
			}
		}else{
			OptionOrderItemLeg item = tradeOrderItem.getLegs().stream().
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

	}

}
