package entityDAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;


public abstract class BaseDAO {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

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
    // Insérer un objet dans la base de données
    public abstract void insert(Document doc);
    public abstract Document find(int id);
    public abstract void delete(int id);
    public abstract void update(int id, Document updatedDocument);





}
