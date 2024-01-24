package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmployerDAO extends BaseDAO {

    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.EMPLOYE.getName());
        collection.createIndex(new Document("id_agence", 1));
        collection.createIndex(new Document("emploi", 1));

    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.EMPLOYE.getName());
        return getDocuments(criteria, collection);
    }

    // Méthode pour trouver les employés par agence et leur nom prenom et emploi
    public ArrayList<Document> employesParAgence(int idAgence) {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les documents de la collection "Employe" par id_agence
                Aggregates.match(Filters.eq("id_agence", idAgence)),
                // Étape 2 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "nom",
                                        "prenom",
                                        "emploi"
                                )
                        )
                )
        );

        return getDocuments(pipeline, CollectionNames.EMPLOYE.getName());
    }

    // Méthode pour trouver les détails de l'employé avec les informations de son agence
    public ArrayList<Document> detailsEmployeParAgence(int idEmploye) {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les documents de la collection "Employe" par _id
                Aggregates.match(Filters.eq("_id", idEmploye)),
                // Étape 2 : Effectuer une jointure avec la collection "Agence" sur le champ "id_agence"
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "id_agence",
                        "_id",
                        "agenceDetails"
                ),
                // Étape 3 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "id_agence",
                                        "nom",
                                        "prenom",
                                        "adresse",
                                        "tel",
                                        "emploi",
                                        "agenceDetails._id",
                                        "agenceDetails.id_employer",
                                        "agenceDetails.id_parking",
                                        "agenceDetails.nom",
                                        "agenceDetails.adresse",
                                        "agenceDetails.telephone"
                                )
                        )
                )
        );

        return getDocuments(pipeline, CollectionNames.EMPLOYE.getName());
    }


    // Méthode pour obtenir la liste des chefs de toutes les agences et affiche nom prenom et nmo agence
    public ArrayList<Document> chefsAgences() {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les documents de la collection "Employe" par emploi = "chef"
                Aggregates.match(Filters.eq("emploi", "chef")),
                // Étape 2 : Effectuer une jointure avec la collection "Agence" sur le champ "id_agence"
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "id_agence",
                        "_id",
                        "agenceDetails"
                ),
                // Étape 3 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "nom",
                                        "prenom",
                                        "agenceDetails.nom"
                                )
                        )
                )
        );

        return getDocuments(pipeline, CollectionNames.EMPLOYE.getName());
    }

    //iciiiiiiiiiiiiii soucis jarrive pas a ajouter nom prenom en plus de l'id du vendeur
    // Méthode pour trouver les employés qui vende le plus de locations par agence
    // afficher nom prenmo de chaque employe nombredevente et nom agence

    public ArrayList<Document> employesPlusLocationsVendues() {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Effectuer une jointure avec la collection "Location" sur le champ "_id_employer"
                Aggregates.lookup(
                        CollectionNames.LOCATION.getName(),
                        "_id",
                        "_id_employer",
                        "locations"
                ),
                // Étape 2 : Grouper les documents par _id et compter le nombre de locations
                Aggregates.group("$_id", Accumulators.sum("nombreLocations", 1)),
                // Étape 3 : Effectuer une jointure avec la collection "Agence" sur le champ "id_agence"
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "_id",
                        "_id",
                        "agenceDetails"
                ),
                // Étape 4 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "nombreLocations",
                                        "agenceDetails.nom"
                                )
                        )
                ),
                // Étape 5 : Trier les documents par nombre de locations
                Aggregates.sort(Sorts.descending("nombreLocations"))
        );

        return getDocuments(pipeline, CollectionNames.EMPLOYE.getName());
    }

    //meme soucis jarrive pas a mettre nom prenom
    // Méthode pour trouver la moyenne des locations de tous les employer par agence affihe nom prenmo employer aussi
    public ArrayList<Document> moyenneLocationsEmployeParAgence() {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Effectuer une jointure avec la collection "Location" sur le champ "_id_employer"
                Aggregates.lookup(
                        CollectionNames.LOCATION.getName(),
                        "_id",
                        "_id_employer",
                        "locations"
                ),
                //recup nom prenmo des employer
                Aggregates.lookup(
                        CollectionNames.EMPLOYE.getName(),
                        "_id",
                        "_id",
                        "employerDetails"
                ),
                // Étape 2 : Grouper les documents par _id et calculer la moyenne des locations
                Aggregates.group("$_id", Accumulators.avg("moyenneLocations", 1)),
                // Étape 3 : Effectuer une jointure avec la collection "Agence" sur le champ "id_agence"
                Aggregates.lookup(
                        CollectionNames.AGENCE.getName(),
                        "_id",
                        "_id",
                        "agenceDetails"
                ),
                // Étape 4 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include(
                                        "_id",
                                        "moyenneLocations",
                                        "agenceDetails.nom",
                                        "employerDetails.nom",
                                        "employerDetails.prenom"
                                )
                        )
                ),
                // Étape 5 : Trier les documents par moyenne des locations
                Aggregates.sort(Sorts.descending("moyenneLocations"))
        );

        return getDocuments(pipeline, CollectionNames.EMPLOYE.getName());
    }


}
