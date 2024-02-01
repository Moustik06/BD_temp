package entityDAO;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public abstract class BaseDAO {
    private static final MongoClient mongoClient;
    private static final MongoDatabase database;

    static {
        // voir ici pour avoir duplication

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
    public ArrayList<Document> getDocuments(Document criteria, MongoCollection<Document> collection) {
        FindIterable<Document> result = collection.find(criteria);
        Iterator<Document> it = result.iterator();
        ArrayList<Document> documents = new ArrayList<>();
        while (it.hasNext()) {
            documents.add(it.next());
        }
        return documents;
    }
    public abstract void createIndexes();
    public abstract ArrayList<Document> findByCriteria(Document criteria);
    // Méthode pour effectuer une jointure entre collections
    public ArrayList<Document> getDocuments(List<Bson> pipeline, String collectionName) {
        MongoCollection<Document> collection = getDatabase().getCollection(collectionName);
        AggregateIterable<Document> result = collection.aggregate(pipeline);
        Iterator<Document> it = result.iterator();
        ArrayList<Document> documents = new ArrayList<>();
        while (it.hasNext()) {
            documents.add(it.next());
        }
        return documents;
    }




}
