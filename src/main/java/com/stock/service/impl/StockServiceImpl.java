package com.stock.service.impl;

import com.stock.controller.request.StockTradeRequest;
import com.stock.controller.response.StockHistResponse;
import com.stock.controller.response.StockTradeResponse;
import com.stock.controller.response.UserPortfolioResponse;
import com.stock.dao.StockDataDao;
import com.stock.dao.StockHistDao;
import com.stock.modal.*;
import com.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class StockServiceImpl implements StockService {

    @Value("${finhub.api.token}")
    private String finHubApiToken;

    @Value("${finhub.api.stock.symbol.url}")
    private String finHubStockUrl;

    @Autowired
    private StockDataDao stockDataDao;

    @Autowired
    private StockHistDao stockHistDao;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserPortfolioResponse getUserPortfolio(String userId) {

        float portfolioValue = 0.0F;
        UserPortfolioResponse userPortfolioResponse = new UserPortfolioResponse();
        List<UserStockData> userPortfolio = stockDataDao.getUserPortfolio(userId);
        userPortfolioResponse.setPortfolioData(userPortfolio);

        for (UserStockData userStockData : userPortfolio) {
            String uri =  finHubStockUrl.concat(userStockData.getStockSymbol()).concat("&token=").concat(finHubApiToken);
            FinHubStockData finHubStockData = restTemplate.getForObject(uri, FinHubStockData.class);
            portfolioValue+=(Float.valueOf(finHubStockData.getC())*userStockData.getQty());
            userStockData.setCurrentPrice(finHubStockData.getC());
        }
        userPortfolioResponse.setPortfolioValue(portfolioValue);

        return userPortfolioResponse;
    }

    @Override
    public StockTradeResponse tradeStock(StockTradeRequest stockTradeRequest) {
        StockTradeResponse stockTradeResponse = new StockTradeResponse();
        Stock stock = new Stock();
        stock.setCurrentPrice(stockTradeRequest.getCurrentPrice());
        stock.setSymbol(stockTradeRequest.getSymbol());
        stock.setQty(stockTradeRequest.getQty());
        stock.setType(stockTradeRequest.getOperation());
        UserStockData stockData = stockDataDao.getStockData(stockTradeRequest.getUserId(), stockTradeRequest.getSymbol());

        if (stockDataDao.tradeStock(stock,stockTradeRequest.getUserId(),stockData)) {
            stockTradeResponse.setStatus(Status.SUCCESS);
        } else {
            stockTradeResponse.setStatus(Status.FAILED);
        }
        return stockTradeResponse;
    }


    @Override
    public StockHistResponse getUserPortfolioHist(String userId) {
        StockHistResponse stockHistResponse = new StockHistResponse();
        List<StockHist> stockHist = stockHistDao.getStockHist(userId);
        stockHistResponse.setStatus(Status.SUCCESS);
        stockHistResponse.setStockHistList(stockHist);
        return stockHistResponse;
    }

    @Override
    public FinHubStockData getCurrentPrice(String symbol) {
        return restTemplate.getForObject(finHubStockUrl.concat(symbol).concat("&token=").concat(finHubApiToken), FinHubStockData.class);
    }

}
