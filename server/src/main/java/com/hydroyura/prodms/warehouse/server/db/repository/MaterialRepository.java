package com.hydroyura.prodms.warehouse.server.db.repository;

import static com.hydroyura.prodms.warehouse.server.SharedConstants.EX_MSG_MATERIAL_PATCH_COUNT;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.hydroyura.prodms.warehouse.server.model.exception.MaterialUpdateCountException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.Collection;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.Jsr310CodecProvider;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MaterialRepository {

    private final MongoDatabase mongoDatabase;
    private final String collection;
    private final Class<Material> type = Material.class;


    public MaterialRepository(MongoDatabase mongoDatabase,
                              @Value("${mongo.collection.materials.name}") String collection) {
        this.mongoDatabase = mongoDatabase;
        this.collection = collection;
    }


    private CodecRegistry getCodecRegistry() {
        return CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
                .register(type)
                .build(),
            new Jsr310CodecProvider(),
            new ValueCodecProvider()
        );
    }

    private MongoCollection<Material> getCollection() {
        return mongoDatabase
            .withCodecRegistry(getCodecRegistry())
            .getCollection(collection, type);
    }

    public Optional<String> create(Material material) {
        try {
            var result = getCollection().insertOne(material);
            if (result.wasAcknowledged() && result.getInsertedId() != null) {
                return Optional.of(result.getInsertedId().asObjectId().toString());
            }
            log.warn("Can't insert new material with number = [{}] => unknown problem", material.getNumber());
        } catch (MongoWriteException ex) {
            log.warn("Can't insert new material with number = [{}], errMsg = [{}], errorCode = [[{}]",
                material.getNumber(),
                ex.getMessage(),
                ex.getError()
            );
        }
        return Optional.empty();
    }

    public Optional<Material> get(String number) {
        return Optional
            .ofNullable(getCollection().find(Filters.eq("number", number)).first());
    }

    public void patchCount(String number, Double delta) {
        Bson filter = Filters.eq("number", number);
        Bson updates = Updates.inc("count", delta);
        UpdateOptions options = new UpdateOptions().upsert(false);

        UpdateResult result = getCollection().updateOne(filter, updates, options);

        if (result.getMatchedCount() == 0 && result.getModifiedCount() == 0) {
            throw new MaterialUpdateCountException(EX_MSG_MATERIAL_PATCH_COUNT);
        }
    }

}
