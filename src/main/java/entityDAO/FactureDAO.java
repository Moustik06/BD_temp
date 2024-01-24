package entityDAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactureDAO extends BaseDAO {
    @Override
    public void createIndexes() {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.FACTURE.getName());
        collection.createIndex(new Document("idLocation", 1));
    }

    @Override
    public ArrayList<Document> findByCriteria(Document criteria) {
        MongoCollection<Document> collection = getDatabase().getCollection(CollectionNames.FACTURE.getName());
        return getDocuments(criteria, collection);
    }

    // Méthode pour trouver la somme totale des montants TTC des factures pour une agence donnée
    public ArrayList<Document> totalMontantTTCParAgence(int idAgence) {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les documents de la collection "Location" par id_agence
                Aggregates.match(Filters.eq("id_agence", idAgence)),
                // Étape 2 : Effectuer une jointure avec la collection "Facture" sur le champ "idLocation"
                Aggregates.lookup(
                        CollectionNames.FACTURE.getName(),
                        "_id",
                        "idLocation",
                        "factures"
                ),
                // Étape 3 : Aplatir le tableau "factures" résultant de la jointure
                Aggregates.unwind("$factures"),
                // Étape 4 : Grouper tous les documents filtrés et calculer la somme des montants TTC
                Aggregates.group(idAgence, Accumulators.sum("montantTotal", "$factures.totalTTC"))
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }


    // Méthode pour trouver les factures impayées pour une agence donnée
    public ArrayList<Document> findFacturesImpayeesParAgence(int idAgence) {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les documents de la collection "Location" par id_agence
                Aggregates.match(Filters.eq("id_agence", idAgence)),
                // Étape 2 : Effectuer une jointure avec la collection "Facture" sur le champ "idLocation"
                Aggregates.lookup(
                        CollectionNames.FACTURE.getName(),
                        "_id",
                        "idLocation",
                        "factures"
                ),
                // Étape 3 : Aplatir le tableau "factures" résultant de la jointure
                Aggregates.unwind("$factures"),
                // Étape 4 : Filtrer les documents pour ne conserver que ceux non acquittés
                Aggregates.match(Filters.eq("factures.acquitte", false))
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    // Méthode pour obtenir les factures d'un client donné avec son nom, prénom et téléphone
    public ArrayList<Document> facturesParClient(int idClient) {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les documents de la collection "Location" par id_client
                Aggregates.match(Filters.eq("id_client", idClient)),
                // Étape 2 : Effectuer une jointure avec la collection "Facture" sur le champ "idLocation"
                Aggregates.lookup(
                        CollectionNames.FACTURE.getName(),
                        "_id",
                        "idLocation",
                        "factures"
                ),
                // Étape 3 : Aplatir le tableau "factures" résultant de la jointure
                Aggregates.unwind("$factures"),
                // Étape 4 : Effectuer une jointure avec la collection "Client" sur le champ "_id"
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "_id",
                        "_id",
                        "clientDetails"
                ),
                // Étape 5 : Aplatir le tableau "clientDetails" résultant de la jointure
                Aggregates.unwind("$clientDetails"),
                // Étape 6 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include("clientDetails.nom", "clientDetails.prenom", "clientDetails.telephone", "factures")
                        )
                )
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    // Méthode pour trouver le montant total des factures par client avec son nom et prénom
    public ArrayList<Document> montantTotalFacturesParClient() {
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
                )
        );

        return getDocuments(pipeline, CollectionNames.LOCATION.getName());
    }

    // Méthode pour trouver la facture la grosse à une date donnée et afficher le montant et le nom prenom du client
    public ArrayList<Document> findFactureLaPlusGrosseParDate(String date) {
        List<Bson> pipeline = Arrays.asList(
                // Étape 1 : Filtrer les documents de la collection "Facture" par date
                Aggregates.match(Filters.eq("date", date)),
                // Étape 2 : Trier les documents par montant TTC décroissant
                Aggregates.sort(Sorts.descending("totalTTC")),
                // Étape 3 : Limiter le nombre de documents à 1
                Aggregates.limit(1),
                // Étape 4 : Effectuer une jointure avec la collection "Location" sur le champ "idLocation"
                Aggregates.lookup(
                        CollectionNames.LOCATION.getName(),
                        "idLocation",
                        "_id",
                        "locationDetails"
                ),
                // Étape 5 : Aplatir le tableau "locationDetails" résultant de la jointure
                Aggregates.unwind("$locationDetails"),
                // Étape 6 : Effectuer une jointure avec la collection "Client" sur le champ "id_client"
                Aggregates.lookup(
                        CollectionNames.CLIENT.getName(),
                        "locationDetails.id_client",
                        "_id",
                        "clientDetails"
                ),
                // Étape 7 : Aplatir le tableau "clientDetails" résultant de la jointure
                Aggregates.unwind("$clientDetails"),
                // Étape 8 : Projeter les champs nécessaires du document résultant
                Aggregates.project(
                        Projections.fields(
                                Projections.excludeId(),
                                Projections.include("totalTTC", "clientDetails.nom", "clientDetails.prenom")
                        )
                )
        );

        return getDocuments(pipeline, CollectionNames.FACTURE.getName());
    }

}
