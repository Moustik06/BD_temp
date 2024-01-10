package entity;

import org.bson.Document;

public class Employer extends BaseEntity{

    /*
    _id
    _id agence
    nom
    prenom
    adresse
    tel
    emploi
     */

    private final int _id;
    private final int _id_agence;
    private final String nom;
    private final String prenom;
    private final String adresse;
    private final String tel;
    private final String emploi;

    public Employer(int _id, int _id_agence, String nom, String prenom, String adresse, String tel, String emploi) {
        this._id = _id;
        this._id_agence = _id_agence;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
        this.emploi = emploi;
    }

    @Override
    public Document generateDocument() {

        return new Document("_id", this._id)
                .append("id_agence", this._id_agence)
                .append("nom", this.nom)
                .append("prenom", this.prenom)
                .append("adresse", this.adresse)
                .append("tel", this.tel)
                .append("emploi", this.emploi);
    }
}
