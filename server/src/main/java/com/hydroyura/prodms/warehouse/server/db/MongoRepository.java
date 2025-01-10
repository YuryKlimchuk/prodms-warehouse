package com.hydroyura.prodms.warehouse.server.db;

public interface MongoRepository<T> {

    String getCollectionName();
    Class<T> getType();
    void createIndexes();

}
