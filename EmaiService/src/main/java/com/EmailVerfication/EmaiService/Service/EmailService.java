package com.EmailVerfication.EmaiService.Service;

import org.springframework.http.ResponseEntity;

public interface EmailService {
    void sendEmail(String[] content);
}
