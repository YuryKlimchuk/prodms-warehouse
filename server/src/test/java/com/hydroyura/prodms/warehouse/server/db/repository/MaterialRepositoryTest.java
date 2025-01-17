package com.hydroyura.prodms.warehouse.server.db.repository;

import static com.hydroyura.prodms.warehouse.server.db.repository.MongoTestUtils.MONGO_IMAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.hydroyura.prodms.warehouse.server.model.exception.MaterialUpdateCountException;
import com.mongodb.MongoWriteException;
import java.util.Map;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JavaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.type.TypeFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class MaterialRepositoryTest {

    @Autowired
    private MaterialRepository materialRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ClassRule
    public static MongoDBContainer MONGO_TEST_CONTAINER = new MongoDBContainer(MONGO_IMAGE);

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("test.mongo.db", () -> "warehouse");
        registry.add("test.mongo.collection", () -> "materials");
        registry.add("test.mongo.connection.string", MONGO_TEST_CONTAINER::getConnectionString);
    }

    @BeforeAll
    static void startContainer() throws Exception {
        // start container
        MONGO_TEST_CONTAINER.start();

        // init "materials" collection
        MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.insertOne({test: 'test'})",
            "--json=relaxed");
        MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.deleteMany({})",
            "--json=relaxed");

        // create indexes
        MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.createIndex({name: 1}, {name: 'number_unique', unique: true})",
            "--json=relaxed");
    }

    @AfterEach
    void clean() throws Exception {
        MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.deleteMany({})",
            "--json=relaxed");
    }

    @AfterAll
    static void stopContainer() throws Exception {
        MONGO_TEST_CONTAINER.stop();
        MONGO_TEST_CONTAINER.close();
    }

    @Test
    void create_OK() {
        var material = createMaterial("TEST_NUMBER");
        materialRepository.create(material);
        assertNotNull(material.getId());
    }

    @Test
    void create_ERROR_DUBLICATION() {
        var name = "TEST_NUMBER";
        materialRepository.create(createMaterial(name));

        assertFalse(materialRepository.create(createMaterial(name)).isPresent());
    }

    @Test
    void patchCountIncrease_OK() throws Exception {
        var number = "TEST_NUMBER";
        double initCount = 10.5d;
        MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.insertOne({number: '%s', count: Double(%s)})".formatted(number, initCount),
            "--json=relaxed");

        double delta = 7.2d;
        materialRepository.patchCount(number, delta);

        var result = MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.findOne({number: '%s'})".formatted(number),
            "--json=relaxed");

        JavaType type = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);

        Map<String, Object> convertedResult = objectMapper.readValue(result.getStdout(), type);
        double currentCount = Double.parseDouble(convertedResult.get("count").toString());

        assertEquals(initCount + delta, currentCount);
    }

    @Test
    void patchCountCutDownOn_OK() throws Exception {
        var number = "TEST_NUMBER";
        double initCount = 10.5d;
        MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.insertOne({number: '%s', count: Double(%s)})".formatted(number, initCount),
            "--json=relaxed");

        double delta = -7.2d;
        materialRepository.patchCount(number, delta);

        var result = MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.findOne({number: '%s'})".formatted(number),
            "--json=relaxed");

        JavaType type = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);

        Map<String, Object> convertedResult = objectMapper.readValue(result.getStdout(), type);
        double currentCount = Double.parseDouble(convertedResult.get("count").toString());

        assertEquals(initCount + delta, currentCount);
    }

    @Test
    void patchCount_NOT_EXIST() throws Exception {
        var number = "TEST_NUMBER";
        double delta = -7.2d;

        assertThrows(MaterialUpdateCountException.class, () -> materialRepository.patchCount(number, delta));
    }

    @Test
    void get_OK() throws Exception {
        var number = "TEST_NUMBER";
        var name = "test-name";
        var size = "10x10x5";
        var count = 15.7d;
        MONGO_TEST_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval",
                """
                    db.materials.insertOne(
                        {
                            number: '%s', groupNumber: 'groupNumber',
                            type: 1, profile: 1, measureUnit: 1,
                            count: %s,
                            name: '%s', groupName: 'test-group-name',
                            size: '%s', standard: 'ISO 6543'
                        }
                    )
                """.formatted(number, count, name, size),
            "--json=relaxed");

        var result = materialRepository.get(number);

        assertTrue(result.isPresent()
            && result.get().getNumber().equals(number)
            && result.get().getCount().equals(count)
            && result.get().getName().equals(name)
            && result.get().getSize().equals(size)
        );
    }

    @Test
    void get_NOT_FOUND() throws Exception {
        var number = "TEST_NUMBER";;
        var result = materialRepository.get(number);

        assertTrue(result.isEmpty());
    }

    private Material createMaterial(String number) {
        var material = new Material();

        material.setNumber(number);
        material.setGroupNumber("GROUP-" + number);
        material.setType(1);
        material.setName("NAME");
        material.setGroupName("GROUP_NAME");
        material.setSize("10x10");
        material.setProfile(1);
        material.setStandard("ISO 6543");
        material.setMeasureUnit(1);
        material.setCount(10d);

        return material;
    }

}