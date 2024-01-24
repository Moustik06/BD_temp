package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class ParkingDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.PARKING.getName());
        collection.createIndex(new Document("idAgence", 1));

    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.PARKING.getName());
        return getDocuments(criteria, collection);
    }

    public ArrayList<Document> getParkingsWithAvailablePlaces() {
        // Définir le pipeline d'agrégation
        // Étape 1 : Filtrer les documents pour ne conserver que ceux avec des places disponibles
        // Étape 2 : Projeter les champs nécessaires du document résultant
        List<Bson> pipeline = List.of(
                // Étape 1 : Filtrer les documents pour ne conserver que ceux avec des places disponibles
                Aggregates.match(Filters.gt("nbDispo", 0)),

                // Étape 2 : Projeter les champs nécessaires du document résultant
                Aggregates.project(Projections.include("idAgence", "nbDispo")),

                // Étape 3 : Effectuer une jointure avec la collection "Agence" sur le champ "idAgence"
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "idAgence",
                        "_id",
                        "agences"
                ),

                // Étape 4 : Aplatir le tableau "agences" résultant de la jointure
                Aggregates.unwind("$agences"),

                // Étape 5 : Projeter les champs nécessaires du document résultant
                Aggregates.project(Projections.include("agences.nom", "nbDispo"))
        );

        // Exécuter la requête d'agrégation et retourner le résultat
        return getDocuments(pipeline, CollectionNames.PARKING.getName());
    }

    public ArrayList<Document> getParkingsByAgenceSortedByPlaces(int idAgence) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.eq("idAgence", idAgence)),
                Aggregates.sort(Sorts.descending("nbDispo")),
                Aggregates.project(Projections.include("idAgence", "nbDispo")),
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "idAgence",
                        "_id",
                        "agences"
                ),
                Aggregates.unwind("$agences"),
                Aggregates.project(Projections.include("agences.nom", "nbDispo"))
        );

        return getDocuments(pipeline, CollectionNames.PARKING.getName());
    }

    public ArrayList<Document> getParkingsByVehiculeBrand(String vehiculeBrand) {
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "idVehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.marque", vehiculeBrand)),
                Aggregates.project(Projections.include("idAgence")),
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "idAgence",
                        "_id",
                        "agences"
                ),
                Aggregates.unwind("$agences"),
                Aggregates.project(Projections.include("agences.nom", "nbDispo"))
        );

        return getDocuments(pipeline, CollectionNames.PARKING.getName());
    }

    public ArrayList<Document> getParkingsByVehiculeModel(String vehiculeModel) {
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "idVehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.modele", vehiculeModel)),
                Aggregates.project(Projections.include("idAgence")),
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "idAgence",
                        "_id",
                        "agences"
                ),
                Aggregates.unwind("$agences"),
                Aggregates.project(Projections.include("agences.nom")
        ));

        return getDocuments(pipeline, CollectionNames.PARKING.getName());
    }

    public ArrayList<Document> getParkingWithImatriculation(String imatriculation) {
        List<Bson> pipeline = List.of(
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "idVehicule",
                        "_id",
                        "vehicules"
                ),
                Aggregates.unwind("$vehicules"),
                Aggregates.match(Filters.eq("vehicules.plaque_imat", imatriculation)),
                Aggregates.project(Projections.include("idAgence")),

                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "idAgence",
                        "_id",
                        "agences"
                ),
                Aggregates.unwind("$agences"),
                Aggregates.project(Projections.include("agences.nom")
        ));

        return getDocuments(pipeline, CollectionNames.PARKING.getName());
    }
}
