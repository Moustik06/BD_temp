package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.BsonType;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehiculeDAO extends BaseDAO {
    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.VEHICULE.getName());
        collection.createIndex(new Document("marque", 1));
        collection.createIndex(new Document("modele", 1));
        collection.createIndex(new Document("plaque_imat", 1));
    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.VEHICULE.getName());
        return getDocuments(criteria, collection);
    }

    //  liste des véhicules disponibles dans une agence spécifique. ok
    public ArrayList<Document> vehiculesParAgence(int idAgence) {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les véhicules par id_agence
                Aggregates.match(Filters.eq("id_agence", idAgence)),
                // Étape 2 : Effectuer une jointure avec la collection "Agence" sur le champ "id_agence"
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "id_agence",
                        "_id",
                        "agenceDetails"
                ),
                // Étape 3 : Unwind pour aplatir le tableau résultant de la jointure
                Aggregates.unwind("$agenceDetails"),
                // Étape 4 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "marque",
                                        "modele",
                                        "prix",
                                        "caution",
                                        "plaque_imat",
                                        "agenceDetails.nom" // Ajouter le nom de l'agence
                                )
                        )
                ),
                // Étape 5 : Filtrer les résultats qui ont "_id" au format ObjectId
                Aggregates.match(Filters.not(Filters.type("_id", BsonType.OBJECT_ID)))
        );

        return getDocuments(pipeline, CollectionNames.VEHICULE.getName());
    }

    // véhicules d'une marque spécifique avec des détails sur l'agence associée ok
    public ArrayList<Document> vehiculesParMarqueAvecDetails(String marque) {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("marque", marque)),
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "id_agence",
                        "_id",
                        "agenceDetails"
                ),
                Aggregates.unwind("$agenceDetails")
        );
        return getDocuments(pipeline, CollectionNames.VEHICULE.getName());
    }


    //Véhicules par Plaque d'immatriculation avec Détails de Location :
    public ArrayList<Document> vehiculesParPlaqueAvecDetails(String plaqueImat) {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("plaque_imat", plaqueImat)),
                Aggregates.lookup(
                        CollectionNames.LOCATION.getName(),
                        "_id",
                        "id_vehicule",
                        "locations"
                ),
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "locations.id_client",
                        "_id",
                        "clientDetails"
                ),
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "marque",
                                        "modele",
                                        "prix",
                                        "caution",
                                        "plaque_imat",
                                        "locations.date_debut",
                                        "locations.date_fin",
                                        "clientDetails.nom",
                                        "clientDetails.prenom",
                                        "clientDetails.telephone"
                                )
                        )
                )
        );


        return getDocuments(pipeline, CollectionNames.VEHICULE.getName());
    }

    //Véhicules par Prix et Agence :
    public ArrayList<Document> vehiculesParPrixEtAgence(double prixMax, String nomAgence) {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.lte("prix", prixMax)),
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "id_agence",
                        "_id",
                        "agenceDetails"
                ),

                Aggregates.unwind("$agenceDetails"),
                Aggregates.match(Filters.eq("agenceDetails.nom", nomAgence)),
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "marque",
                                        "modele",
                                        "prix",
                                        "caution",
                                        "plaque_imat",
                                        "agenceDetails.nom"
                                )
                        )
                )
        );
        return getDocuments(pipeline, CollectionNames.VEHICULE.getName());
    }


    // marque la plus loué (par ordre décroissant) et le nombre de fois qu'elle a été loué
    public ArrayList<Document> marqueModelePlusLoues() {
        // Définir le pipeline d'agrégation
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Effectuer une jointure avec la collection "Location" sur le champ "id_vehicule"
                Aggregates.lookup(
                        CollectionNames.LOCATION.getName(),
                        "_id",
                        "id_vehicule",
                        "locationDetails"
                ),
                // Étape 2 : Unwind pour aplatir le tableau résultant de la jointure
                Aggregates.unwind("$locationDetails"),
                // Étape 3 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "marque",
                                        "modele",
                                        "locationDetails._id_vehicule",
                                        "locationDetails._id_agence",
                                        "locationDetails._id_client",
                                        "locationDetails.date_debut",
                                        "locationDetails.date_fin"
                                )
                        )
                ),
                Aggregates.group(
                        new Document("marque", "$marque")
                                .append("modele", "$modele"),
                        Accumulators.sum("count", 1)
                ),

                Aggregates.sort(Sorts.descending("count"))


        );

        return getDocuments(pipeline, CollectionNames.VEHICULE.getName());

    }


}


