package fr.api2.bdd;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection Sqlite 
 * Le fichier music.db a déjà des données. Il doit exister.
 * le dbPath est récupéré par la classe Config : 
 * services.Api
 *     -> lancement ou récupération de la config : run()
 *         -> passage de dbName à la classe bdd.Requetes()
 *             -> passage de dbName à cette classe
 * 
 * voir classe Requetes
 */
public class Connect {

    private String dbPath;

    public Connect(String dbPath) {
        this.dbPath = dbPath;
    }

    protected Connection connexion() throws SQLException, FileNotFoundException {
        return DriverManager.getConnection("jdbc:sqlite:" + this.dbPath);
    }
}

