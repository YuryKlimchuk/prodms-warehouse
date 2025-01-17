package com.hydroyura.prodms.warehouse.server.db.repository;

import lombok.experimental.UtilityClass;
import org.testcontainers.utility.DockerImageName;

@UtilityClass
public class MongoTestUtils {

    public static final DockerImageName MONGO_IMAGE = DockerImageName.parse("mongo:7.0.16");

}
