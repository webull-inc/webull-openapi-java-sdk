package com.webull.openapi.samples.trade;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.core.exception.ClientException;
import com.webull.openapi.core.exception.ServerException;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.serialize.JsonSerializer;
import com.webull.openapi.core.sse.ISseClient;
import com.webull.openapi.core.sse.ISseSubscription;
import com.webull.openapi.core.sse.message.SseSubscribeRequest;
import com.webull.openapi.core.sse.message.SseSubscribeResponse;
import com.webull.openapi.samples.config.Env;
import com.webull.openapi.trade.TradeEventSseClient;
import com.webull.openapi.trade.events.subscribe.message.EventType;

import java.util.Map;

public class TradeEventsSseClient {

    private static final Logger logger = LoggerFactory.getLogger(TradeEventsSseClient.class);

    public static void main(String[] args) {
        try (ISseClient client = TradeEventSseClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                 .host("<event_api_endpoint>")
                .onMessage(TradeEventsSseClient::handleEventMessage)
                .build()) {

            SseSubscribeRequest request = new SseSubscribeRequest("<your_account_id>");

            ISseSubscription subscription = client.subscribe(request);
            subscription.blockingAwait();

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }

    private static void handleEventMessage(SseSubscribeResponse response) {
        if (SseSubscribeResponse.CONTENT_TYPE_JSON.equals(response.getContentType())) {
            Map<String, Object> payload = JsonSerializer.fromJson(response.getPayload(),
                    new TypeToken<Map<String, Object>>(){}.getType());
            if (EventType.Order.getCode() == response.getEventType()) {
                logger.info("----request_id:{}----", payload.get("request_id"));
                logger.info("account:{}", payload.get("account_id"));
                logger.info("client_order_id: {}", payload.get("client_order_id"));
                logger.info("order_status:{}", payload.get("order_status"));
            }
            if (EventType.Position.getCode() == response.getEventType()) {
                logger.info("event payload:{}", payload);
            }

            if (EventType.Option.getCode() == response.getEventType()) {
                logger.info("option payload:{}", payload);
            }
        }
    }
}
