package ru.mikhailova.customerService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    private final String newClaimTopic;

    public KafkaTopicConfig(@Value("${kafka.topic.new-claim}") String newClaimTopic) {
        this.newClaimTopic = newClaimTopic;
    }

    @Bean
    public NewTopic newClaimTopic() {
        return TopicBuilder.name(newClaimTopic)
                .build();
    }
}
