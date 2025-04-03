package com.EmailVerfication.EmaiService.Kafka;
import com.EmailVerfication.EmaiService.Dto.EmailReq;
import com.EmailVerfication.EmaiService.Service.EmailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
@EnableKafka
public class KafkaService {
    private static final Logger log = LoggerFactory.getLogger(KafkaService.class);
    private final EmailService emailService;
    public KafkaService(EmailService emailService) {
        this.emailService = emailService;
    }
    @KafkaListener(topics = "incomingTopic")
    public void listen(ConsumerRecord<String, String> record) {
        String[] data = record.value().split("S!--!B");
        EmailReq emailReq = new EmailReq(
                record.key(),
                data[0],
                data[1]
        );

        log.info("Received record: " + record.key());
        emailService.sendEmail(emailReq);
    }
}
