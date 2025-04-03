package com.EmailVerfication.EmaiService.Service;
import com.EmailVerfication.EmaiService.Dto.EmailReq;
import com.EmailVerfication.EmaiService.Dto.EmailStatus;
import com.EmailVerfication.EmaiService.Dto.KafkaMsg;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public EmailServiceImpl(
            JavaMailSender mailSender,
            KafkaTemplate<String, String> kafkaTemplate) {
        this.mailSender = mailSender;
        this.kafkaTemplate = kafkaTemplate;
    }
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
    public void sendEmail(EmailReq emailReq) {
        String toEmail = emailReq.getTo();
        String subject = emailReq.getSubject();
        String body = emailReq.getBody();
        KafkaMsg kafkaMsg = new KafkaMsg();
        kafkaMsg.setKey(toEmail);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mailSender.send(mimeMessage);
            kafkaMsg.setStatus(EmailStatus.SUCCESS.getValue());
        }catch (Exception e){
            log.error("Failed to send email to %s", toEmail);
            kafkaMsg.setStatus(EmailStatus.FAILURE.getValue());
        }
        kafkaTemplate.send("responseTopic", kafkaMsg.getKey(),kafkaMsg.getStatus().toString());
        log.info("Email sent successfully");
        log.info("Message sent successfully");
    }
}
