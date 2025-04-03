package com.stock.controller.response;

import com.stock.modal.Status;
import com.stock.modal.StockHist;

import java.util.List;

public class StockHistResponse {
    private Status status;
    private List<StockHist> stockHistList;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<StockHist> getStockHistList() {
        return stockHistList;
    }

    public void setStockHistList(List<StockHist> stockHistList) {
        this.stockHistList = stockHistList;
    }
}
