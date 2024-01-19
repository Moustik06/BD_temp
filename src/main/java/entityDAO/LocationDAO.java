package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.ArrayList;

public class LocationDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.FACTURE.getName());
        collection.createIndex(new Document("id_client", 1));
        collection.createIndex(new Document("id_vehicule", 1));

    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.LOCATION.getName());
        return getDocuments(criteria, collection);
    }


}
