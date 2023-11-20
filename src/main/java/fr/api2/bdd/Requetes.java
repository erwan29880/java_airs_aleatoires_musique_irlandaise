package fr.api2.bdd;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import fr.api2.bdd.Connect;


/**
 * Requête à la table music
 * retourne 50 chemins d'accès d'airs de danses pseudo-aléatoires
 * d'après une base de données musicale déjà existante
 * 
 * La classification des airs a été effectuée par un programme d'intelligence artificielle
 * sur une base de 30 secondes par piste audio
 * chaque air est transformé en Spectrogramme (Mel)
 * les modèles (des réseaux de neurones à convolutions) sont entraînés sur des données labellisées
 * la classification comprend deux étapes : 
 *     -> classification des airs en chanson/instrumental
 *     -> classification des airs en danse/slow air
 * 
 * Les chemins d'accès entrés en base de données sont relatifs au dossier IRISH/
 * Les ' des chemins d'accès en base de données sont remplacés par @
 * 
 * le dbPath est fourni par la classe services.Api() et est passé à l'instance de Connect 
 * le prefix (des chemins d'accès relatifs) provient de services.Api() est est configuré lors de la première utilisation du programme
 * 
 * Utilisation : 
 *     -> voir services.Api().run()
 */
public class Requetes {

    // suffixe personnalisé par ordinateur
    private String prefix;
    private String dbPath;

    public Requetes(String dbPath, String prefix) {
        this.prefix = prefix;
        this.dbPath = dbPath;
    }

    /**
     * requêter la table musique selon la classification airs de danse
     * @return les chemins d'accès de 50 pistes audio en pseudo aléatoire
     */
    public String[] getAll() {
        String sql = "SELECT path FROM music where vgg>0.5 and cnn<0.5 order by random() limit 50";
        String[] res = new String[50];

        try (Connection conn = new Connect(this.dbPath).connexion();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
                int inc = 0;
                while (rs.next()) {
                    String suffixPath = rs.getString("path").replaceAll("@", "'");
                    res[inc] = this.prefix + suffixPath;
                    inc++;
                }

                return res;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}
