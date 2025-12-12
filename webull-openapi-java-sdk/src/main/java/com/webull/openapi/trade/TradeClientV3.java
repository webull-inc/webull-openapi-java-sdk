package com.webull.openapi.trade;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.core.common.Headers;
import com.webull.openapi.core.common.Region;
import com.webull.openapi.core.common.Versions;
import com.webull.openapi.core.common.dict.InstrumentSuperType;
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
import com.webull.openapi.trade.http.ITradeV3Client;
import com.webull.openapi.trade.request.v3.*;
import com.webull.openapi.trade.response.TradeCalendar;
import com.webull.openapi.trade.response.v3.*;

import java.util.*;

public class TradeClientV3 implements ITradeV3Client {

	private static final String ACCOUNT_ID_ARG = "accountId";
	private static final String TRADE_ORDER_ARG = "tradeOrder";
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

	private static final String CLIENT_COMBO_ORDER_ID_PARAM = "client_combo_order_id";
	private static final String NEW_ORDERS_PARAM = "new_orders";
	private static final String MODIFY_ORDERS_PARAM = "modify_orders";

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
		HttpRequest request = new HttpRequest("/openapi/account/list", Versions.V2, HttpMethod.GET);
		return apiClient.request(request).responseType(new TypeToken<List<Account>>() {
		}.getType()).doAction();
	}

	@Override
	public AccountBalanceInfo balanceAccount(String accountId) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/openapi/assets/balance", Versions.V2, HttpMethod.GET);
		Map<String, Object> params = new HashMap<>();
		params.put(ACCOUNT_ID_PARAM, accountId);
		request.setQuery(params);
		return apiClient.request(request).responseType(AccountBalanceInfo.class).doAction();
	}

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

	@Override
	public PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/openapi/trade/order/preview", Versions.V2, HttpMethod.POST);

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
		HttpRequest request = new HttpRequest("/openapi/trade/order/place", Versions.V2, HttpMethod.POST);
		addCustomHeadersFromOrder(request, tradeOrder);

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
	public TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
		Assert.notEmpty(MODIFY_ORDERS_ARG, tradeOrder.getModifyOrders());
		HttpRequest request = new HttpRequest("/openapi/trade/order/replace", Versions.V2, HttpMethod.POST);

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
		HttpRequest request = new HttpRequest("/openapi/trade/order/cancel", Versions.V2, HttpMethod.POST);

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
		return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {
		}.getType()).doAction();
	}

	@Override
	public List<OrderHistory> openOrders(String accountId, Integer pageSize, String lastClientOrderId) {
		Assert.notBlank(ACCOUNT_ID_ARG, accountId);
		HttpRequest request = new HttpRequest("/openapi/trade/order/open", Versions.V2, HttpMethod.GET);
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
		HttpRequest request = new HttpRequest("/openapi/trade/order/detail", Versions.V2, HttpMethod.GET);
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

	private void addCustomHeadersFromOrder(HttpRequest request, TradeOrder tradeOrder) {
		if (Objects.isNull(tradeOrder)
			|| CollectionUtils.isEmpty(tradeOrder.getNewOrders())
			|| Objects.isNull(tradeOrder.getNewOrders().get(0))) {
			return;
		}

		if(CollectionUtils.isEmpty(tradeOrder.getNewOrders().get(0).getLegs())){
			TradeOrderItem item = tradeOrder.getNewOrders().get(0);
			List<String> categoryList = Arrays.asList(item.getMarket(), item.getInstrumentType());
			String category = StringUtils.join(categoryList, "_");
			if (StringUtils.isNotBlank(category)) {
				request.getHeaders().put(Headers.CATEGORY_KEY, category);
			}
		}else{
			OptionOrderItemLeg item = tradeOrder.getNewOrders().get(0).getLegs().stream().
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
