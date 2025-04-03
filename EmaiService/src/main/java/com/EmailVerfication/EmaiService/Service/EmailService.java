package com.EmailVerfication.EmaiService.Service;

import com.EmailVerfication.EmaiService.Dto.EmailReq;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    void sendEmail(EmailReq emailReq);
}
