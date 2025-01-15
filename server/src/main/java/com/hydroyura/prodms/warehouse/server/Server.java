package com.hydroyura.prodms.warehouse.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        var context = SpringApplication.run(Server.class, args);
    }

}