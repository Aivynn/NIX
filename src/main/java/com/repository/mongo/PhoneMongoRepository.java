package com.repository.mongo;

import com.config.MONGODB;
import com.google.gson.*;
import com.model.Phone;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.util.Singleton;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class PhoneMongoRepository implements CrudMongoRepository<Phone> {


    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsString() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)))
            .create();
    private static final MongoDatabase mongodb = MONGODB.getMongoDatabase();
    private final MongoCollection<Document> documents = mongodb.getCollection("Phone");

    @Override
    public void save(Phone product) {
        documents.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void update(String id) {
        Document document = documents.find(eq("id", id)).first();
        Bson updates = Updates.set("time",LocalDateTime.now());
        documents.updateOne(Document.parse(gson.toJson(document)),updates);
    }

    @Override
    public void delete(String id) {
        documents.deleteOne(Objects.requireNonNull(documents.find(eq("id", id)).first()));
    }

    @Override
    public Phone getById(String id) {
        Document document = documents.find(eq("id", id)).first();
        return gson.fromJson(document.toJson(), Phone.class);
    }
}
