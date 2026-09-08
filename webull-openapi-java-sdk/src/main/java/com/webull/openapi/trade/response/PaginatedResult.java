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
package com.webull.openapi.trade.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Generic paginated result wrapper for cursor-based pagination using paginationKey.
 * <p>
 * When {@code paginationKey} is null, it indicates there are no more pages.
 * Otherwise, pass it back in the next request to fetch the next page.
 *
 * @param <T> the type of items in the data list
 */
public class PaginatedResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> data;

    private String paginationKey;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * Returns the pagination key for fetching the next page.
     * Null indicates no more pages available.
     */
    public String getPaginationKey() {
        return paginationKey;
    }

    public void setPaginationKey(String paginationKey) {
        this.paginationKey = paginationKey;
    }

    @Override
    public String toString() {
        return "PaginatedResult{" +
                "data=" + data +
                ", paginationKey='" + paginationKey + '\'' +
                '}';
    }
}
