package com.hydroyura.prodms.warehouse.server.service.kafka;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import com.hydroyura.prodms.warehouse.server.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final MaterialService materialService;

    @Override
    public void handleReceiptEvent(MaterialReceipt event) {
        materialService.createUpdate(event);
    }

    @Override
    public void handleConsumptionEvent(MaterialConsumption event) {
        materialService.patchCount(event);
    }

    @Override
    public void handleDefaultEvent(Object event) {
        int a = 1;
    }

}
