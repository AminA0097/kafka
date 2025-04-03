package com.EmailVerfication.EmaiService.Service;
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
    public void sendEmail(String[] content) {
        String toEmail = content[0];
        String subject = content[1];
        KafkaMsg kafkaMsg = new KafkaMsg();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            mailSender.send(mimeMessage);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        kafkaMsg.setKey(toEmail);
        kafkaMsg.setStatus(1);
        kafkaTemplate.send("responseTopic", kafkaMsg.getKey(),kafkaMsg.getStatus().toString());
        log.info("Email sent successfully");
        log.info("Message sent successfully");
    }
}
