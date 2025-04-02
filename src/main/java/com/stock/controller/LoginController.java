package com.stock.controller;

import com.stock.controller.request.LoginRequest;
import com.stock.controller.response.LoginResponse;
import com.stock.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        return loginService.loginUser(loginRequest);
    }

}
