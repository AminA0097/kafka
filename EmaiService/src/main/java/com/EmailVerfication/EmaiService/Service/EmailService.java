package com.EmailVerfication.EmaiService.Service;

import org.springframework.http.ResponseEntity;

public interface EmailService {
    String sendEmail(String email);
    boolean verify(String email,String code);
    void readMsg();
}
