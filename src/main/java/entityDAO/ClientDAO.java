package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class ClientDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.CLIENT.getName());
        collection.createIndex(new Document("nom", 1));
        collection.createIndex(new Document("prenom", 1));
        collection.createIndex(new Document("email", 1));


    }
}
