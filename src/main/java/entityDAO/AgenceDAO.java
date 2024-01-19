package entityDAO;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
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
    public ArrayList<Document> sortAgencesByNom() { //toi  c bon
        // Définir le pipeline d'agrégation
        List<Bson> pipeline = List.of(
                Aggregates.sort(Sorts.ascending("nom"))
        );

        return getDocuments(pipeline, CollectionNames.AGENCE.getName());
    }

    // Méthode pour trouver les agences par nombre d'employés
    public Document getAgenceWithMaxEmployes() {
        // Définir le pipeline d'agrégation
        List<Bson> pipeline = List.of(
                // Ajouter un champ "nombreEmployes" basé sur la taille du tableau "id_employer"
                Aggregates.addFields(new Field<>("nombreEmployes", new Document("$size", "$id_employer"))),
                // Trier par ordre décroissant selon le champ "nombreEmployes"
                Aggregates.sort(Sorts.descending("nombreEmployes")),
                // Limiter les résultats à un seul document (l'agence avec le plus grand nombre d'employés)
                Aggregates.limit(1)
        );

        // Exécuter la requête d'agrégation et retourner le résultat
        return getDocuments(pipeline, CollectionNames.AGENCE.getName()).get(0);
    }

    // Méthode pour trouver le chef d'une agence
    public ArrayList<Document> getAgenceChef(int idAgence) {
        // Définir le pipeline d'agrégation
        List<Bson> pipeline = List.of(
                // Étape 1 : Filtrer les documents de la collection "Agence" par idAgence
                Aggregates.match(Filters.eq("_id", idAgence)),
                // Étape 2 : Effectuer une jointure avec la collection "Employer" sur le champ "id_employer"
                Aggregates.lookup(
                        CollectionNames.EMPLOYE.getName(),
                        "id_employer",
                        "_id",
                        "employers"
                ),
                // Étape 3 : Aplatir le tableau "employers" résultant de la jointure
                Aggregates.unwind("$employers"),
                // Étape 4 : Filtrer les documents pour ne conserver que ceux avec emploi = "chef"
                Aggregates.match(Filters.eq("employers.emploi", "chef")),
                // Étape 5 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include("employers._id", "employers.nom", "employers.prenom", "employers.adresse", "employers.tel", "employers.emploi")
                        )
                )
        );

        // Exécuter la requête d'agrégation et retourner le résultat
        return getDocuments(pipeline, CollectionNames.AGENCE.getName());
    }


    // Méthode pour trouver le nombre de locations d'une agence
        // Méthode pour trouver le nombre de locations d'une agence
        public long countLocationsAgence(int agenceId) {
            MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.LOCATION.getName());
            Document criteria = new Document("id_agence", agenceId);

            return collection.countDocuments(criteria);
        }


    // Méthode pour trouver l'agence avec le plus grand nombre de clients
    public ArrayList<Document> findAgencePlusClients() {
        // Définir le pipeline d'agrégation

        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Effectuer une jointure avec la collection "Location" sur le champ "id_agence"
                Aggregates.lookup(
                        CollectionNames.LOCATION.getName(),
                        "_id",
                        "id_agence",
                        "locations"
                ),
                // Étape 2 : Aplatir le tableau "locations" résultant de la jointure
                Aggregates.unwind("$locations"),
                // Étape 3 : Effectuer une jointure avec la collection "Client" sur le champ "id_client"
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "locations.id_client",
                        "_id",
                        "clients"
                ),
                // Étape 4 : Aplatir le tableau "clients" résultant de la jointure
                Aggregates.unwind("$clients"),
                // Étape 5 : Grouper les documents par id_agence et compter le nombre de clients
                Aggregates.group(
                        "$_id",
                        Accumulators.sum("nombreClients", 1)
                ),
                // Étape 6 : Trier par ordre décroissant selon le champ "nombreClients"
                Aggregates.sort(Sorts.descending("nombreClients")),
                // Étape 7 : Limiter les résultats à un seul document (l'agence avec le plus grand nombre de clients)
                Aggregates.limit(1)
        );

        // Exécuter la requête d'agrégation et retourner le résultat
        return getDocuments(pipeline, CollectionNames.AGENCE.getName());
    }








    //a vérifier ce qui suit

    // Méthode pour trouver l'agence avec le plus de véhicules
    public ArrayList<Document> findAgencePlusVehicules() {
        // Définir le pipeline d'agrégation

        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Effectuer une jointure avec la collection "Vehicule" sur le champ "id_agence"
                Aggregates.lookup(
                        CollectionNames.VEHICULE.getName(),
                        "_id",
                        "id_agence",
                        "vehicules"
                ),
                // Étape 2 : Aplatir le tableau "vehicules" résultant de la jointure
                Aggregates.unwind("$vehicules"),
                // Étape 3 : Grouper les documents par id_agence et compter le nombre de véhicules
                Aggregates.group(
                        "$_id",
                        Accumulators.sum("nombreVehicules", 1)
                ),
                // Étape 4 : Trier par ordre décroissant selon le champ "nombreVehicules"
                Aggregates.sort(Sorts.descending("nombreVehicules")),
                // Étape 5 : Limiter les résultats à un seul document (l'agence avec le plus grand nombre de véhicules)
                Aggregates.limit(1)
        );

        // Exécuter la requête d'agrégation et retourner le résultat
        return getDocuments(pipeline, CollectionNames.AGENCE.getName());
    }

    // Méthode pour trouver l'agence avec le plus gros chiffre d'affaires
    public ArrayList<Document> findAgencePlusChiffreAffaires() {
        // Définir le pipeline d'agrégation

        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Effectuer une jointure avec la collection "Location" sur le champ "_id_agence"
                Aggregates.lookup(
                        CollectionNames.LOCATION.getName(),
                        "_id",
                        "_id_agence",
                        "locations"
                ),
                // Étape 2 : Aplatir le tableau "locations" résultant de la jointure
                Aggregates.unwind("$locations"),
                // Étape 3 : Effectuer une jointure avec la collection "Facture" sur le champ "_idLocation"
                Aggregates.lookup(
                        CollectionNames.FACTURE.getName(),
                        "locations._id",
                        "_idLocation",
                        "factures"
                ),
                // Étape 4 : Aplatir le tableau "factures" résultant de la jointure
                Aggregates.unwind("$factures"),
                // Étape 5 : Grouper les documents par id_agence et calculer le chiffre d'affaires
                Aggregates.group(
                        "$_id",
                        Accumulators.sum("chiffreAffaires", "$factures.totalTTC")
                ),
                // Étape 6 : Trier par ordre décroissant selon le champ "chiffreAffaires"
                Aggregates.sort(Sorts.descending("chiffreAffaires")),
                // Étape 7 : Limiter les résultats à un seul document (l'agence avec le plus gros chiffre d'affaires)
                Aggregates.limit(1)
        );

        // Exécuter la requête d'agrégation et retourner le résultat
        return getDocuments(pipeline, CollectionNames.AGENCE.getName());
    }
}
