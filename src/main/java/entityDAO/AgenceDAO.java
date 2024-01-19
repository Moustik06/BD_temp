package entityDAO;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
    public ArrayList<Document> sortAgencesByNom() {
        // Définir le pipeline d'agrégation
        List<Bson> pipeline = List.of(
                Aggregates.sort(Sorts.ascending("nom"))
        );

        return getDocuments(pipeline, CollectionNames.AGENCE.getName());
    }

}
