package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Document> findLocationWithClientAndVehicleDetailsFromId(Integer id) {
        List<Bson> pipeline = List.of(
                Aggregates.match(new Document("_id", id)),
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "id_client",
                        "_id",
                        "clients"
                ),
                Aggregates.unwind("$clients"),
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.project(Projections.excludeId())
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    public ArrayList<Document> findLocationsForClientAndSortByStartDate(int clientId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.eq("id_client", clientId)),
                Aggregates.sort(Sorts.ascending("date_debut")),
                Aggregates.project(Projections.excludeId())
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    public ArrayList<Document> findLocationsWithVehicleDetailsAndSortByStartDate() {
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.sort(Sorts.descending("date_debut")),
                Aggregates.project(Projections.excludeId())
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    public ArrayList<Document> findLocationsByAgencyWithPriceAbove(int agencyId, double minPrice) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.eq("id_agence", agencyId)),
                Aggregates.match(Filters.gt("prix_ttc", minPrice)),
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.project(Projections.excludeId())
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }



    public ArrayList<Document> findLocationsByBrand(String brand){
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.marque", brand)),
                Aggregates.group("$vehicules.marque", Accumulators.sum("count", 1)),
                Aggregates.sort(Sorts.ascending("count")),
                Aggregates.project(Projections.include("vehicules.marque", "count")));

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    // Liste par odre décroisant des marques les plus louées

    public ArrayList<Document> findLocationsByBrandDesc(){
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "id_vehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.group("$vehicules.marque", Accumulators.sum("count", 1)),
                Aggregates.sort(Sorts.descending("count")),
                Aggregates.project(Projections.include("vehicules.marque", "count")));

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }
}
