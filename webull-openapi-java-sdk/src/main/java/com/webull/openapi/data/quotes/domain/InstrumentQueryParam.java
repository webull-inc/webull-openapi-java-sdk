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

import java.util.Set;

public class InstrumentQueryParam {

    private String category;
    private Set<String> symbols;
    private String status;
    private String lastInstrumentId;
    private int count = 1000;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Set<String> symbols) {
        this.symbols = symbols;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastInstrumentId() {
        return lastInstrumentId;
    }

    public void setLastInstrumentId(String lastInstrumentId) {
        this.lastInstrumentId = lastInstrumentId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
