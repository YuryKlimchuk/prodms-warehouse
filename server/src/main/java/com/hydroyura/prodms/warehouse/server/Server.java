package com.hydroyura.prodms.warehouse.server;

import com.hydroyura.prodms.warehouse.server.props.MongoProps;
import com.hydroyura.prodms.warehouse.server.props.ValidationGetAllMaterialParamsProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({MongoProps.class, ValidationGetAllMaterialParamsProps.class})
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class Server {

    public static void main(String[] args) {
        var context = SpringApplication.run(Server.class, args);
    }

}