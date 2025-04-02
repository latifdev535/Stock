package com.stock.controller.response;

import com.stock.modal.Stock;

import java.util.List;

public class StockSearchResponse {
    private Integer count;
    private List<Stock> result;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Stock> getResult() {
        return result;
    }

    public void setResult(List<Stock> result) {
        this.result = result;
    }
}
