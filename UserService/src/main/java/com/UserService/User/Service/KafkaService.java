package com.UserService.User.Service;
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
    private final PersonService personService;

    public KafkaService(PersonService personService) {
        this.personService = personService;
    }

    @KafkaListener(topics = "responseTopic",groupId = "verified-group")
    public void listen(ConsumerRecord<String, String> record) {
        String[] split = record.value().split(",");
        String email = split[0];
        Integer status = Integer.valueOf(split[1]);
        if (status == 1) {
            email = "aminfot97@gmail.com";
            personService.makeEnable(email);
        }
        else{
            log.error("This Email Not Verified!");
        }
    }
}
