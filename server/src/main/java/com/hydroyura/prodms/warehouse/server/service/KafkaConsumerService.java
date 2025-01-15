package com.hydroyura.prodms.warehouse.server.service;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "warehouse", topics = "materials")
public class KafkaConsumerService {

    @KafkaHandler
    public void handleEvent11(MaterialReceipt event) {
        int a = 1;
    }

    @KafkaHandler
    public void handleEvent22(MaterialConsumption event) {
        int a = 1;
    }

    @KafkaHandler(isDefault = true)
    public void handleEvent33(Object event) {
        int a = 1;
    }

}
