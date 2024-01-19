package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class EmployerDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.EMPLOYE.getName());
        collection.createIndex(new Document("id_agence", 1));
        collection.createIndex(new Document("emploi", 1));

    }
}
