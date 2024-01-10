package entity;

import org.bson.Document;

public class Parking extends BaseEntity{

    /*
    _id
    id vehicule
    id agence
    nb place
    nb dispo
     */

    private final int _id;
    private final int _idVehicule;
    private final int _idAgence;
    private final int nombrePlace;
    private final int nombrePlaceDispo;

    public Parking(int _id, int _idVehicule, int _idAgence, int nbPlace, int nbDispo) {
        this._id = _id;
        this._idVehicule = _idVehicule;
        this._idAgence = _idAgence;
        this.nombrePlace = nbPlace;
        this.nombrePlaceDispo = nbDispo;
    }


    @Override
    public Document generateDocument() {
        return new Document("_id", this._id)
                .append("idVehicule", this._idVehicule)
                .append("idAgence", this._idAgence)
                .append("nbPlace", this.nombrePlace)
                .append("nbDispo", this.nombrePlaceDispo);
    }
}
