package com.hydroyura.prodms.warehouse.server.service.kafka;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;

@KafkaListener(id = "warehouse", topics = "${kafka.topic:materials}")
public interface KafkaConsumerService {
    @KafkaHandler
    void handleReceiptEvent(MaterialReceipt event);

    @KafkaHandler
    void handleConsumptionEvent(MaterialConsumption event);

    @KafkaHandler(isDefault = true)
    void handleDefaultEvent(Object event);
}
