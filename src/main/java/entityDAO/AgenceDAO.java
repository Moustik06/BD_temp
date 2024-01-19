package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class AgenceDAO extends BaseDAO {


    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.AGENCE.getName());
        collection.createIndex(new Document("nom", 1));
    }
}
