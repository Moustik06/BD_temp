package entity;

import org.bson.Document;

public class Vehicule extends BaseEntity{

    /*
    _id
    id agence
    marque
    modèle
    prix
    caution
    plaque imat
     */

    private final int _id;
    private final int _id_agence;
    private final String marque;
    private final String modele;
    private final double prix;
    private final double caution;
    private final String plaque_imat;

    public Vehicule(int _id, int _id_agence, String marque, String modele, double prix, double caution, String plaque_imat) {
        this._id = _id;
        this._id_agence = _id_agence;
        this.marque = marque;
        this.modele = modele;
        this.prix = prix;
        this.caution = caution;
        this.plaque_imat = plaque_imat;
    }
    @Override
    public Document generateDocument() {
        return new Document("_id", this._id)
                .append("id_agence", this._id_agence)
                .append("marque", this.marque)
                .append("modele", this.modele)
                .append("prix", this.prix)
                .append("caution", this.caution)
                .append("plaque_imat", this.plaque_imat);
    }
}
