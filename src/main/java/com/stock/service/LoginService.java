package com.stock.service;

import com.stock.controller.request.LoginRequest;
import com.stock.controller.response.LoginResponse;


public interface LoginService {

    LoginResponse loginUser(LoginRequest loginRequest);

}
