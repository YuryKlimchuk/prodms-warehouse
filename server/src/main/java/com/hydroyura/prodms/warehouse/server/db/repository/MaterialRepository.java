package com.hydroyura.prodms.warehouse.server.db.repository;

import static com.hydroyura.prodms.warehouse.server.SharedConstants.EX_MSG_MATERIAL_PATCH_COUNT;

import com.hydroyura.prodms.warehouse.server.db.MongoRepository;
import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.hydroyura.prodms.warehouse.server.model.exception.MaterialUpdateCountException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.Jsr310CodecProvider;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

@RequiredArgsConstructor
public class MaterialRepository implements MongoRepository<Material> {

    private final MongoDatabase mongoDatabase;
    private static final String COLLECTION_NAME = "materials";

    private CodecRegistry getCodecRegistry() {
        return CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
                .register(getType())
                .build(),
            new Jsr310CodecProvider(),
            new ValueCodecProvider()
        );
    }

    private MongoCollection<Material> getCollection() {
        return mongoDatabase
            .withCodecRegistry(getCodecRegistry())
            .getCollection(getCollectionName(), getType());
    }

    public void create(Material material) {
        getCollection().insertOne(material);
    }

    public Optional<Material> get(String number) {
        return Optional
            .ofNullable(getCollection().find(Filters.eq("number", number)).first());
    }

    public Collection<Material> getGroup(String groupNumber) {
        return null;
    }


    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Class<Material> getType() {
        return Material.class;
    }

    @Override
    public void createIndexes() {
        getCollection().createIndex(
            Indexes.ascending("number"),
            new IndexOptions().unique(true).name("NUMBER_UNIQUE")
        );
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
