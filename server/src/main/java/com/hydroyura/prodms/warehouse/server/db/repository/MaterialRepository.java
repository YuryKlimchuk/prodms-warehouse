package com.hydroyura.prodms.warehouse.server.db.repository;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

@RequiredArgsConstructor
public class MaterialRepository {

    private final MongoClient mongoClient;


    private MongoCollection<Material> getCollection() {
        return this.mongoClient.getDatabase("warehouse").getCollection("materials", Material.class);
    }

    public void create(Material material) {
        var result = getCollection().insertOne(material);
        int a = 1;
    }

    public Optional<Material> get(String number) {
        return Optional
            .ofNullable(getCollection().find(new Document("number", number)).first());
    }

    public Collection<Material> getGroup(String groupNumber) {
        return null;
    }


}
