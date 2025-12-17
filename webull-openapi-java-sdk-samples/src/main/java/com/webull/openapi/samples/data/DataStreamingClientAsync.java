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

public class DataStreamingClientAsync {

    private static final Logger logger = LoggerFactory.getLogger(DataStreamingClientAsync.class);

    public static void main(String[] args) {

        Set<String> symbols = new HashSet<>();
        symbols.add("AAPL");

        Set<String> subTypes = new HashSet<>();
        subTypes.add(SubscribeType.SNAPSHOT.name());
        subTypes.add(SubscribeType.QUOTE.name());
        subTypes.add(SubscribeType.TICK.name());

        String category = Category.US_STOCK.name();
        String depth = "1";
        boolean overnightRequired = false;


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
                .onMessage(DataStreamingClientAsync::handleMarketData)
                .addSubscription(symbols, category, subTypes, depth, overnightRequired)
                .build()) {

            // connect
            client.connectBlocking();

            // subscribe asynchronously.
            client.subscribeAsync();

            // waiting to unsubscribe
            long ticker = 30;
            int waitTime = 1;
            long startTime = System.currentTimeMillis();
            while (true) {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                if (elapsed >= ticker) {
                    logger.info("Wait completed, start remove subscription...");
                    break;
                }
                logger.info("Waiting {} seconds before remove subscription... (elapsed {}s / {}s)", waitTime, elapsed, ticker);
                Thread.sleep(waitTime * 1000L);
            }
            client.removeSubscriptionAsync(symbols, category, subTypes);
            logger.info("Asynchronous call to cancel subscription succeeded.");


            // waiting to subscribe
            startTime = System.currentTimeMillis();
            while (true) {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                if (elapsed >= ticker) {
                    logger.info("Wait completed, start subscribing...");
                    break;
                }
                logger.info("Waiting {} seconds before subscription... (elapsed {}s / {}s)", waitTime, elapsed, ticker);
                Thread.sleep(waitTime * 1000L);
            }
            client.addSubscriptionAsync(symbols, category, subTypes, depth, overnightRequired);
            logger.info("Asynchronous call to subscribe succeeded.");


            // waiting to disconnect
            startTime = System.currentTimeMillis();
            while (true) {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                if (elapsed >= ticker) {
                    logger.info("Wait completed, start disconnect...");
                    break;
                }
                logger.info("Waiting {} seconds before disconnect... (elapsed {}s / {}s)", waitTime, elapsed, ticker);
                Thread.sleep(waitTime * 1000L);
            }
            client.disconnectAsync();
            logger.info("Asynchronous call to disconnect succeeded.");

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
}
