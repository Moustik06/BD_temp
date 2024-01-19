package entityDAO;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

public class AssuranceDAO extends BaseDAO {


    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.ASSURANCE.getName());
        collection.createIndex(new Document("id_vehicule", 1));
        collection.createIndex(new Document("type", 1));
    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.ASSURANCE.getName());
        return getDocuments(criteria, collection);
    }
}
