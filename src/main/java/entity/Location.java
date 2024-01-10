package entity;

import org.bson.Document;

public class Location extends BaseEntity{

    /*
    _id
    id client
    id vehicule
    id agence
    id employe
    date début
    date fin
    _id_parkingRecup
    _id_parkingRendu 
    prix ttc
     */

    private final int _id;
    private final int _id_client;
    private final int _id_vehicule;
    private final int _id_agence;
    private final int _id_employe;
    private final String date_debut;
    private final String date_fin;
    private final int _id_parkingRecup;
    private final int _id_parkingRendu;
    private final float prix_ttc;

    public Location(int _id, int _id_client, int _id_vehicule, int _id_agence, int _id_employe, String date_debut, String date_fin, int _id_parkingRecup, int _id_parkingRendu, float prix_ttc) {
        this._id = _id;
        this._id_client = _id_client;
        this._id_vehicule = _id_vehicule;
        this._id_agence = _id_agence;
        this._id_employe = _id_employe;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this._id_parkingRecup = _id_parkingRecup;
        this._id_parkingRendu = _id_parkingRendu;
        this.prix_ttc = prix_ttc;
    }

    @Override
    public Document generateDocument() {
        return new Document("_id", this._id)
                .append("id_client", this._id_client)
                .append("id_vehicule", this._id_vehicule)
                .append("id_agence", this._id_agence)
                .append("id_employe", this._id_employe)
                .append("date_debut", this.date_debut)
                .append("date_fin", this.date_fin)
                .append("id_parkingRecup", this._id_parkingRecup)
                .append("id_parkingRendu", this._id_parkingRendu)
                .append("prix_ttc", this.prix_ttc);
    }


}


