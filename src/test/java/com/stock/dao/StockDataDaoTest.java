package com.stock.dao;

import com.stock.modal.Stock;
import com.stock.modal.UserStockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StockDataDaoTest {

    @InjectMocks
    private StockDataDao stockDataDao;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private JdbcTemplate ijdbcTemplate;

    @Mock
    private TransactionTemplate transactionTemplate;

    @Captor
    private ArgumentCaptor<TransactionCallbackWithoutResult> transactionCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserPortfolio() {
        UserStockData mockData = new UserStockData();
        mockData.setId("1");
        mockData.setStockSymbol("AAPL");
        mockData.setQty(10);

        when(jdbcTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(List.of(mockData));

        List<UserStockData> result = stockDataDao.getUserPortfolio("user123");

        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).getStockSymbol());
        verify(jdbcTemplate, times(1)).query(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    void testGetStockData_success() {
        UserStockData mockData = new UserStockData();
        mockData.setId("1");
        mockData.setQty(5);

        when(jdbcTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(mockData);

        UserStockData result = stockDataDao.getStockData("user123", "AAPL");

        assertNotNull(result);
        assertEquals(5, result.getQty());
    }

    @Test
    void testGetStockData_emptyResult() {
        when(jdbcTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        UserStockData result = stockDataDao.getStockData("user123", "AAPL");

        assertNull(result);
    }


    @Test
    void testTradeStock_invalidSell() {
        Stock stock = new Stock();
        stock.setSymbol("AAPL");
        stock.setQty("20");
        stock.setType("Sell");

        UserStockData existing = new UserStockData();
        existing.setQty(10);  // trying to sell more than owned
        stockDataDao.tradeStock(stock, "user123", existing);

    }
}
