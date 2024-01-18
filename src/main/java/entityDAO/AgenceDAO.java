package entityDAO;

import entity.Agence;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

public class AgenceDAO extends BaseDAO {

    private static final String COLLECTION_NAME = "agences";

    @Override
    public void insert(Document doc) {
        MongoCollection<Document> collection = getDatabase().getCollection(COLLECTION_NAME);

        try {
            collection.insertOne(doc);
            System.out.println("Agence insérée avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion de l'agence : " + e.getMessage());
        }
    }

    public Document find(int id) {
        // Utilisez votre connexion à la base de données pour récupérer l'agence par ID
        // Assurez-vous de gérer le cas où l'objet n'est pas trouvé (retournez null ou lancez une exception)
        return getDatabase().getCollection("agences").find(new Document("_id", id)).first();
    }
    public void delete(int id) {
        // Utilisez votre connexion à la base de données pour supprimer l'agence par ID
        getDatabase().getCollection("agences").deleteOne(new Document("_id", id));
    }
    @Override
    public void update(int id, Document updatedFields) {
        // Utilisez votre connexion à la base de données pour mettre à jour l'agence par ID
        /*
         * Ici, on remplace uniquement les champs qui sont renseignés dans le document updatedFields
         *
         */
        getDatabase().getCollection("agences").updateOne(new Document("_id", id), new Document("$set", updatedFields));
    }

}
