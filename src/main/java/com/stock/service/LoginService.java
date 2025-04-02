package com.stock.service;

import com.stock.controller.request.LoginRequest;
import com.stock.controller.response.LoginResponse;
import com.stock.modal.Status;
import com.stock.modal.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public LoginResponse loginUser(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {

//            SimpleCrypto
//            CryptoJS.AES.decrypt("encrypted", "Secret Passphrase");
            loginResponse.setStatus(Status.SUCCESS);
            User user = new User();
            user.setUserName(loginRequest.getUsername());
            user.setId(1);
            loginResponse.setUser(user);
        } catch (Exception ex) {
            loginResponse.setStatus(Status.FAILED);
        }

        return loginResponse;
    }

}
