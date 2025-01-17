package com.hydroyura.prodms.warehouse.server.service.kafka;

import static com.hydroyura.prodms.warehouse.server.service.kafka.KafkaTestUtils.BASH_CMD_PRODUCE_MSG_CONSUMPTION;
import static com.hydroyura.prodms.warehouse.server.service.kafka.KafkaTestUtils.BASH_CMD_PRODUCE_MSG_RECEIPT;
import static com.hydroyura.prodms.warehouse.server.service.kafka.KafkaTestUtils.BASH_CMD_PRODUCE_MSG_WITH_UNKNOWN_ID_HEADER;
import static com.hydroyura.prodms.warehouse.server.service.kafka.KafkaTestUtils.KAFKA_TEST_CONTAINER_NAME;
import static com.hydroyura.prodms.warehouse.server.service.kafka.KafkaTestUtils.KAFKA_TEST_IMAGE;

import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import com.hydroyura.prodms.warehouse.server.service.kafka.KafkaConsumerServiceTestImpl.ConsumerIndicator;
import java.time.Duration;
import java.util.function.Supplier;
import org.awaitility.Awaitility;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


// TODO: implemented only successful cases
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class KafkaConsumerSettingsTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaConsumerServiceTestImpl kafkaConsumerService;

    @ClassRule
    public static Network KAFKA_TEST_NETWORK = Network.newNetwork();

    @ClassRule
    public static KafkaContainer KAFKA_TEST_CONTAINER =
        new KafkaContainer(KAFKA_TEST_IMAGE).withCreateContainerCmdModifier(
            cmd -> cmd.withName(KAFKA_TEST_CONTAINER_NAME)).withNetwork(KAFKA_TEST_NETWORK);

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("kafka.url", KAFKA_TEST_CONTAINER::getBootstrapServers);
    }

    @BeforeAll
    public static void init() {
        KAFKA_TEST_CONTAINER.start();
    }

    @AfterAll
    public static void clear() {
        KAFKA_TEST_CONTAINER.close();
    }

    @BeforeEach
    void resetKafkaConsumerState() throws Exception {
        Thread.sleep(Duration.ofSeconds(5).toMillis());
        kafkaConsumerService.setIndicator(null);
    }

    @Test
    void consumeConsumptionEvent_OK() throws Exception {
        ConsumerIndicator indicator = new ConsumerIndicator();
        kafkaConsumerService.setIndicator(indicator);

        var event = new MaterialConsumption();
        event.setNumber("TEST_MAT_NUMBER");
        event.setCount(15.8d);
        String json = objectMapper.writeValueAsString(event);

        KAFKA_TEST_CONTAINER.execInContainer("/bin/bash", "-c", BASH_CMD_PRODUCE_MSG_CONSUMPTION.formatted(json));

        await(indicator::isHandleConsumptionEvent);
    }

    @Test
    void consumeReceiptEvent_OK() throws Exception {
        ConsumerIndicator indicator = new ConsumerIndicator();
        kafkaConsumerService.setIndicator(indicator);

        var event = new MaterialReceipt();
        event.setNumber("TEST_MAT_NUMBER");
        event.setGroupNumber("GROUP_NUM");
        event.setName("NAME");
        event.setGroupName("GROUP_NAME");
        event.setStandard("STANDARD");
        event.setCount(15.8d);
        event.setSize("SIZE");
        event.setProfile(1);
        event.setMeasureUnit(1);
        event.setType(1);
        String json = objectMapper.writeValueAsString(event);

        KAFKA_TEST_CONTAINER.execInContainer("/bin/bash", "-c", BASH_CMD_PRODUCE_MSG_RECEIPT.formatted(json));

        await(indicator::isHandleReceiptEvent);
    }

    private void await(Supplier<Boolean> s) {
        Awaitility
            .await()
            .atLeast(Duration.ofMillis(100))
            .atMost(Duration.ofSeconds(15000))
            .with()
            .pollInterval(Duration.ofMillis(100))
            .until(s::get);
    }
}
