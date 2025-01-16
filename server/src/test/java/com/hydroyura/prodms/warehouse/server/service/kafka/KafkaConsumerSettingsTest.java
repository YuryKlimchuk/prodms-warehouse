package com.hydroyura.prodms.warehouse.server.service.kafka;

import static com.hydroyura.prodms.warehouse.server.service.kafka.KafkaTestUtils.KAFKA_TEST_IMAGE;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.hydroyura.prodms.warehouse.server.service.kafka.KafkaConsumerServiceTestImpl.ConsumerIndicator;
import org.testcontainers.containers.KafkaContainer;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class KafkaConsumerSettingsTest {

    @Autowired
    private KafkaConsumerServiceTestImpl kafkaConsumerService;

    @Autowired
    private ApplicationContext context;

    @ClassRule
    public static KafkaContainer KAFKA_TEST_CONTAINER = new KafkaContainer(KAFKA_TEST_IMAGE);

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

        KAFKA_TEST_CONTAINER.execInContainer(
            "/bin/bash", "-c", """
echo '__TypeId__:consumption    {"number": "test", "count": 55}' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092 --property parse.headers=true
        """
        );

        //docker exec 57213c12499a /bin/bash -c "echo '{"number": "test", "count": 55}' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092"

        await()
            .atLeast(Duration.ofMillis(500))
            .atMost(Duration.ofSeconds(500))
            .with()
            .pollInterval(Duration.ofMillis(500))
            .until(indicator::getIsHandleConsumptionEvent);
    }
}
