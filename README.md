# Webull OpenAPI Java SDK

Webull OpenAPI aims to provide quantitative trading investors with convenient, fast and secure services. Webull aims to help every quant traders achieve flexible and changeable trading or market strategies.

The main function:

Trading management: create, modify, cancel orders, etc.

Market information: You can query stocks/ETFs and other related market information through the HTTP interface.

Account Information: Query account balance and position information.

Subscription to real-time information: Subscribe to order status changes, market information, etc.

## Requirements

- Please first generate the app key and app secret on the Webull official website.

| Market | Link                               |
|--------|------------------------------------|
| HK     | https://corporate.webull.hk/center |

- Requires JDK 8 and above.

## Interface Protocol

The bottom layer of Webull OpenAPI provides three protocols, HTTP / GRPC / MQTT, to support functions and features like trading, subscriptions for changes of order status and real-time market quotes.

| Protocol | Description                                                                                                 |
|----------|-------------------------------------------------------------------------------------------------------------|
| HTTP     | It mainly provides interface services for data such as tradings, accounts, candlestick charts, snapshots, etc. |
| GRPC	    | Provide real-time push messages for order status changes.                                                   |
| MQTT	    | Provides data services for real-time market conditions.                                                     |

## Developer documentation

| Market | Link                                      |
|--------|-------------------------------------------|
| HK     | https://developer.webull.com/open-api-doc |
