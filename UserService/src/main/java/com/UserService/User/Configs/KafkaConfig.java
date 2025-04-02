package com.UserService.User.Configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public  String Unverified_Topic = "Unverified";
    public  String verified_Topic = "verified";
    public  String READ_TOPIC = "read";
    public  long delTime = 1000 * 60 * 5;
    @Bean
    public NewTopic UnverifiedTopic() {
        return TopicBuilder.name(Unverified_Topic)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic verifiedTopic() {
        return TopicBuilder.name(verified_Topic)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic processedUsersTopic() {
        return TopicBuilder.name(READ_TOPIC)
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(delTime))
                .build();
    }

}
