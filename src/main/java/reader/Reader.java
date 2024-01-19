package reader;

import entity.*;
import org.bson.BsonType;
import org.bson.Document;
import org.bson.json.JsonReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Reader {

    public Document[] agenceReader() {
        Document[] agenceDocuments = new Document[1000];

        try {
            // Ouvrir le fichier json : AgenceData.json
            Path path = Paths.get("src/main/resources/AgenceData.json");
            String content = Files.readString(path);

            // Créer un JsonReader à partir du contenu du fichier json
            JsonReader reader = new JsonReader(content);
            reader.readStartArray();

            //key
            String id,idEmployer,idParking,nom,adresse,telephone;

            //value
            int idInt,idParkingInt;
            ArrayList<Integer> idEmployerInt = null;
            String nomString,adresseString,telephoneString;

            for (int i = 0; i < 1000; i++) {
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                idEmployer = reader.readName();
                String idEmployerString = reader.readString();
                String newString = idEmployerString.substring(1,idEmployerString.length()-1);
                String[] idEmployerStringArray = newString.split(",");
                idEmployerInt = new ArrayList<Integer>();
                for (String s : idEmployerStringArray) {
                    idEmployerInt.add(Integer.parseInt(s));
                }
                idParking = reader.readName();
                idParkingInt = reader.readInt32();
                nom = reader.readName();
                nomString = reader.readString();
                adresse = reader.readName();
                adresseString = reader.readString();
                telephone = reader.readName();
                telephoneString = reader.readString();
                reader.readEndDocument();

                // Créer un objet Agence et le convertir en Document
                Agence agence = new Agence(idInt, idEmployerInt, idParkingInt, nomString, adresseString, telephoneString);
                agenceDocuments[i] = agence.generateDocument();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return agenceDocuments;
    }
    public Document[] clientReader() {
        Document[] clientDocuments = new Document[1000];

        try {
            // Ouvrir le fichier json : ClientData.json
            Path path = Paths.get("src/main/resources/ClientData.json");
            String content = Files.readString(path);

            // Créer un JsonReader à partir du contenu du fichier json
            JsonReader reader = new JsonReader(content);
            reader.readStartArray();

            //{"_id":1,"nom":"De Zuani","prenom":"Josée","adresse":"12th Floor","telephone":"3662217370","email":"edezuani0@flavors.me"}

            //Key
            String id,nom,prenom,adresse,telephone,email;

            //Value
            int idInt;
            String nomString,prenomString,adresseString,telephoneString,emailString;

            for (int i = 0; i < 1000; i++) {
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                nom = reader.readName();
                nomString = reader.readString();
                prenom = reader.readName();
                prenomString = reader.readString();
                adresse = reader.readName();
                adresseString = reader.readString();
                telephone = reader.readName();
                telephoneString = reader.readString();
                email = reader.readName();
                emailString = reader.readString();

                reader.readEndDocument();

                // Créer un objet Client et le convertir en Document
                Client client = new Client(idInt, nomString, prenomString, adresseString, telephoneString, emailString);
                clientDocuments[i] = client.generateDocument();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return clientDocuments;
    }
    public Document[] employerReader() {
        Document[] employerDocuments = new Document[1000];

        try {
            // Ouvrir le fichier json : EmployerData.json
            Path path = Paths.get("src/main/resources/EmployerData.json");
            String content = Files.readString(path);

            // Créer un JsonReader à partir du contenu du fichier json
            JsonReader reader = new JsonReader(content);
            reader.readStartArray();

            //{"_id":1,"_id_agence":1,"nom":"Ovenden","prenom":"Måns","adresse":"Apt 1229","telephone":"5492994392","emploi":"chef"}

            //key
            String id,idAgence,nom,prenom,adresse,telephone,emploi;

            //value
            int idInt,idAgenceInt;
            String nomString,prenomString,adresseString,telephoneString,emploiString;

            for (int i = 0; i < 1000; i++) {
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                idAgence = reader.readName();
                idAgenceInt = reader.readInt32();
                nom = reader.readName();
                nomString = reader.readString();
                prenom = reader.readName();
                prenomString = reader.readString();
                adresse = reader.readName();
                adresseString = reader.readString();
                telephone = reader.readName();
                telephoneString = reader.readString();
                emploi = reader.readName();
                emploiString = reader.readString();
                reader.readEndDocument();

                // Créer un objet Employer et le convertir en Document
                Employer employer = new Employer(idInt, idAgenceInt, nomString, prenomString, adresseString, telephoneString, emploiString);
                employerDocuments[i] = employer.generateDocument();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return employerDocuments;
    }

    public Document[] locationReader() {
        Document[] locationDocuments = new Document[1000];

        try {
            // Open the json file: LocationData.json
            Path path = Paths.get("src/main/resources/LocationData.json");
            String content = Files.readString(path);

            // Create a JsonReader from the json file content
            JsonReader reader = new JsonReader(content);
            reader.readStartArray();

            // {"_id":1,"_id_client":1,"_id_vehicule":1,"_id_agence":1,"id_employer":1,"id_parkingRecup":1,"_id_parkingRendu":1,"date_debut":"7/16/2023","date_fin":"7/9/2023","prix_ttc":225.0}

            //key
            String id,idClient,idVehicule,idAgence,idEmploye,idParkingRecup,idParkingRendu,dateDebut,dateFin,prixTTC;

            //value
            int idInt,idClientInt,idVehiculeInt,idAgenceInt,idEmployeInt,idParkingRecupInt,idParkingRenduInt;
            String dateDebutString,dateFinString;
            double prixTTCDouble;
            for (int i = 0; i < 1000; i++) {
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                idClient = reader.readName();
                idClientInt = reader.readInt32();
                idVehicule = reader.readName();
                idVehiculeInt = reader.readInt32();
                idAgence = reader.readName();
                idAgenceInt = reader.readInt32();
                idEmploye = reader.readName();
                idEmployeInt = reader.readInt32();
                idParkingRecup = reader.readName();
                idParkingRecupInt = reader.readInt32();
                idParkingRendu = reader.readName();
                idParkingRenduInt = reader.readInt32();
                dateDebut = reader.readName();
                dateDebutString = reader.readString();
                dateFin = reader.readName();
                dateFinString = reader.readString();
                prixTTC = reader.readName();
                prixTTCDouble = reader.readDouble();
                reader.readEndDocument();

                // Create a Location object and convert it to a Document
                Location location = new Location(idInt, idClientInt, idVehiculeInt, idAgenceInt, idEmployeInt, dateDebutString, dateFinString, idParkingRecupInt, idParkingRenduInt, prixTTCDouble);
                locationDocuments[i] = location.generateDocument();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return locationDocuments;
    }
    public Document[] parkingReader(){
        Parking[] parkings = new Parking[100];
        try {
            // Open the json file : ParkinData.json
            Path path = Paths.get("src/main/resources/ParkingData.json");
            String content = Files.readString(path);
            // Create a JsonReader from the json file content

            JsonReader reader = new JsonReader(content);
            reader.readStartArray();
            // Read the first element of the json file
            //reader.readStartDocument();
            // Print it to the console
            String id, idVehicule,idAgence,nbPlace,nbDispo;
            int idInt, idVehiculeInt,idAgenceInt,nbPlaceInt,nbDispoInt;

            for (int i = 0; i < 100;i++){
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                idVehicule = reader.readName();
                idVehiculeInt = reader.readInt32();
                idAgence = reader.readName();
                idAgenceInt = reader.readInt32();
                nbPlace = reader.readName();
                nbPlaceInt = reader.readInt32();
                nbDispo = reader.readName();
                nbDispoInt = reader.readInt32();

                reader.readEndDocument();
                //System.out.println(id + ": " + idInt + " " + idVehicule + ": " + idVehiculeInt + " " + idAgence + ": " + idAgenceInt + " " + nbPlace + ": " + nbPlaceInt + " " + nbDispo + ": " + nbDispoInt);
                Parking parking = new Parking(idInt,idVehiculeInt,idAgenceInt,nbPlaceInt,nbDispoInt);
                parkings[i] = parking;

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        Document[] documents = new Document[100];
        for (int i = 0; i < 100;i++){
            documents[i] = parkings[i].generateDocument();
        }
        return documents;

    }
    public Document[] vehiculeReader() {
        Document[] vehiculeDocuments = new Document[1000];

        try {
            // Open the json file: VehiculeData.json
            Path path = Paths.get("src/main/resources/VehiculeData.json");
            String content = Files.readString(path);

            // Create a JsonReader from the json file content
            JsonReader reader = new JsonReader(content);
            reader.readStartArray();

            //{"_id":1,"_id_agence":1,"marque":"Toyota","modele":"Tercel","prix":192.06,"caution":120,"plaque_imat":"SCBCP73W08C053747"}
            // Read the first element of the json file


            String id, idAgence, marque, modele, plaqueImat,prix,caution;
            String marqueString, modeleString, plaqueImatString;
            double prixDouble, cautionDouble;
            int idInt, idAgenceInt;

            for (int i = 0; i < 1000; i++) {
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                idAgence = reader.readName();
                idAgenceInt = reader.readInt32();
                marque = reader.readName();
                marqueString = reader.readString();
                modele = reader.readName();
                modeleString = reader.readString();
                prix = reader.readName();
                prixDouble = reader.readDouble();
                caution = reader.readName();
                cautionDouble = reader.readDouble();
                plaqueImat = reader.readName();
                plaqueImatString = reader.readString();
                reader.readEndDocument();

                // Create a Vehicule object and convert it to a Document
                Vehicule vehicule = new Vehicule(idInt, idAgenceInt, marqueString, modeleString, prixDouble, cautionDouble, plaqueImatString);
                vehiculeDocuments[i] = vehicule.generateDocument();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return vehiculeDocuments;
    }

    public Document[] factureReader() {
        Document[] factureDocuments = new Document[1000];

        try {
            // Ouvrir le fichier json : FactureData.json
            Path path = Paths.get("src/main/resources/FactureData.json");
            String content = Files.readString(path);

            // Créer un JsonReader à partir du contenu du fichier json
            JsonReader reader = new JsonReader(content);
            reader.readStartArray();

            // {"_id":1,"_idLocation":1,"date":"5/24/2023","totalTTC":427.4,"acquitte":true}

            //key
            String id,idLocation,date,totalTTC,acquitte;

            //value
            int idInt,idLocationInt;
            String dateString;
            double totalTTCDouble;
            boolean acquitteBoolean;

            for (int i = 0; i < 1000; i++) {
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                idLocation = reader.readName();
                idLocationInt = reader.readInt32();
                date = reader.readName();
                dateString = reader.readString();
                totalTTC = reader.readName();
                totalTTCDouble = reader.readDouble();
                acquitte = reader.readName();
                acquitteBoolean = reader.readBoolean();
                reader.readEndDocument();

                // Créer un objet Facture et le convertir en Document
                Facture facture = new Facture(idInt, idLocationInt, dateString, totalTTCDouble, acquitteBoolean);
                factureDocuments[i] = facture.generateDocument();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return factureDocuments;
    }

    public Document[] assuranceReader() {
        Document[] assuranceDocuments = new Document[1000];

        try {
            // Ouvrir le fichier json : AssuranceData.json
            Path path = Paths.get("src/main/resources/AssuranceData.json");
            String content = Files.readString(path);

            // Créer un JsonReader à partir du contenu du fichier json
            JsonReader reader = new JsonReader(content);
            reader.readStartArray();

            // {"_id":1,"_id_vehicule":1,"type":"tier","nom_assurance":"Waelchi Inc","prix_assurance":155.7}

            //key
            String id,idVehicule,type,nomAssurance,prixAssurance;

            //value
            int idInt,idVehiculeInt;
            String typeString,nomAssuranceString;
            double prixAssuranceDouble;

            for (int i = 0; i < 1000; i++) {
                reader.readStartDocument();
                id = reader.readName();
                idInt = reader.readInt32();
                idVehicule = reader.readName();
                idVehiculeInt = reader.readInt32();
                type = reader.readName();
                typeString = reader.readString();
                nomAssurance = reader.readName();
                nomAssuranceString = reader.readString();
                prixAssurance = reader.readName();
                prixAssuranceDouble = reader.readDouble();
                reader.readEndDocument();

                // Créer un objet Assurance et le convertir en Document
                Assurance assurance = new Assurance(idInt, idVehiculeInt, typeString, nomAssuranceString, prixAssuranceDouble);
                assuranceDocuments[i] = assurance.generateDocument();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return assuranceDocuments;
    }

}
