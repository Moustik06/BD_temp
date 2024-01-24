package entityDAO;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AssuranceDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.ASSURANCE.getName());
        collection.createIndex(new Document("id_vehicule", 1));
        collection.createIndex(new Document("type", 1));
    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.ASSURANCE.getName());
        return getDocuments(criteria, collection);
    }


    public ArrayList<Document> findAssurancesByVehicle(String marque) {
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.marque", marque)),
                Aggregates.project(Projections.fields(Projections.exclude("vehicules")))
        );

        return getDocuments(pipeline, CollectionNames.ASSURANCE.getName());
    }

    public ArrayList<Document> findAssurancesByVehicleAndType(String marque, String type) {
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.and(Filters.eq("vehicules.marque", marque), Filters.eq("type", type))),
                Aggregates.project(Projections.fields(Projections.exclude("vehicules")))
        );

        return getDocuments(pipeline, CollectionNames.ASSURANCE.getName());
    }


    public ArrayList<Document> findAssuranceByVehicleImmatriculation(String immat){
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.plaque_imat", immat)),
                Aggregates.project(Projections.fields(Projections.exclude("vehicules")))
        );

        return getDocuments(pipeline, CollectionNames.ASSURANCE.getName());
    }

    public ArrayList<Document> findAssurancesFromVehicleModele(String modele){
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.modele", modele)),
                Aggregates.project(Projections.fields(Projections.exclude("vehicules")))
        );

        return getDocuments(pipeline, CollectionNames.ASSURANCE.getName());
    }

    public ArrayList<Document> findAssurancesFromVehicleMarque(String marque){
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.marque", marque)),
                Aggregates.project(Projections.fields(Projections.exclude("vehicules")))
        );

        return getDocuments(pipeline, CollectionNames.ASSURANCE.getName());
    }
}
