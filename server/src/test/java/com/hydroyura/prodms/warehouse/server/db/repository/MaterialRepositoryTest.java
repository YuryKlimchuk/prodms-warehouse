package com.hydroyura.prodms.warehouse.server.db.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mongodb.client.MongoClients;
import java.util.List;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

class MaterialRepositoryTest {

    private final MaterialRepository materialRepository;

    @ClassRule
    public static MongoDBContainer TEST_MONGO_CONTAINER =
        new MongoDBContainer(DockerImageName.parse("mongo:7.0.16"));


    @BeforeAll
    static void startContainer() {
        TEST_MONGO_CONTAINER.setPortBindings(List.of("27020:27017"));
//        TEST_MONGO_CONTAINER.withCopyFileToContainer(
//            MountableFile.forClasspathResource("./test.js"),
//            "/docker-entrypoint-initdb.d/test.js"
//        );
        TEST_MONGO_CONTAINER.start();
    }

    MaterialRepositoryTest() {
        var mongoClient =
            MongoClients.create("mongodb://localhost:27020/?directConnection=true&serverSelectionTimeoutMS=2000");
        this.materialRepository = new MaterialRepository(mongoClient);
    }

    @Test
    void create_OK() {
        int a = 1;
        assertTrue(true);
        assertFalse(false);
    }

    @Test
    void create_ERROR_DUBLICATION() {
        assertFalse(false);
    }

    @Test
    void createIndex_OK() throws Exception {
        materialRepository.createIndexes();
        var result = TEST_MONGO_CONTAINER.execInContainer("mongosh", "--quiet", "--eval", "db.warehouse.getIndexes()");
    }


}