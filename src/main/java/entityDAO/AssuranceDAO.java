package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class AssuranceDAO extends BaseDAO {


    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.ASSURANCE.getName());
        collection.createIndex(new Document("id_vehicule", 1));
        collection.createIndex(new Document("type", 1));
    }
}
