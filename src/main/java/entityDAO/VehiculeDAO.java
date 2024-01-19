package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class VehiculeDAO extends BaseDAO {
    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.VEHICULE.getName());
        collection.createIndex(new Document("marque", 1));
        collection.createIndex(new Document("modele", 1));
        collection.createIndex(new Document("plaque_imat", 1));


    }



}
