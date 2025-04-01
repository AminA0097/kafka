package com.EmailVerfication.EmaiService.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.EmailVerfication.EmaiService.Config.DigitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JwtService jwtService;
    private final DigitCode digitCode;
    private JavaMailSender mailSender;
    public EmailServiceImpl(
            JwtService jwtService,
            DigitCode digitCode) {
        this.jwtService = jwtService;
        this.digitCode = digitCode;
    }
    @Override
    public String sendEmail(String email) {
        String content = digitCode.getCode(email);
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + content + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to our app!");
        message.setText(htmlMessage);
        mailSender.send(message);
        return htmlMessage;
    }
    @Override
    public boolean verify(String email,String code){
        return digitCode.verify(email,code);
    }

}
