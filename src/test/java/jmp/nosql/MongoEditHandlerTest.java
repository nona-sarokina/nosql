package jmp.nosql;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Nona_Sarokina on 10/23/2016.
 */
@RunWith(JUnit4.class)
public class MongoEditHandlerTest {

    private static final String nameToFind = "name";
    private static final String valueToFind = "value";
    private static final String STUB_ID = "580d10c696c555198cb691b0";

    MongodForTestsFactory factory;
    MongoDatabase database;

    MongoEditHandler handler;

    @Before
    public void init() throws Exception {
        factory = MongodForTestsFactory.with(Version.Main.DEVELOPMENT);
        MongoClient mongo = factory.newMongo();
        database = mongo.getDatabase(MongoEditHandler.MODULE_13_DB_NAME);

        database.getCollection(MongoEditHandler.NOSQL_COLLECTION_NAME).insertMany(new ArrayList<Document>(
                Arrays.asList(new Document[]{
                        createTestDocument(),
                        createTestDocument(),
                        createTestDocument(),
                        createTestDocument(nameToFind, valueToFind)
                })));

        handler = new MongoEditHandler(database);


    }

    private Document createTestDocument() {
        return new Document(new HashMap<String, Object>() {{
            put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
            put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        }});
    }

    private Document createTestDocument(String name, String value) {
        Document testDocument = createTestDocument();
        testDocument.append(name, value);
        return testDocument;
    }

    @After
    public void tearDown() throws Exception {
        if (null != factory) {
            factory.shutdown();
        }
    }

    @Test
    public void testCreate_createDocumentWithData() throws Exception {
        final String name = UUID.randomUUID().toString();
        final String value = UUID.randomUUID().toString();
        HashMap<String, Object> values = new HashMap<String, Object>() {{
            put(name, value);
        }};
        handler.createDocument(values);
        assertThat(database.getCollection(MongoEditHandler.NOSQL_COLLECTION_NAME).find(new Document(values)).into(new ArrayList<Document>()), is(not(empty())));
    }

    @Test
    public void testCreate_createEmptyDocument() throws Exception {
        HashMap<String, Object> values = new HashMap<String, Object>();
        handler.createDocument(values);
        assertThat(database.getCollection(MongoEditHandler.NOSQL_COLLECTION_NAME).find(new Document(values)).into(new ArrayList<Document>()), is(not(empty())));
    }

    @Test
    public void testFind_noExistingElement() throws Exception {
        final String name = UUID.randomUUID().toString();
        final String value = UUID.randomUUID().toString();
        assertThat(handler.find(name, value), is(empty()));

    }

    @Test
    public void testFind_oneExistingElement() throws Exception {
        assertThat(handler.find(nameToFind, valueToFind), is(not(empty())));
    }

    @Test
    public void testFind_twoExistingElement() throws Exception {
        handler.createDocument(createTestDocument(nameToFind, valueToFind));
        assertThat(handler.find(nameToFind, valueToFind), hasSize(2));
    }

    @Test
    public void testDelete_deletingExistingElement() throws Exception {
        ObjectId id = (ObjectId) handler.find(nameToFind, valueToFind).get(0).get(MongoEditHandler.ID_FIELD_NAME);
        handler.delete(id.toHexString());
        assertThat(handler.find(nameToFind, valueToFind), is(empty()));
    }

    @Test
    public void testDelete_deletingNotExistingElement() throws Exception {
        assertNull(handler.delete(STUB_ID));
    }

}