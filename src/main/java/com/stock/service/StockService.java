package com.stock.service;

import com.stock.controller.request.StockTradeRequest;
import com.stock.controller.response.StockHistResponse;
import com.stock.controller.response.StockTradeResponse;
import com.stock.controller.response.UserPortfolioResponse;
import com.stock.modal.FinHubStockData;

public interface StockService {

    UserPortfolioResponse getUserPortfolio(String userId);

    StockTradeResponse tradeStock(StockTradeRequest stockTradeRequest);

//    StockTradeResponse tradeStock(String userId);

    StockHistResponse getUserPortfolioHist(String userId);


    FinHubStockData getCurrentPrice(String symbol);
}
