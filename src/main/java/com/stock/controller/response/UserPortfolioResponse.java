package com.stock.controller.response;

import com.stock.modal.UserStockData;

import java.util.List;

public class UserPortfolioResponse {

    private List<UserStockData> portfolioData;

    private Float portfolioValue;

    public List<UserStockData> getPortfolioData() {
        return portfolioData;
    }

    public void setPortfolioData(List<UserStockData> portfolioData) {
        this.portfolioData = portfolioData;
    }

    public Float getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(Float portfolioValue) {
        this.portfolioValue = portfolioValue;
    }
}
