package fr.api2;

import fr.api2.services.Api;
import java.util.Arrays;

/**
 * Main Class
 * args config : réinitiliser la configuration 
 * 
 * Lors de l'export en jar, il faut également placer le fichier music.db dans le même dossier que le jar
 * Les autres fichiers sont inutiles. Le fichier Config.ser est créé lors de la première utilisation, 
 * La configuration peut être réinitialisée par java -jar api2.jar config
 * 
 * Voir la classe Config pour la configuration.
 * 
 * Le programme va chercher dans la base de données sqlite music.db 50 chemins d'accès pseudo-aléatoires 
 * d'airs de danses de musique irlandaise. Le programme vérifie que les chemins d'accès existent, et générent 
 * un fichier playlist m3u placé sur le bureau.
 * 
 * La base de données musicale est privée, il est indispensable de l'avoir pour utiliser le programme.
 * La base de données musicale ne peut pas être obtenue, à moins d'avoir acquis au préalable tous les enregistrements
 * de façon légale.
 * 
 */
public class Main {
    public static void main(String[] args) {
        // instancier le service
        Api api = new Api();

        // args config : delete Config.ser et reinitialiser la configuration
        if (args.length != 0) {
            if (Arrays.asList(args).contains("config")) {
                api.deleteConfig();    
            }
        }

        // lancer le programme
        api.run();
    }
}