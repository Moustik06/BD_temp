package entityDAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;


public abstract class BaseDAO {
    private static final MongoClient mongoClient;
    private static final MongoDatabase database;

    static {
        // Initialisation de la connexion MongoDB
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("projet-BD");
    }

    // Méthode pour obtenir la base de données
    protected static MongoDatabase getDatabase() {
        return database;
    }

    // Méthode pour fermer la connexion à la base de données
    public static void closeConnection() {
        mongoClient.close();
    }
    public void insert(Document doc,String collectionName) {
        MongoCollection<Document> collection = getDatabase().getCollection(collectionName);

        try {
            collection.insertOne(doc);
            System.out.println("insérée avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }
    // Insérer un objet dans la base de données
    public Document find(int id, String collectionName) {
        return getDatabase().getCollection(collectionName).find(new Document("_id", id)).first();
    }

    public ArrayList<Document> findAll(String collectionName) {
        ArrayList<Document> documents = new ArrayList<>();
        getDatabase().getCollection(collectionName).find().into(documents);
        return documents;
    }
    public void delete(int id, String collectionName) {
        // Utilisez votre connexion à la base de données pour supprimer l'agence par ID
        getDatabase().getCollection(collectionName).deleteOne(new Document("_id", id));
    }
    public void dropCollection(String collectionName) {
        getDatabase().getCollection(collectionName).drop();
    }

    public static void dropDatabase(String collectionName) {
        getDatabase().drop();
    }
    public void update(int id, Document updatedFields, String collectionName) {
        // Utilisez votre connexion à la base de données pour mettre à jour l'agence par ID
        /*
         * Ici, on remplace uniquement les champs qui sont renseignés dans le document updatedFields
         *
         */
        getDatabase().getCollection(collectionName).updateOne(new Document("_id", id), new Document("$set", updatedFields));
    }
    public abstract void createIndexes();




}
