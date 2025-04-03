package com.EmailVerfication.EmaiService.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.EmailVerfication.EmaiService.Config.DigitCode;
import com.EmailVerfication.EmaiService.Dto.KafkaMsg;
import com.EmailVerfication.EmaiService.Kafka.KafkaService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JwtService jwtService;
    private final JavaMailSender mailSender;
    private final DigitCode digitCode;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public EmailServiceImpl(
            JwtService jwtService,
            DigitCode digitCode,
            JavaMailSender mailSender,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.jwtService = jwtService;
        this.digitCode = digitCode;
        this.mailSender = mailSender;
        this.kafkaTemplate = kafkaTemplate;
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
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Confirm Your Account");
            mimeMessageHelper.setText(htmlMessage,true);
            mailSender.send(mimeMessage);
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        return "Done!";
    }
    @Override
    public boolean verify(String email,String code){
        if(digitCode.verify(email,code)){
            KafkaMsg kafkaMsg = new KafkaMsg();
            kafkaMsg.setEmail(email);
            kafkaMsg.setStatus(1);
            kafkaMsg.setRead(0);
            kafkaMsg.setTopic("verified");
            String msg = String.format("%s,%d,%d",
                    kafkaMsg.getEmail(),
                    kafkaMsg.getStatus(),
                    kafkaMsg.getRead());
            kafkaTemplate.send(kafkaMsg.getTopic(),email,msg);
        };
        return false;
    }
    @Override
    public void readMsg() {

    }
}
