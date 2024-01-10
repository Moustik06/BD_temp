package entity;


import org.bson.Document;
import java.util.Arrays;

public class Agence extends BaseEntity{

    private final int _id;
    private final int[] _id_employer;
    private final int _id_parking;
    private final String nom;
    private final String adresse;
    private final String telephone;



    public Agence(int id, int[] id_employer, int id_parking, String nom, String adresse, String telephone) {
        this._id = id;
        this._id_employer = id_employer;
        this._id_parking = id_parking;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public Document generateDocument(){
        return new Document("_id", this._id)
                .append("id_employer", Arrays.toString(this._id_employer))
                .append("id_parking", this._id_parking)
                .append("nom", this.nom)
                .append("adresse", this.adresse)
                .append("telephone", this.telephone);
    }

    public static void main(String[] args) {
        int[] emplyer_id = new int[3];
        emplyer_id[0] = 1;
        emplyer_id[1] = 2;
        emplyer_id[2] = 3;
        Agence agence1 = new Agence(1,emplyer_id, 1, "agence1", "adresse1", "telephone1");
        Document curr = agence1.generateDocument();
        // pprint the document
        System.out.println(curr.toJson());
    }
}
