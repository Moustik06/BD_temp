package entityDAO;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

public class ClientDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.CLIENT.getName());
        collection.createIndex(new Document("nom", 1));
        collection.createIndex(new Document("prenom", 1));
        collection.createIndex(new Document("email", 1));


    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.CLIENT.getName());
        return getDocuments(criteria, collection);
    }


}
