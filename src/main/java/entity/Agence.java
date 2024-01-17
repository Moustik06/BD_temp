package entity;



import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Agence extends BaseEntity{

    private final int _id;
    private ArrayList<Integer> _id_employer = new ArrayList<Integer>();
    private final int _id_parking;
    private final String nom;
    private final String adresse;
    private final String telephone;



    public Agence(int id, ArrayList<Integer> id_employer, int id_parking, String nom, String adresse, String telephone) {
        this._id = id;
        this._id_employer = id_employer;
        this._id_parking = id_parking;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public Document generateDocument(){
        return new Document("_id", this._id)
                .append("id_employer", this._id_employer)
                .append("id_parking", this._id_parking)
                .append("nom", this.nom)
                .append("adresse", this.adresse)
                .append("telephone", this.telephone);
    }

}
