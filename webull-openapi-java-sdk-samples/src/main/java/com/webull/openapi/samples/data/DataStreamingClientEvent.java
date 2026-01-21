package com.webull.openapi.samples.data;

import com.webull.openapi.core.common.dict.Category;
import com.webull.openapi.core.common.dict.SubscribeType;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ServerException;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.serialize.JsonSerializer;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.data.quotes.subsribe.IDataStreamingClient;
import com.webull.openapi.data.quotes.subsribe.message.MarketData;
import com.webull.openapi.samples.config.Env;

import java.util.HashSet;
import java.util.Set;

public class DataStreamingClientEvent {

    private static final Logger logger = LoggerFactory.getLogger(DataStreamingClientEvent.class);

    public static void main(String[] args) {
        Set<String> symbols = new HashSet<>();
        symbols.add("KXNHLGAME-26JAN21ANACOL-ANA");
        symbols.add("KXNBAGAME-26JAN20LACCHI-LAC");

        Set<String> subTypes = new HashSet<>();
        subTypes.add(SubscribeType.QUOTE.name());
        subTypes.add(SubscribeType.SNAPSHOT.name());

        try (IDataStreamingClient client = IDataStreamingClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .sessionId(GUID.get())
                .regionId(Env.REGION_ID)
                //.http_host("<api_endpoint>")
                //.mqtt_host("<quotes_endpoint>")
                /*
                 * The tokenDir parameter can be used to specify the directory for storing the 2FA token. Both absolute and relative paths are supported and this option has the highest priority.
                 * Alternatively, the storage directory can be configured via an environment variable with the key WEBULL_OPENAPI_TOKEN_DIR, which also supports both absolute and relative paths.
                 * If neither is specified, the default configuration will be used, and the token will be stored at conf/token.txt under the current working directory.
                 */
                //.tokenDir("conf_custom_relative")
                .onMessage(DataStreamingClientEvent::handleMarketData)
                .addSubscription(symbols, Category.US_EVENT.name(), subTypes, "1", false)
                .build()) {

            // subscribe blocking.
            subscribeBlocking(client);

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }

    private static void handleMarketData(MarketData marketData) {
        // your code...
        logger.info("Received market data: {}", JsonSerializer.toJson(marketData));
    }


    private static void subscribeBlocking(IDataStreamingClient client) {
        client.connectBlocking();
        logger.info("Connect completed.");
        client.subscribeBlocking();
        logger.info("Subscribe completed.");
    }

}
