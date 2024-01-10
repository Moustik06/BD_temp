import entity.*;

import java.util.Date;

public class main {
    public static void main(String[] args) {
        int[] emplyer_id = new int[3];
        emplyer_id[0] = 1;
        emplyer_id[1] = 2;
        emplyer_id[2] = 3;


        Agence ag1 = new Agence(1, emplyer_id, 1, "agence1", "adresse1", "telephone1");
        Assurance ac1 = new Assurance(1, 1, "assurance1", "adresse1", 300);
        Client c1 = new Client(1, "nom1", "prenom1", "adresse1", "telephone1", "email1@email.fr");
        Employer emp1 = new Employer(1, 1, "nom1", "prenom1", "adresse1", "telephone1", "Directeur");
        Facture fact1 = new Facture(1,1, new Date().toString(),1000,false);
        Location loc1 = new Location(1,1,1,1,1,"demain", "apres demain", 1, 2,500);
        Parking park1 = new Parking(1,1,1,1,1);
        Vehicule veh1 = new Vehicule(1,1,"marque1","modele1",100,100,"plaque1");

        for (BaseEntity entity : new BaseEntity[]{ag1, ac1, c1, emp1, fact1, loc1, park1, veh1}) {
            System.out.println(entity.generateDocument().toJson());
        }
    }
}
