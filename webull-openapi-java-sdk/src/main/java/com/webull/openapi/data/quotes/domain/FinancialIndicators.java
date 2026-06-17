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
import java.util.Map;

/**
 * Financial indicators response.
 * The "values" field contains various financial indicator arrays keyed by indicator name.
 * Indicators include: roa (Return on Total Assets), roe (Return on Net Assets),
 * net_margin (Net profit margin), diluted_eps_incl_extra (Earnings per share),
 * debt_to_assets (Debt ratio), naps (Per-share net asset value),
 * ocf_ps (Per-share cash flow), cap_surplus_ps (Per-share reserve fund).
 */
public class FinancialIndicators {

    /**
     * Currency
     */
    private String currency;

    /**
     * Financial indicator values. Keys are indicator names (e.g., "roa", "roe", "net_margin",
     * "diluted_eps_incl_extra", "debt_to_assets", "naps", "ocf_ps", "cap_surplus_ps").
     * Each value is a list of period data entries.
     */
    private Map<String, List<FinancialIndicatorValue>> values;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Map<String, List<FinancialIndicatorValue>> getValues() {
        return values;
    }

    public void setValues(Map<String, List<FinancialIndicatorValue>> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "FinancialIndicators{" +
                "currency='" + currency + '\'' +
                ", values=" + values +
                '}';
    }
}
