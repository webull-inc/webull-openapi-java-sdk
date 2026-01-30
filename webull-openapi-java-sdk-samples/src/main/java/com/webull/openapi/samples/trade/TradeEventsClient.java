package com.webull.openapi.samples.trade;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ServerException;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.serialize.JsonSerializer;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.events.subscribe.ISubscription;
import com.webull.openapi.trade.events.subscribe.ITradeEventClient;
import com.webull.openapi.trade.events.subscribe.message.EventType;
import com.webull.openapi.trade.events.subscribe.message.SubscribeRequest;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;

import java.util.Map;

public class TradeEventsClient {

    private static final Logger logger = LoggerFactory.getLogger(TradeEventsClient.class);

    public static void main(String[] args) {
        try (ITradeEventClient client = ITradeEventClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                // .host("<event_api_endpoint>")
                .onMessage(TradeEventsClient::handleEventMessage)
                .build()) {

            SubscribeRequest request = new SubscribeRequest("<your_account_id>");

            ISubscription subscription = client.subscribe(request);
            subscription.blockingAwait();

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }

    private static void handleEventMessage(SubscribeResponse response) {
        if (SubscribeResponse.CONTENT_TYPE_JSON.equals(response.getContentType())) {
            Map<String, String> payload = JsonSerializer.fromJson(response.getPayload(),
                    new TypeToken<Map<String, String>>(){}.getType());
            if (EventType.Order.getCode() == response.getEventType()) {
                logger.info("----request_id:{}----", payload.get("request_id"));
                logger.info(payload.get("account_id"));
                logger.info(payload.get("client_order_id"));
                logger.info(payload.get("order_status"));
            }
            if (EventType.Position.getCode() == response.getEventType()) {
                logger.info("event payload:{}", payload);
            }
        }
    }
}
