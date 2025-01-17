package com.hydroyura.prodms.warehouse.server.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mongo")
public class MongoProps {

    private Connection connection;

    @Data
    public static class Connection {
        private String user;
        private String pwd;
        private String db;
        private String host;
        private Integer port;

        public String getConnectionString() {
            return "mongodb://%s:%s@%s:%s/".formatted(user, pwd, host, port);
        }
    }
}