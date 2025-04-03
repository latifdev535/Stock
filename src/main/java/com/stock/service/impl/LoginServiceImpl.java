package com.stock.service.impl;

import com.stock.controller.request.LoginRequest;
import com.stock.controller.response.LoginResponse;
import com.stock.dao.UserDao;
import com.stock.modal.Status;
import com.stock.modal.User;
import com.stock.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    public LoginResponse loginUser(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            User user = userDao.getUser(loginRequest.getUsername(),loginRequest.getPassword());
            loginResponse.setStatus(Objects.nonNull(user)?Status.SUCCESS:Status.FAILED);
            loginResponse.setUser(user);
        } catch (Exception ex) {
            loginResponse.setStatus(Status.FAILED);
        }
        return loginResponse;
    }
}
