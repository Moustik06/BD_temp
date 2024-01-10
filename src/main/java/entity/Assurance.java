package entity;

import org.bson.Document;

public class Assurance extends BaseEntity{
    /*
_id
id vehicule
type
nom assurance
prix assurance
 */
    private final int  _id;
    private final int _id_vehicule;
    private final String type;
    private final String nom_assurance;
    private final float prix_assurance;


    public Assurance(int _id, int _id_vehicule, String type, String nom_assurance, float prix_assurance) {
        this._id = _id;
        this._id_vehicule = _id_vehicule;
        this.type = type;
        this.nom_assurance = nom_assurance;
        this.prix_assurance = prix_assurance;
    }


    @Override
    public Document generateDocument() {
        return new Document("_id", this._id)
                .append("id_vehicule", this._id_vehicule)
                .append("type", this.type)
                .append("nom_assurance", this.nom_assurance)
                .append("prix_assurance", this.prix_assurance);
    }

}
