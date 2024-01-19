package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.ArrayList;

public class ParkingDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.PARKING.getName());
        collection.createIndex(new Document("idAgence", 1));

    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.PARKING.getName());
        return getDocuments(criteria, collection);
    }


}
