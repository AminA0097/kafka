package com.KafkaService.KafkaService.Config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@EnableKafka
@Configuration
public class KafkaConfig {

    public  String incomingTopic = "incomingTopic";
    public  String responseTopic = "responseTopic";
    public  String readTopic = "read";
    public  long delTime = 1000 * 60 * 5;
    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public AdminClient adminClient(KafkaAdmin kafkaAdmin) {
        return KafkaAdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Bean
    public NewTopic UnverifiedTopic() throws ExecutionException, InterruptedException {
        if (!checkTopic(incomingTopic)) {
            log.info("Creating Unverified Topic");
            return TopicBuilder.name(incomingTopic)
                    .partitions(3)
                    .replicas(1)
                    .build();
        }
        log.error("Read Unverified Exists!");
        return null;
    }

    @Bean
    public NewTopic verifiedTopic()throws ExecutionException, InterruptedException {
        if(!checkTopic(responseTopic)) {
            log.info("Creating Verified Topic");
            return TopicBuilder.name(responseTopic)
                    .partitions(3)
                    .replicas(1)
                    .build();
        }
        log.error("Read Verified Exists!");
        return null;
    }

    @Bean
    public NewTopic processedUsersTopic() throws ExecutionException, InterruptedException {
        if (!checkTopic(readTopic)) {
            log.info("Creating Read Topic");
            return TopicBuilder.name(readTopic)
                    .partitions(3)
                    .replicas(1)
                    .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(delTime))
                    .build();
        }
        log.error("Read Topic Exists!");
        return null;
    }
    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(Collections.singletonMap("bootstrap.servers", "localhost:9092"));
    }
    private boolean checkTopic(String topicName) throws ExecutionException, InterruptedException {
        try {
            ListTopicsResult listTopicsResult = adminClient(kafkaAdmin()).listTopics();
            return listTopicsResult.names().get().contains(topicName);
        }catch (Exception e) {
            return false;
        }
    }
}
