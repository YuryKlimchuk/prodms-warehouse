package com.hydroyura.prodms.warehouse.server.db.repository;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.Jsr310CodecProvider;
import org.bson.codecs.pojo.PojoCodecProvider;

@RequiredArgsConstructor
public class MaterialRepository {

    private final MongoClient mongoClient;

    private CodecRegistry getCodecRegistry() {
        return CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
                .register(Material.class)
                .build(),
            new Jsr310CodecProvider(),
            new ValueCodecProvider()
        );
    }

    private MongoCollection<Material> getCollection() {
        return this.mongoClient
            .getDatabase("warehouse")
            .withCodecRegistry(getCodecRegistry())
            .getCollection("materials", Material.class);
    }

    public void createIndexes() {
        getCollection().createIndex(
            Indexes.ascending("number"),
            new IndexOptions().unique(true).name("NUMBER_UNIQUE")
        );
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
