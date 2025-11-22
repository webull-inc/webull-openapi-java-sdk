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
package com.webull.openapi.trade.response.v2;

import java.util.List;


public class AccountBalanceInfo {

    private String totalAssetCurrency;

    private String totalCashBalance;

    private String totalUnrealizedProfitLoss;

    private String totalMarketValue;

    private String totalNetLiquidationValue;

    private String totalDayProfitLoss;

    private String dayTradesLeft;

    private String maintenanceMargin;

    private List<String> openMarginCalls;

    private List<AccountAssetInfo> accountCurrencyAssets;

    public String getTotalAssetCurrency() {
        return totalAssetCurrency;
    }

    public void setTotalAssetCurrency(String totalAssetCurrency) {
        this.totalAssetCurrency = totalAssetCurrency;
    }

    public String getTotalCashBalance() {
        return totalCashBalance;
    }

    public void setTotalCashBalance(String totalCashBalance) {
        this.totalCashBalance = totalCashBalance;
    }

    public String getTotalUnrealizedProfitLoss() {
        return totalUnrealizedProfitLoss;
    }

    public void setTotalUnrealizedProfitLoss(String totalUnrealizedProfitLoss) {
        this.totalUnrealizedProfitLoss = totalUnrealizedProfitLoss;
    }

    public String getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(String totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public String getTotalNetLiquidationValue() {
        return totalNetLiquidationValue;
    }

    public void setTotalNetLiquidationValue(String totalNetLiquidationValue) {
        this.totalNetLiquidationValue = totalNetLiquidationValue;
    }

    public String getTotalDayProfitLoss() {
        return totalDayProfitLoss;
    }

    public void setTotalDayProfitLoss(String totalDayProfitLoss) {
        this.totalDayProfitLoss = totalDayProfitLoss;
    }

    public String getDayTradesLeft() {
        return dayTradesLeft;
    }

    public void setDayTradesLeft(String dayTradesLeft) {
        this.dayTradesLeft = dayTradesLeft;
    }

    public String getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(String maintenanceMargin) {
        this.maintenanceMargin = maintenanceMargin;
    }

    public List<String> getOpenMarginCalls() {
        return openMarginCalls;
    }

    public void setOpenMarginCalls(List<String> openMarginCalls) {
        this.openMarginCalls = openMarginCalls;
    }

    public List<AccountAssetInfo> getAccountCurrencyAssets() {
        return accountCurrencyAssets;
    }

    public void setAccountCurrencyAssets(List<AccountAssetInfo> accountCurrencyAssets) {
        this.accountCurrencyAssets = accountCurrencyAssets;
    }

    @Override
    public String toString() {
        return "AccountBalanceInfo{" +
                "totalAssetCurrency='" + totalAssetCurrency + '\'' +
                ", totalCashBalance='" + totalCashBalance + '\'' +
                ", totalUnrealizedProfitLoss='" + totalUnrealizedProfitLoss + '\'' +
                ", totalMarketValue='" + totalMarketValue + '\'' +
                ", totalNetLiquidationValue='" + totalNetLiquidationValue + '\'' +
                ", totalDayProfitLoss='" + totalDayProfitLoss + '\'' +
                ", dayTradesLeft='" + dayTradesLeft + '\'' +
                ", maintenanceMargin='" + maintenanceMargin + '\'' +
                ", openMarginCalls='" + openMarginCalls + '\'' +
                ", accountCurrencyAssets=" + accountCurrencyAssets +
                '}';
    }
}
