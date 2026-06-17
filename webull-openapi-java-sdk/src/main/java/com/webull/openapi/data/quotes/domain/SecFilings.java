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

/**
 * SEC filings response for a stock.
 */
public class SecFilings {

    /** Security symbol */
    private String symbol;
    /** Security category */
    private String category;
    /** List of filings */
    private List<SecFilingItem> filings;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<SecFilingItem> getFilings() {
        return filings;
    }

    public void setFilings(List<SecFilingItem> filings) {
        this.filings = filings;
    }

    @Override
    public String toString() {
        return "SecFilings{" +
                "symbol='" + symbol + '\'' +
                ", category='" + category + '\'' +
                ", filings=" + filings +
                '}';
    }
}
