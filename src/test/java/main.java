import com.mongodb.client.MongoCollection;
import entityDAO.*;
import org.bson.Document;
import reader.Reader;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.List;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {
        /*
        Document[] agences = new Document[1000];
        Document[] assurances = new Document[1000];
        Document[] clients = new Document[1000];
        Document[] employes = new Document[1000];
        Document[] factures = new Document[1000];
        Document[] locations = new Document[1000];
        Document[] parkings = new Document[100];
        Document[] vehicules = new Document[1000];

        Reader reader = new Reader();
        agences = reader.agenceReader();
        assurances = reader.assuranceReader();
        clients = reader.clientReader();
        employes = reader.employerReader();
        factures = reader.factureReader();
        locations = reader.locationReader();
        parkings = reader.parkingReader();
        vehicules = reader.vehiculeReader();
        */

        /*
        * Sert de base pour l'instant, plus tard on doit faire des requètes plus dur qui seront écrire dans la class
        * associé a la table mais vu que pour l'instant c'est simple on peut tout faire d'un coup
        */

        AgenceDAO agenceDAO = new AgenceDAO();
        AssuranceDAO assuranceDAO = new AssuranceDAO();
        ClientDAO clientDAO = new ClientDAO();
        EmployerDAO employeDAO = new EmployerDAO();
        FactureDAO factureDAO = new FactureDAO();
        LocationDAO locationDAO = new LocationDAO();
        ParkingDAO parkingDAO = new ParkingDAO();
        VehiculeDAO vehiculeDAO = new VehiculeDAO();

        /*
        for (int i = 0; i < 1000; i++) {

            agenceDAO.insert(agences[i], CollectionNames.AGENCE.getName());
            agenceDAO.insert(assurances[i], CollectionNames.ASSURANCE.getName());
            agenceDAO.insert(clients[i], CollectionNames.CLIENT.getName());
            agenceDAO.insert(employes[i], CollectionNames.EMPLOYE.getName());
            agenceDAO.insert(factures[i], CollectionNames.FACTURE.getName());
            agenceDAO.insert(locations[i], CollectionNames.LOCATION.getName());
            agenceDAO.insert(vehicules[i], CollectionNames.VEHICULE.getName());
        }
        for (int i = 0; i < 100; i++) {
            agenceDAO.insert(parkings[i], CollectionNames.PARKING.getName());
        }
        agenceDAO.createIndexes();
        assuranceDAO.createIndexes();
        clientDAO.createIndexes();
        employeDAO.createIndexes();
        factureDAO.createIndexes();
        locationDAO.createIndexes();
        parkingDAO.createIndexes();
        vehiculeDAO.createIndexes();

        agenceDAO.createIndexes();
        */

        /*
        ArrayList<Document> doc = agenceDAO.sortAgencesByNom();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //faits moi le test de la fonction findAgencesByNombreEmployes
        // Appel de la méthode pour trouver le chef d'une agence

        ArrayList<Document> agences = agenceDAO.findAgencePlusChiffreAffaires();
        for (Document agence : agences) {
            System.out.println(agence);
        }


        ArrayList<Document> totalMontantTTC = factureDAO.totalMontantTTCParAgence(1);
        for (Document result : totalMontantTTC) {
            System.out.println(result);
        }


        ArrayList<Document> facturesImpayees = factureDAO.findFacturesImpayeesParAgence(8);
        for (Document result : facturesImpayees) {
            System.out.println(result);
        }

        //montantTotalFacturesParClient
        ArrayList<Document> montantTotalFacturesParClient = factureDAO.montantTotalFacturesParClient();
        for (Document result : montantTotalFacturesParClient) {
            System.out.println(result);
        }

        //factures pour un client donné
        ArrayList<Document> facturesParClient = factureDAO.facturesParClient(31 );
        for (Document result : facturesParClient) {
            System.out.println(result);
        }


        //findFactureLaPlusGrosseParDate
        ArrayList<Document> factureLaPlusGrosseParDate = factureDAO.findFactureLaPlusGrosseParDate("1/2/2024");
        for (Document result : factureLaPlusGrosseParDate) {
            System.out.println(result);
        }

        //detailsEmployeParAgence
        ArrayList<Document> detailsEmployeParAgence = employeDAO.detailsEmployeParAgence(1);
        for (Document result : detailsEmployeParAgence) {
            System.out.println(result);
        }


        //employesParAgence
        ArrayList<Document> employesParAgence = employeDAO.employesParAgence(1);
        for (Document result : employesParAgence) {
            System.out.println(result);
        }


        //chefsAgences
        ArrayList<Document> chefsAgences = employeDAO.chefsAgences();
        for (Document result : chefsAgences) {
            System.out.println(result);
        }

        //employesPlusLocationsVendues
        ArrayList<Document> employesPlusLocationsVendues = employeDAO.employesPlusLocationsVendues();
        for (Document result : employesPlusLocationsVendues) {
            System.out.println(result);
        }*/

        //moyenneLocationsEmployeParAgence
        ArrayList<Document> moyenneLocationsEmployeParAgence = employeDAO.moyenneLocationsEmployeParAgence();
        for (Document result : moyenneLocationsEmployeParAgence) {
            System.out.println(result);
        }
        BaseDAO.closeConnection();
    }
}
