package com.EmailVerfication.EmaiService.Kafka;


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

    @KafkaListener(topics = "Unverified")
    public void listen(ConsumerRecord<String, String> record) {
        String[] split = record.value().split(",");
        log.info(split[0]);
        log.info(split[1]);
        log.info(split[2]);

    }
}
