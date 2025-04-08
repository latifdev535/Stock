package com.stock.dao;

import com.stock.modal.StockHist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StockHistDaoTest {

    @InjectMocks
    private StockHistDao stockHistDao;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStockHist_ReturnsCorrectData() {
        // Given
        String userId = "user123";
        StockHist stockHist = new StockHist();
        stockHist.setStockSymbol("AAPL");
        stockHist.setQty("10");
        stockHist.setDateTime("2025-04-07");
        stockHist.setOperation("BUY");

        when(jdbcTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenReturn(List.of(stockHist));

        // When
        List<StockHist> result = stockHistDao.getStockHist(userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).getStockSymbol());
        assertEquals("10", result.get(0).getQty());
        assertEquals("2025-04-07", result.get(0).getDateTime());
        assertEquals("BUY", result.get(0).getOperation());

        verify(jdbcTemplate, times(1))
                .query(anyString(), anyMap(),any(RowMapper.class));
    }
}
