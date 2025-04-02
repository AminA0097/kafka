package com.UserService.User.Configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    private static final String Unverified_Topic = "Unverified";
    private static final String verified_Topic = "verified";
    private static final String READ_TOPIC = "read";
    private static final long delTime = 1000 * 60 * 5;
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
