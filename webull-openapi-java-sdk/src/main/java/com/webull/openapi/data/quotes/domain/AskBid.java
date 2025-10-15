/*
 * Copyright 2022 Webull
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
package com.webull.openapi.data.quotes.domain;

import java.util.List;

public class AskBid {

    private String price;
    private String size;
    private List<Order> order;
    private List<Broker> broker;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<Broker> getBroker() {
        return broker;
    }

    public void setBroker(List<Broker> broker) {
        this.broker = broker;
    }

    @Override
    public String toString() {
        return "AskBid{" +
                "price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", order=" + order +
                ", broker=" + broker +
                '}';
    }
}
