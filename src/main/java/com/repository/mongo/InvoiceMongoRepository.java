package com.repository.mongo;

import com.config.MONGODB;
import com.google.gson.*;
import com.model.mongoInvoice.InvoiceMongo;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.util.Singleton;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Filters.eq;


@Singleton
public class InvoiceMongoRepository {


    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsString() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)))
            .create();
    private static final MongoDatabase mongodb = MONGODB.getMongoDatabase();
    private final MongoCollection<Document> documents = mongodb.getCollection("Invoice");

    public void save(InvoiceMongo invoiceMongo){
        documents.insertOne(Document.parse(gson.toJson(invoiceMongo)));
    }

    public List<InvoiceMongo> findBySum(double sum){
        Bson filter = Filters.gte("sum", sum);
       return documents.find(filter).map(x -> gson.fromJson(x.toJson(), InvoiceMongo.class)).into(new ArrayList<>());
    }

    public InvoiceMongo findById(String id){
        Document document = documents.find(eq("_id", new ObjectId(id))).first();
        return gson.fromJson(document.toJson(), InvoiceMongo.class);
    }
    public void updateTime(String id){
        Document document = documents.find(eq("_id", new ObjectId(id))).first();
        Bson updates = Updates.set("time",LocalDateTime.now());
        documents.updateOne(Document.parse(gson.toJson(document)),updates);
    }

    public long countRows(){
       return documents.countDocuments();
    }

    public void groupBySum(){
        HashMap<Double, Integer> map = new HashMap<>();
        AggregateIterable<Document> groupBy = documents.aggregate(List.of(
                group("$sum", Accumulators.sum("count", 1)),
                sort(Sorts.descending("sum"))));
        for(Document document : groupBy){
            System.out.println(document);
        }
    }
}
