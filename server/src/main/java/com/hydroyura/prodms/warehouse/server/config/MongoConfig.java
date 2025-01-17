package com.hydroyura.prodms.warehouse.server.config;

import com.hydroyura.prodms.warehouse.server.db.repository.MaterialRepository;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {


    @Bean
    MongoDatabase mongoDatabase() {
        var mongoClient = MongoClients.create("mongodb://mongodb-user:mongodb-pwd@localhost:27017/");
        return mongoClient.getDatabase("warehouse");
    }

    @Bean
    MaterialRepository materialRepository(MongoDatabase mongoDatabase) {
        var materialRepo =  new MaterialRepository(mongoDatabase);
        materialRepo.createIndexes();
        return materialRepo;
    }

}
