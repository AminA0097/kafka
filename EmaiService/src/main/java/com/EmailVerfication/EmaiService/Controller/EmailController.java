package com.EmailVerfication.EmaiService.Controller;

import com.EmailVerfication.EmaiService.Service.EmailService;
import com.EmailVerfication.EmaiService.Service.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/email")
public class EmailController {
    private static final Logger log = LoggerFactory.getLogger(EmailController.class);
    private final EmailService emailServiceImpl;
    public EmailController(EmailServiceImpl emailServiceImpl) {
        this.emailServiceImpl = emailServiceImpl;
    }
    @GetMapping("/confirm")
    public String sendTempEmail() {
        String email = "aminfot97@gmail.com";
        return emailServiceImpl.sendEmail(email);
    }
    @PostMapping("/verify")
    public boolean verify(@RequestParam String code) {
        String email = "aminfot97@gmail.com";
        return emailServiceImpl.verify(email, code);
    }
}
