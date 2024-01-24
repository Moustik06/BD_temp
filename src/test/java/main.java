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
        ArrayList<Document> doc = locationDAO.findLocationsByBrandDesc();
        for (Document d : doc) {
            System.out.println(d.toJson());
        }
        BaseDAO.closeConnection();
    }
}
