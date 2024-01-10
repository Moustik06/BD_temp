package entity;

import org.bson.Document;

import java.util.Date;

public class Facture extends BaseEntity{
    /*
    _id
    _id location
    date
    total ttc
    acquitté
     */

    private final int _id;
    private final int _idLocation;
    private final String date;
    private final double totalTTC;
    private final boolean acquitte;

    public Facture(int _id, int _idLocation, String date, double totalTTC, boolean acquitte) {
        this._id = _id;
        this._idLocation = _idLocation;
        this.date = date;
        this.totalTTC = totalTTC;
        this.acquitte = acquitte;
    }


    @Override
    public Document generateDocument() {
        return new Document("_id", this._id)
                .append("idLocation", this._idLocation)
                .append("date", this.date)
                .append("totalTTC", this.totalTTC)
                .append("acquitte", this.acquitte);
    }
}
