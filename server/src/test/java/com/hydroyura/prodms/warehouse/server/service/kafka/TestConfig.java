package com.hydroyura.prodms.warehouse.server.service.kafka;

import com.hydroyura.prodms.warehouse.server.config.KafkaConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({KafkaConsumerConfig.class, KafkaAutoConfiguration.class})
public class TestConfig {


    @Bean
    KafkaConsumerService kafkaConsumerService() {
        return new KafkaConsumerServiceTestImpl();
    }
}
