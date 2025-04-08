package com.stock.modal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void testGetterAndSetter() {
        Stock stock = new Stock();

        // Test "description" property
        stock.setDescription("Test Description");
        assertEquals("Test Description", stock.getDescription(), "The value of 'description' should be 'Test Description'");

        // Test "displaySymbol" property
        stock.setDisplaySymbol("TST");
        assertEquals("TST", stock.getDisplaySymbol(), "The value of 'displaySymbol' should be 'TST'");

        // Test "symbol" property
        stock.setSymbol("TEST");
        assertEquals("TEST", stock.getSymbol(), "The value of 'symbol' should be 'TEST'");

        // Test "type" property
        stock.setType("Equity");
        assertEquals("Equity", stock.getType(), "The value of 'type' should be 'Equity'");

        // Test "currentPrice" property
        stock.setCurrentPrice("125.50");
        assertEquals("125.50", stock.getCurrentPrice(), "The value of 'currentPrice' should be '125.50'");

        // Test "qty" property
        stock.setQty("10");
        assertEquals("10", stock.getQty(), "The value of 'qty' should be '10'");
    }
}