package com.hydroyura.prodms.warehouse.server.config;

import com.hydroyura.prodms.warehouse.server.props.MongoProps;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MongoConfig {

    @Bean
    MongoDatabase mongoDatabase(MongoProps props) {
        log.info("Mongo props = {}", props);
        var mongoClient = MongoClients.create(props.getConnection().getConnectionString());
        return mongoClient.getDatabase(props.getConnection().getDb());
    }

}
