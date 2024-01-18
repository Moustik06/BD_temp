import entity.*;
import entityDAO.AgenceDAO;
import entityDAO.BaseDAO;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonReader;
import reader.Reader;

import javax.print.Doc;
import java.io.Console;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Document[] agences = new Document[1000];
        Reader reader = new Reader();
        agences = reader.agenceReader();

        AgenceDAO agenceDAO = new AgenceDAO();
        Document updatedDocument = new Document();
        updatedDocument.append("nom", "Agence 1");
        updatedDocument.append("adresse", "1 rue de la paix");
        updatedDocument.append("telephone", "0783732492");

        agenceDAO.update(3, updatedDocument);
        System.out.println("closing connection");
        BaseDAO.closeConnection();
    }
}
