package com.hydroyura.prodms.warehouse.server.config;

import com.hydroyura.prodms.warehouse.server.props.MongoProps;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    MongoDatabase mongoDatabase(MongoProps props) {
        var mongoClient = MongoClients.create(props.getConnection().getConnectionString());
        return mongoClient.getDatabase(props.getConnection().getDb());
    }

}
