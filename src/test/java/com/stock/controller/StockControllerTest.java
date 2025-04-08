package com.stock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.controller.request.StockTradeRequest;
import com.stock.controller.response.StockHistResponse;
import com.stock.controller.response.StockTradeResponse;
import com.stock.controller.response.UserPortfolioResponse;
import com.stock.modal.FinHubStockData;
import com.stock.modal.Status;
import com.stock.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(stockService);
    }

    @Test
    void testGetCurrentPrice() throws Exception {
        String symbol = "AAPL";
        FinHubStockData finHubStockData = new FinHubStockData();
        finHubStockData.setC("150.00");

        when(stockService.getCurrentPrice(symbol)).thenReturn(finHubStockData);

        mockMvc.perform(get("/stock/{symbol}", symbol))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.c").value("150.00"));

        verify(stockService, times(1)).getCurrentPrice(symbol);
    }

    @Test
    void testTradeStock() throws Exception {
        StockTradeRequest stockTradeRequest = new StockTradeRequest();
        stockTradeRequest.setUserId("user123");
        stockTradeRequest.setSymbol("AAPL");
        stockTradeRequest.setQty("10");
        stockTradeRequest.setOperation("BUY");
        stockTradeRequest.setCurrentPrice("150");

        StockTradeResponse stockTradeResponse = new StockTradeResponse();
        stockTradeResponse.setStatus(Status.SUCCESS);

        when(stockService.tradeStock(any(StockTradeRequest.class))).thenReturn(stockTradeResponse);

        mockMvc.perform(post("/trade-stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockTradeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(stockService, times(1)).tradeStock(any(StockTradeRequest.class));
    }

    @Test
    void testGetUserPortfolio() throws Exception {
        String userid = "user123";
        UserPortfolioResponse userPortfolioResponse = new UserPortfolioResponse();
        userPortfolioResponse.setPortfolioValue(1500.0F);

        when(stockService.getUserPortfolio(userid)).thenReturn(userPortfolioResponse);

        mockMvc.perform(get("/portfolio/user/{userid}", userid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.portfolioValue").value(1500.0F));

        verify(stockService, times(1)).getUserPortfolio(userid);
    }

    @Test
    void testGetUserPortfolioHist() throws Exception {
        String userid = "user123";
        StockHistResponse stockHistResponse = new StockHistResponse();
        stockHistResponse.setStatus(Status.SUCCESS);

        when(stockService.getUserPortfolioHist(userid)).thenReturn(stockHistResponse);

        mockMvc.perform(get("/portfoliohist/user/{userid}", userid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(stockService, times(1)).getUserPortfolioHist(userid);
    }
}