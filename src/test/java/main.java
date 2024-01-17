import entity.*;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonReader;
import reader.Reader;

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
        agences = reader.assuranceReader();

        for (int i = 0; i < 1000; i++) {
            System.out.println(agences[i]);
        }
    }
}
