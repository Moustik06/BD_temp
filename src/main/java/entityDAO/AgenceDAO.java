package entityDAO;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

public class AgenceDAO extends BaseDAO {


    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.AGENCE.getName());
        collection.createIndex(new Document("nom", 1));
    }
    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.AGENCE.getName());

        return getDocuments(criteria, collection);
    }

}
