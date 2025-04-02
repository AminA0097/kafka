package com.EmailVerfication.EmaiService.Kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class KafkaService {
    @KafkaListener(topics = "test-group",groupId = "test-group")
    public void listen(String msg) {
        System.out.println(msg);
    }
}
