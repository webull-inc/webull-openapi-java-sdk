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
 * Response of the insider transactions API (US only).
 * {@code paginationKey} is null when there are no more pages.
 */
public class InsiderTransactionResult {

    private String paginationKey;
    private List<InsiderTransaction> data;

    public String getPaginationKey() {
        return paginationKey;
    }

    public void setPaginationKey(String paginationKey) {
        this.paginationKey = paginationKey;
    }

    public List<InsiderTransaction> getData() {
        return data;
    }

    public void setData(List<InsiderTransaction> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InsiderTransactionResult{" +
                "paginationKey='" + paginationKey + '\'' +
                ", data=" + data +
                '}';
    }
}
