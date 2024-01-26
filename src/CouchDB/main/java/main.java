package main.java;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Session;

class main {
    public static void main(String[] args) {
        Session s = new Session("localhost", 5984);
        Database db = s.getDatabase("projet-DB");

    }
}