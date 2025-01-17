package com.hydroyura.prodms.warehouse.server;

import com.hydroyura.prodms.warehouse.server.props.MongoProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(MongoProps.class)
public class Server {

    public static void main(String[] args) {
        var context = SpringApplication.run(Server.class, args);
    }

}