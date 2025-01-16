package com.hydroyura.prodms.warehouse.server.service.kafka;

import lombok.experimental.UtilityClass;
import org.testcontainers.utility.DockerImageName;

@UtilityClass
public class KafkaTestUtils {

    public static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:6.2.1");
}
