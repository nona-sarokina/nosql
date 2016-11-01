package jmp.nosql;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Nona_Sarokina on 10/17/2016.
 */
public class MongoEditHandler {
    protected static final String NOSQL_COLLECTION_NAME = "nosql";
    protected static final String MODULE_13_DB_NAME = "module13";
    protected static final String ID_FIELD_NAME = "_id";

    private MongoDatabase database;

    public MongoEditHandler() {
        MongoClient mongoClient = new MongoClient();
        database = mongoClient.getDatabase(MODULE_13_DB_NAME);
    }

    public MongoEditHandler(MongoDatabase database) {
        this.database = database;
    }

    private MongoDatabase getDatabase() {
        return database;
    }

    public Document createDocument(Map<String, Object> values) {
        Document document = new Document(values);
        getDatabase().getCollection(NOSQL_COLLECTION_NAME).insertOne(document);
        return document;
    }

    public ArrayList<Document> find(String name, String value) {
        return getDatabase().getCollection(NOSQL_COLLECTION_NAME).find(new Document(name, value)).into(new ArrayList<Document>());

    }

    public Document delete(String id) {
        return getDatabase().getCollection(NOSQL_COLLECTION_NAME).findOneAndDelete(new Document(ID_FIELD_NAME, new ObjectId(id)));
    }
}
