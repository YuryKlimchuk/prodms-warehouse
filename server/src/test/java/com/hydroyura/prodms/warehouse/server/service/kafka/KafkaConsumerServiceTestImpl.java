package com.hydroyura.prodms.warehouse.server.service.kafka;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
public class KafkaConsumerServiceTestImpl implements KafkaConsumerService {

    private ConsumerIndicator indicator;

    @Override
    public void handleReceiptEvent(MaterialReceipt event) {
        log.info("[handleReceiptEvent] was called");
        indicator.setIsHandleReceiptEvent(Boolean.TRUE);
    }

    @Override
    public void handleConsumptionEvent(MaterialConsumption event) {
        log.info("[handleConsumptionEvent] was called");
        indicator.setIsHandleConsumptionEvent(Boolean.TRUE);
    }

    @Override
    public void handleDefaultEvent(Object event) {
        log.info("[handleDefaultEvent] was called");
        indicator.setIsHandleDefaultEvent(Boolean.TRUE);
    }

    @Data
    static class ConsumerIndicator {

        private Boolean isHandleReceiptEvent = Boolean.FALSE;
        private Boolean isHandleConsumptionEvent = Boolean.FALSE;
        private Boolean isHandleDefaultEvent = Boolean.FALSE;

    }

}
