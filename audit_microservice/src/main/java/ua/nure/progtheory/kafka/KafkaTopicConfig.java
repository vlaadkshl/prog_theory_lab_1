package ua.nure.progtheory.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicRead() {
        return new NewTopic("read_data", 1, (short) 1);
    }

    @Bean
    public NewTopic topicUpdate() {
        return new NewTopic("update_data", 1, (short) 1);
    }

    @Bean
    public NewTopic topicCreate() {
        return new NewTopic("create_data", 1, (short) 1);
    }

    @Bean
    public NewTopic topicDelete() {
        return new NewTopic("delete_data", 1, (short) 1);
    }
}
