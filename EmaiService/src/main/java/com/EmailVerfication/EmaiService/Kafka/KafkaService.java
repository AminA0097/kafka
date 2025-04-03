package com.EmailVerfication.EmaiService.Kafka;


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

    @KafkaListener(topics = "Unverified")
    public void listen(ConsumerRecord<String, String> record) {
        String[] split = record.value().split(",");
        String email = split[0];
        email = "aminfot97@gmail.com";
        emailService.sendEmail(email);
    }
}
