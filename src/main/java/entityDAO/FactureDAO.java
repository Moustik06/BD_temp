package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class FactureDAO extends BaseDAO {
    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.FACTURE.getName());
        collection.createIndex(new Document("idLocation", 1));
    }


}
