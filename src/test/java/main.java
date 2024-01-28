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
        ArrayList<Document> doc = locationDAO.findLocationsByBrandDesc();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //vehiculesParAgence
        ArrayList<Document> doc = vehiculeDAO.vehiculesParAgence(2);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //vehiculesParMarqueAvecDetails
        ArrayList<Document> doc = vehiculeDAO.vehiculesParMarqueAvecDetails("Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //modelePlusLoue couiic couiccc elle aussi
        ArrayList<Document> doc = vehiculeDAO.modelePlusLoue();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //marqueModelePlusLoues a revoir ca marche pas
        ArrayList<Document> doc = vehiculeDAO.marqueModelePlusLoues();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //sortAgencesByNom
        ArrayList<Document> doc = agenceDAO.sortAgencesByNom();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getAgenceWithMaxEmployes
        ArrayList<Document> doc = agenceDAO.getAgenceWithMaxEmployes();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getAgenceChef
        ArrayList<Document> doc = agenceDAO.getAgenceChef(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //countLocationsAgence
        Long doc = locationDAO.countLocationsAgence(1);
        System.out.println(doc);

        //findAgencePlusClients
        ArrayList<Document> doc = agenceDAO.findAgencePlusClients();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findAgencePlusVehicules
        ArrayList<Document> doc = agenceDAO.findAgencePlusVehicules();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findAgencePlusChiffreAffaires
        ArrayList<Document> doc = agenceDAO.findAgencePlusChiffreAffaires();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findAssurancesByVehicle
        ArrayList<Document> doc = assuranceDAO.findAssurancesByVehicle("Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findAssurancesByVehicleAndType
        ArrayList<Document> doc = assuranceDAO.findAssurancesByVehicleAndType("Audi", "full");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findAssuranceByVehicleImmatriculation
        ArrayList<Document> doc = assuranceDAO.findAssuranceByVehicleImmatriculation("WAUJFAFH7BN867243");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findAssurancesFromVehicleModele
        ArrayList<Document> doc = assuranceDAO.findAssurancesFromVehicleModele("Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findAssurancesFromVehicleMarque
        ArrayList<Document> doc = assuranceDAO.findAssurancesFromVehicleMarque("Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getLocationsForClient
        ArrayList<Document> doc = clientDAO.getLocationsForClient(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getClientsForLocation
        ArrayList<Document> doc = clientDAO.getClientsForLocation(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getClientWithMaxLocations
        ArrayList<Document> doc = clientDAO.getClientWithMaxLocations();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getMostRentedVehicleForClient
        ArrayList<Document> doc = clientDAO.getMostRentedVehicleForClient(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findClientsQuiDepensentLePlus
        ArrayList<Document> doc = clientDAO.findClientsQuiDepensentLePlus();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //employesParAgence
        ArrayList<Document> doc = employeDAO.employesParAgence(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //detailsEmployeParAgence
        ArrayList<Document> doc = employeDAO.detailsEmployeParAgence(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //chefsAgences
        ArrayList<Document> doc = employeDAO.chefsAgences();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //employesPlusLocationsVendues
        ArrayList<Document> doc = employeDAO.employesPlusLocationsVendues();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //moyenneLocationsEmployeParAgence
        ArrayList<Document> doc = employeDAO.moyenneLocationsEmployeParAgence(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //totalMontantTTCParAgence
        ArrayList<Document> doc = factureDAO.totalMontantTTCParAgence(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findFacturesImpayeesParAgence
        ArrayList<Document> doc = factureDAO.findFacturesImpayeesParAgence(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //facturesParClient
        ArrayList<Document> doc = factureDAO.facturesParClient(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //montantTotalFacturesParClient
        ArrayList<Document> doc = factureDAO.montantTotalFacturesParClient(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findFactureLaPlusGrosseParDate
        ArrayList<Document> doc = factureDAO.findFactureLaPlusGrosseParDate();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findLocationWithClientAndVehicleDetailsFromId
        ArrayList<Document> doc = locationDAO.findLocationWithClientAndVehicleDetailsFromId(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findLocationsForClientAndSortByStartDate
        ArrayList<Document> doc = locationDAO.findLocationsForClientAndSortByStartDate(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findLocationsWithVehicleDetailsAndSortByStartDate
        ArrayList<Document> doc = locationDAO.findLocationsWithVehicleDetailsAndSortByStartDate();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findLocationsByAgencyWithPriceAbove
        ArrayList<Document> doc = locationDAO.findLocationsByAgencyWithPriceAbove(1, 100);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findLocationsByBrand
        ArrayList<Document> doc = locationDAO.findLocationsByBrand("Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //findLocationsByBrandDesc
        ArrayList<Document> doc = locationDAO.findLocationsByBrandDesc();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getParkingsWithAvailablePlaces
        ArrayList<Document> doc = parkingDAO.getParkingsWithAvailablePlaces();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getParkingsByAgenceSortedByPlaces
        ArrayList<Document> doc = parkingDAO.getParkingsByAgenceSortedByPlaces(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getParkingsByVehiculeBrand
        ArrayList<Document> doc = parkingDAO.getParkingsByVehiculeBrand("Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getParkingsByVehiculeModel
        ArrayList<Document> doc = parkingDAO.getParkingsByVehiculeModel("M5");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //getParkingWithImatriculation
        ArrayList<Document> doc = parkingDAO.getParkingWithImatriculation("WAUJFAFH7BN867243");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //vehiculesParAgence
        ArrayList<Document> doc = vehiculeDAO.vehiculesParAgence(1);
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //vehiculesParMarqueAvecDetails
        ArrayList<Document> doc = vehiculeDAO.vehiculesParMarqueAvecDetails("Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //vehiculesParPlaqueAvecDetails
        ArrayList<Document> doc = vehiculeDAO.vehiculesParPlaqueAvecDetails("WAUJFAFH7BN867243");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //vehiculesParPrixEtAgence
        ArrayList<Document> doc = vehiculeDAO.vehiculesParPrixEtAgence(100, "Audi");
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        //marqueModelePlusLoues
        ArrayList<Document> doc = vehiculeDAO.marqueModelePlusLoues();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }

        */

        BaseDAO.closeConnection();
    }
}
