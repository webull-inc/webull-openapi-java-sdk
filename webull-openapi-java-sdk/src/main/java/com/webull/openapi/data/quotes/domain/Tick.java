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

public class Tick extends QuotesBasic {

    private List<TickRecord> result;

    public List<TickRecord> getResult() {
        return result;
    }

    public void setResult(List<TickRecord> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Tick{" +
                "  symbol='" + symbol + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
