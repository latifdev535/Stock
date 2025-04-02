package com.stock.controller;

import com.stock.controller.response.LoginResponse;
import com.stock.controller.response.StockSearchResponse;
import com.stock.modal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class StockController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/stocklist/{search}")
    public StockSearchResponse getStockList(@PathVariable("search") String search) {
        StockSearchResponse result = new StockSearchResponse();
        result.setResult(new ArrayList<>());
        result.setCount(0);
        // https://finnhub.io/api/v1/quote?symbol=PPP.V&token=cvlhvdpr01qj3umdh5h0cvlhvdpr01qj3umdh5hg
        if (Objects.nonNull(search) && search.length() > 0) {
             String uri = "https://finnhub.io/api/v1/search?q=" + search + "&token=cvlhvdpr01qj3umdh5h0cvlhvdpr01qj3umdh5hg";
            uri = "https://finnhub.io/api/v1/search?q=" + search + "&exchange=US&token=cvlhvdpr01qj3umdh5h0cvlhvdpr01qj3umdh5hg";
            RestTemplate restTemplate = new RestTemplate();
             result = restTemplate.getForObject(uri, StockSearchResponse.class);
        }
        return result;
    }

    @GetMapping("/stock/{symbol}")
    public StockData getCurrentPrice(@PathVariable("symbol") String symbol) {
       String uri =  "https://finnhub.io/api/v1/quote?symbol="+symbol+"&token=cvlhvdpr01qj3umdh5h0cvlhvdpr01qj3umdh5hg";
        RestTemplate restTemplate = new RestTemplate();
        StockData forObject = restTemplate.getForObject(uri, StockData.class);
        return forObject;
    }

    @PostMapping("/add-stock")
    public LoginResponse addStock(@RequestBody Stock loginRequest) {


        int update = jdbcTemplate.update("insert into USER_STOCK_DATA (user_id,stock_symbol,stock_qty,invested_amt)" +
                " values('1','" + loginRequest.getSymbol() + "','" + loginRequest.getCurrentPrice() + "','100')");

        System.out.println(jdbcTemplate.queryForList("select * from users"));
        return null;
    }



    @GetMapping("/portfolio/{userid}")
    public List<Map<String, Object>> getPortfolio(@PathVariable("userid") String userid) {

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from USER_STOCK_DATA");

        return maps;
    }


}
