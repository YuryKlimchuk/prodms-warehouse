package com.hydroyura.prodms.warehouse.server.service;

import java.util.List;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

public class KafkaErrorHandler implements CommonErrorHandler {

    @Override
    public void handleRemaining(Exception e, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
                                MessageListenerContainer container) {
        //CommonErrorHandler.super.handleRemaining(thrownException, records, consumer, container);

        int a = 1;

    }

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer,
                                     MessageListenerContainer container, boolean batchListener) {
        int a = 1;
    }
}
