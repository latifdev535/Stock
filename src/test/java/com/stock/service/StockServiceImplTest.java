package com.stock.service;

import com.stock.controller.request.StockTradeRequest;
import com.stock.controller.response.StockHistResponse;
import com.stock.controller.response.StockTradeResponse;
import com.stock.controller.response.UserPortfolioResponse;
import com.stock.dao.StockDataDao;
import com.stock.dao.StockHistDao;
import com.stock.modal.*;
import com.stock.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockDataDao stockDataDao;

    @Mock
    private StockHistDao stockHistDao;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Simulating @Value fields
        ReflectionTestUtils.setField(stockService, "finHubApiToken", "dummy_token");
        ReflectionTestUtils.setField(stockService, "finHubStockUrl", "https://api.finhub.com/quote?symbol=");

    }

    @Test
    void testGetUserPortfolio() {
        String userId = "user123";
        UserStockData stockData = new UserStockData();
        stockData.setStockSymbol("AAPL");
        stockData.setQty(2);

        FinHubStockData finHubData = new FinHubStockData();
        finHubData.setC("150.0");

        when(stockDataDao.getUserPortfolio(userId)).thenReturn(List.of(stockData));
        when(restTemplate.getForObject(contains("AAPL"), eq(FinHubStockData.class))).thenReturn(finHubData);

        UserPortfolioResponse response = stockService.getUserPortfolio(userId);

        assertNotNull(response);
        assertEquals(1, response.getPortfolioData().size());
        assertEquals(300.0F, response.getPortfolioValue());
        assertEquals("150.0", response.getPortfolioData().get(0).getCurrentPrice());

        verify(stockDataDao).getUserPortfolio(userId);
        verify(restTemplate).getForObject(anyString(), eq(FinHubStockData.class));
    }

    @Test
    void testTradeStock_Success() {
        StockTradeRequest request = new StockTradeRequest();
        request.setCurrentPrice("100.0");
        request.setSymbol("AAPL");
        request.setQty("5");
        request.setOperation("BUY");
        request.setUserId("user123");

        UserStockData stockData = new UserStockData();

        when(stockDataDao.getStockData("user123", "AAPL")).thenReturn(stockData);
        when(stockDataDao.tradeStock(any(Stock.class), eq("user123"), eq(stockData))).thenReturn(true);

        StockTradeResponse response = stockService.tradeStock(request);

        assertEquals(Status.SUCCESS, response.getStatus());
    }

    @Test
    void testTradeStock_Failure() {
        StockTradeRequest request = new StockTradeRequest();
        request.setCurrentPrice("100.0");
        request.setSymbol("AAPL");
        request.setQty("5");
        request.setOperation("SELL");
        request.setUserId("user123");

        UserStockData stockData = new UserStockData();

        when(stockDataDao.getStockData("user123", "AAPL")).thenReturn(stockData);
        when(stockDataDao.tradeStock(any(Stock.class), eq("user123"), eq(stockData))).thenReturn(false);

        StockTradeResponse response = stockService.tradeStock(request);

        assertEquals(Status.FAILED, response.getStatus());
    }

    @Test
    void testGetUserPortfolioHist() {
        String userId = "user123";
        StockHist hist = new StockHist();
        hist.setStockSymbol("GOOG");

        when(stockHistDao.getStockHist(userId)).thenReturn(List.of(hist));

        StockHistResponse response = stockService.getUserPortfolioHist(userId);

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals(1, response.getStockHistList().size());
        assertEquals("GOOG", response.getStockHistList().get(0).getStockSymbol());
    }

        @Test
        void testGetCurrentPrice() {
            String symbol = "AAPL";
            FinHubStockData finHubStockData = new FinHubStockData();
            finHubStockData.setC("150");

            when(restTemplate.getForObject(anyString(), eq(FinHubStockData.class))).thenReturn(finHubStockData);

            FinHubStockData response = stockService.getCurrentPrice(symbol);

            assertNotNull(response, "Response should not be null");
            assertEquals("150", response.getC(), "Current price should be fetched correctly");
            verify(restTemplate, times(1)).getForObject(anyString(), eq(FinHubStockData.class));
        }


}