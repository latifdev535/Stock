package com.stock.modal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinHubStockDataTest {

    @Test
    void testGetterAndSetter() {
        FinHubStockData stockData = new FinHubStockData();

        // Test "c" property
        stockData.setC("100");
        assertEquals("100", stockData.getC(), "The value of 'c' should be '100'");

        // Test "d" property
        stockData.setD("50");
        assertEquals("50", stockData.getD(), "The value of 'd' should be '50'");

        // Test "dp" property
        stockData.setDp("2.5");
        assertEquals("2.5", stockData.getDp(), "The value of 'dp' should be '2.5'");

        // Test "h" property
        stockData.setH("150");
        assertEquals("150", stockData.getH(), "The value of 'h' should be '150'");

        // Test "l" property
        stockData.setL("90");
        assertEquals("90", stockData.getL(), "The value of 'l' should be '90'");

        // Test "o" property
        stockData.setO("95");
        assertEquals("95", stockData.getO(), "The value of 'o' should be '95'");

        // Test "pc" property
        stockData.setPc("85");
        assertEquals("85", stockData.getPc(), "The value of 'pc' should be '85'");

        // Test "t" property
        stockData.setT("1627898400");
        assertEquals("1627898400", stockData.getT(), "The value of 't' should be '1627898400'");
    }
}