package com.hydroyura.prodms.warehouse.server.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClients;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JavaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.type.TypeFactory;
import org.testcontainers.utility.DockerImageName;

class MaterialRepositoryTest {

    private final MaterialRepository materialRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ClassRule
    public static MongoDBContainer TEST_MONGO_CONTAINER =
        new MongoDBContainer(DockerImageName.parse("mongo:7.0.16"));


    @BeforeAll
    static void startContainer() throws Exception {
        // start container
        TEST_MONGO_CONTAINER.setPortBindings(List.of("27020:27017"));
        TEST_MONGO_CONTAINER.start();

        // init "materials" collection
        TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.insertOne({test: 'test'})",
            "--json=relaxed");
        TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.deleteMany({})",
            "--json=relaxed");
    }

    @AfterEach
    void clean() throws Exception {
        TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.deleteMany({})",
            "--json=relaxed");
    }

    MaterialRepositoryTest() {
        var mongoClient = MongoClients.create("mongodb://localhost:27020/warehouse?directConnection=true&serverSelectionTimeoutMS=2000");
        var mongoDatabase = mongoClient.getDatabase("warehouse");
        this.materialRepository = new MaterialRepository(mongoDatabase);
    }

    @Test
    void createIndex_OK() throws Exception {
        materialRepository.createIndexes();
        var result = TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.getIndexes()",
            "--json=relaxed"
        );

        JavaType type = TypeFactory
            .defaultInstance()
            .constructCollectionType(
                ArrayList.class,
                TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)
            );

        List<Map<String, Object>> convertedResult = objectMapper.readValue(result.getStdout(), type);
        List<String> indexes = convertedResult
            .stream()
            .map(m -> m.get("name").toString())
            .toList();

        assertTrue(
            indexes.size() == 2
            && indexes.contains("NUMBER_UNIQUE")
        );
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

        var ex = assertThrows(MongoWriteException.class, () -> materialRepository.create(createMaterial(name)));
        assertEquals(11000, ex.getError().getCode());
    }

    @Test
    void patchCountIncrease_OK() throws Exception {
        var number = "TEST_NUMBER";
        double initCount = 10.5d;
        TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.insertOne({number: '%s', count: Double(%s)})".formatted(number, initCount),
            "--json=relaxed");

        double delta = 7.2d;
        materialRepository.patchCount(number, delta);

        var result = TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
            "--eval", "use warehouse",
            "--eval", "db.materials.findOne({number: '%s'})".formatted(number),
            "--json=relaxed");

        JavaType type = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);

        Map<String, Object> convertedResult = objectMapper.readValue(result.getStdout(), type);
        double currentCount = Double.parseDouble(convertedResult.get("count").toString());

        assertEquals(initCount + delta, currentCount);
    }



    /*
    //TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
//    "--eval", "use warehouse",
//    "--eval", "db.materials.insertOne({test: 'test'})");

//TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
//    "--eval", "use warehouse",
//    "--eval", "db.materials.deleteMany({test: 'test'})");

//TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
//    "--eval", "use warehouse",
//    "--eval", "db.getCollectionNames()");

//TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
//    "--eval", "db.warehouse.createIndex({'field_super': 1})");

TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
    "--eval", "db.warehouse.getIndexes()", "--json");
     */


//TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet",
//    "--eval", "use warehouse",
//    "--eval", "db.materials.insertOne({test: 'test'})",
//    "--json");


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