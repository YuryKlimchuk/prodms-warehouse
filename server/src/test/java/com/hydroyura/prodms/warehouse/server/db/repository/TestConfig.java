package com.hydroyura.prodms.warehouse.server.db.repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    MongoDatabase mongoDatabase(@Value("${test.mongo.connection.string}") String connectionString,
                                @Value("${test.mongo.db}") String db) {
        var mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase(db);
    }

    @Bean
    public MaterialRepository materialRepository(MongoDatabase database,
                                                 @Value("${test.mongo.collection}") String collection) {
        return new MaterialRepository(database, collection);
    }

}
