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
public class MongoEditHandler
{
    protected static final String NOSQL = "nosql";
    protected static final String MODULE_13 = "module13";
    protected static final String ID = "_id";

    MongoDatabase database;

    public MongoEditHandler() {
        MongoClient mongoClient = new MongoClient();
        database = mongoClient.getDatabase(MODULE_13);
    }

    private MongoDatabase getDatabase() {
        return database;
    }


    public void creteDocument(Map<String, Object> values) {
        getDatabase().getCollection(NOSQL).insertOne(
            new Document(values));
    }

    public ArrayList<Document> find(String name, String value) {
        return getDatabase().getCollection(NOSQL).find(new Document(name, value)).into(new ArrayList<Document>());

    }

    public void delete(String id) {
        System.out
            .println(getDatabase().getCollection(NOSQL).findOneAndDelete(new Document(ID, new ObjectId(id))) + "has been deleted");
    }
}
