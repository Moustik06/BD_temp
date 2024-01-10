package entity;

import org.bson.Document;

public class Client extends BaseEntity {
    private final int _id;
    private final String nom;
    private final String prenom;
    private final String adresse;
    private final String telephone;
    private final String email;

    public Client(int id, String nom, String prenom, String adresse, String telephone, String email) {
        this._id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    @Override
    public Document generateDocument() {
        return new Document("_id", this._id)
                .append("nom", this.nom)
                .append("prenom", this.prenom)
                .append("adresse", this.adresse)
                .append("telephone", this.telephone)
                .append("email", this.email);
    }
}
