package entityDAO;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.CLIENT.getName());
        collection.createIndex(new Document("nom", 1));
        collection.createIndex(new Document("prenom", 1));
        collection.createIndex(new Document("email", 1));

    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.CLIENT.getName());
        return getDocuments(criteria, collection);
    }

    public ArrayList<Document> getLocationsForClient(int clientId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.eq("id_client", clientId))
        );
        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    public ArrayList<Document> getClientsForLocation(int locationId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.eq("_id", locationId)),
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "id_client",
                        "_id",
                        "clients"
                ),
                Aggregates.unwind("$clients"),
                Aggregates.project(Projections.excludeId())
        );
        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    public Document getClientWithMaxLocations() {
        List<Bson> pipeline = List.of(
                Aggregates.group("$id_client", Accumulators.sum("totalLocations", 1)),
                Aggregates.sort(Sorts.descending("totalLocations")),
                Aggregates.limit(1),
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "_id",
                        "_id",
                        "client"
                ),
                Aggregates.unwind("$client"),
                Aggregates.project(Projections.include("client.nom", "client.prenom", "totalLocations"))
        );
        ArrayList<Document> result = getDocuments(pipeline, CollectionNames.LOCATION.getName());
        return result.isEmpty() ? null : result.get(0);
    }

    public Document getMostRentedVehicleForClient(int clientId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.eq("id_client", clientId)),
                Aggregates.group("$id_vehicule", Accumulators.sum("count", 1)),
                Aggregates.sort(Sorts.descending("count")),
                Aggregates.limit(1),
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "_id",
                        "_id",
                        "vehicule"
                ),
                Aggregates.unwind("$vehicule"),
                Aggregates.replaceRoot("$vehicule"),
                Aggregates.project(Projections.include("marque", "modele", "immatriculation", "count"))
        );
        ArrayList<Document> result = getDocuments(pipeline, CollectionNames.LOCATION.getName());
        return result.isEmpty() ? null : result.get(0);
    }

    public ArrayList<Document> findClientsQuiDepensentLePlus() {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Effectuer une jointure avec la collection "Facture" sur le champ "idLocation"
                Aggregates.lookup(
                        CollectionNames.FACTURE.getName(),
                        "_id",
                        "idLocation",
                        "factures"
                ),
                // Étape 2 : Aplatir le tableau "factures" résultant de la jointure
                Aggregates.unwind("$factures"),
                // Étape 3 : Effectuer une jointure avec la collection "Client" sur le champ "_id"
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "_id",
                        "_id",
                        "clientDetails"
                ),
                // Étape 4 : Aplatir le tableau "clientDetails" résultant de la jointure
                Aggregates.unwind("$clientDetails"),
                // Étape 5 : Grouper les documents par id_client et calculer la somme des montants TTC
                Aggregates.group("$clientDetails._id", Accumulators.sum("montantTotal", "$factures.totalTTC")),
                // Étape 6 : Effectuer une jointure avec la collection "Client" sur le champ "_id"
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "_id",
                        "_id",
                        "clientDetails"
                ),
                // Étape 7 : Aplatir le tableau "clientDetails" résultant de la jointure
                Aggregates.unwind("$clientDetails"),
                // Étape 8 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include("clientDetails.nom", "clientDetails.prenom", "clientDetails.telephone", "montantTotal")
                        )
                ),
                // Étape 9 : Trier les documents par montantTotal par ordre croissant
                Aggregates.sort(Sorts.descending("montantTotal"))
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }
}

