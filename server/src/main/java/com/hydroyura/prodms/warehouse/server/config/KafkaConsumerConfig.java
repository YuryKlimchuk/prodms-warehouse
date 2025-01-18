package com.hydroyura.prodms.warehouse.server.config;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
//@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.url:localhost:9092}")
    private String kafkaUrl;

//    @Bean
//    public RecordMessageConverter multiTypeConverter() {
//        StringJsonMessageConverter converter = new StringJsonMessageConverter();
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//        typeMapper.addTrustedPackages("com.hydroyura.prodms.warehouse.server.model.event");
//        Map<String, Class<?>> mappings = new HashMap<>();
//        mappings.put("consumption", MaterialConsumption.class);
//        mappings.put("receipt", MaterialReceipt.class);
//        typeMapper.setIdClassMapping(mappings);
//        converter.setTypeMapper(typeMapper);
//        return converter;
//    }

    @Bean
    public ConsumerFactory<String, Object> multiTypeConsumerFactory() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("com.hydroyura.prodms.warehouse.server.model.event");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("consumption", MaterialConsumption.class);
        mappings.put("receipt", MaterialReceipt.class);
        typeMapper.setIdClassMapping(mappings);
        jsonDeserializer.setTypeMapper(typeMapper);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
//        RecordMessageConverter converter,
        ConsumerFactory<String, Object> multiTypeConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(multiTypeConsumerFactory());
        //factory.setRecordMessageConverter(converter);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(500, 2)));
        //factory.setCommonErrorHandler(new KafkaErrorHandler());
        return factory;
    }
}