package com.hydroyura.prodms.warehouse.server.service.kafka;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import lombok.Data;

@Data
public class KafkaConsumerServiceTestImpl implements KafkaConsumerService {

    private ConsumerIndicator indicator;

    @Override
    public void handleReceiptEvent(MaterialReceipt event) {
        indicator.setHandleReceiptEvent(true);
    }

    @Override
    public void handleConsumptionEvent(MaterialConsumption event) {
        indicator.setHandleConsumptionEvent(true);
    }

    @Override
    public void handleDefaultEvent(Object event) {
        indicator.setHandleDefaultEvent(true);
    }


    static class ConsumerIndicator {

        private boolean isHandleReceiptEvent = false;
        private boolean isHandleConsumptionEvent = false;
        private boolean isHandleDefaultEvent = false;

        public boolean isHandleReceiptEvent() {
            return isHandleReceiptEvent;
        }

        public void setHandleReceiptEvent(boolean handleReceiptEvent) {
            isHandleReceiptEvent = handleReceiptEvent;
        }

        public boolean isHandleConsumptionEvent() {
            return isHandleConsumptionEvent;
        }

        public void setHandleConsumptionEvent(boolean handleConsumptionEvent) {
            isHandleConsumptionEvent = handleConsumptionEvent;
        }

        public boolean isHandleDefaultEvent() {
            return isHandleDefaultEvent;
        }

        public void setHandleDefaultEvent(boolean handleDefaultEvent) {
            isHandleDefaultEvent = handleDefaultEvent;
        }
    }

}
