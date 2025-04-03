package com.stock.controller.response;

import com.stock.modal.Status;

public class StockTradeResponse {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
