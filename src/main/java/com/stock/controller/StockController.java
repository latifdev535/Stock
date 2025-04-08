package com.stock.controller;

import com.stock.controller.request.StockTradeRequest;
import com.stock.controller.response.StockHistResponse;
import com.stock.controller.response.StockTradeResponse;
import com.stock.controller.response.UserPortfolioResponse;
import com.stock.modal.*;
import com.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class StockController {


    @Autowired
    private StockService stockService;

    @Autowired
    private RestTemplate restTemplate;

//    @GetMapping("/stocklist/{search}")
//    public StockSearchResponse getStockList(@PathVariable("search") String search) {
//        StockSearchResponse result = new StockSearchResponse();
//        result.setResult(new ArrayList<>());
//        result.setCount(0);
//        // https://finnhub.io/api/v1/quote?symbol=PPP.V&token=cvlhvdpr01qj3umdh5h0cvlhvdpr01qj3umdh5hg
//        if (Objects.nonNull(search) && search.length() > 0) {
//             String uri = "https://finnhub.io/api/v1/search?q=" + search + "&token=cvlhvdpr01qj3umdh5h0cvlhvdpr01qj3umdh5hg";
//            uri = "https://finnhub.io/api/v1/search?q=" + search + "&exchange=US&token=cvlhvdpr01qj3umdh5h0cvlhvdpr01qj3umdh5hg";
//            RestTemplate restTemplate = new RestTemplate();
//             result = restTemplate.getForObject(uri, StockSearchResponse.class);
//        }
//        return result;
//    }

    @GetMapping("/stock/{symbol}")
    public FinHubStockData getCurrentPrice(@PathVariable("symbol") String symbol) {
        return stockService.getCurrentPrice(symbol);
    }

    @PostMapping("/trade-stock")
    public StockTradeResponse tradeStock(@RequestBody StockTradeRequest stockTradeRequest) {
        StockTradeResponse stockTradeResponse = stockService.tradeStock(stockTradeRequest);
        return stockTradeResponse;
    }



    @GetMapping("/portfolio/user/{userid}")
    public UserPortfolioResponse getUserPortfolio(@PathVariable("userid") String userid) {
        return stockService.getUserPortfolio(userid);
    }


    @GetMapping("/portfoliohist/user/{userid}")
    public StockHistResponse getUserPortfolioHist(@PathVariable("userid") String userid) {
        return stockService.getUserPortfolioHist(userid);
    }

}
