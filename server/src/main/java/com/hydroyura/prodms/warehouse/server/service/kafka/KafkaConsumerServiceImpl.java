package com.hydroyura.prodms.warehouse.server.service.kafka;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    @Override
    public void handleReceiptEvent(MaterialReceipt event) {
        int a = 1;
    }

    @Override
    public void handleConsumptionEvent(MaterialConsumption event) {
        int a = 1;
    }

    @Override
    public void handleDefaultEvent(Object event) {
        int a = 1;
    }

}
