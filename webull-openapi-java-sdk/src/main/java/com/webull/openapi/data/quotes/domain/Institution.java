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

/**
 * An institutional investor returned by the institution search API (US only).
 */
public class Institution {

    private String cik;
    private String name;
    private String notablePeople;

    public String getCik() {
        return cik;
    }

    public void setCik(String cik) {
        this.cik = cik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotablePeople() {
        return notablePeople;
    }

    public void setNotablePeople(String notablePeople) {
        this.notablePeople = notablePeople;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "cik='" + cik + '\'' +
                ", name='" + name + '\'' +
                ", notablePeople='" + notablePeople + '\'' +
                '}';
    }
}
