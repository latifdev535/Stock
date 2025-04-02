package com.stock.controller.response;

import com.stock.modal.Status;
import com.stock.modal.User;

public class LoginResponse {
    private Status status;
    private User user;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
