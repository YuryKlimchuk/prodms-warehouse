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
        private String connectionString;
        /**
         *  mode = 0 create connection string from peaces: user, pwd, db, host, port
         *  mode != 0 use prepared connection string
         */
        private Integer mode = 0;

        public String getConnectionString() {
            return (mode == 0)
                ? "mongodb://%s:%s@%s:%s/%s".formatted(user, pwd, host, port, db)
                : this.connectionString;
        }
    }
}