package com.hydroyura.prodms.warehouse.server.service.kafka;

import lombok.experimental.UtilityClass;
import org.testcontainers.utility.DockerImageName;

@UtilityClass
public class KafkaTestUtils {

    public static final DockerImageName KAFKA_TEST_IMAGE =
        DockerImageName.parse("confluentinc/cp-kafka:7.4.0");


    public static final String KAFKA_TEST_CONTAINER_NAME = "kafka-test-container-name";

    public static final String BASH_CMD_PRODUCE_MSG_CONSUMPTION = """
echo '__TypeId__:consumption\t%s' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092 --property parse.headers=true""";

    public static final String BASH_CMD_PRODUCE_MSG_RECEIPT = """
echo '__TypeId__:receipt\t%s' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092 --property parse.headers=true""";

    public static final String BASH_CMD_PRODUCE_MSG_WITHOUT_ID_HEADER = """
echo '%s' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092 --property""";

    public static final String BASH_CMD_PRODUCE_MSG_WITH_UNKNOWN_ID_HEADER = """
echo '__TypeId__:SOME_VALUE\t%s' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092 --property""";

}
