package fr.api2.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.api2.utils.Verifications;
import fr.api2.utils.ToM3U;
import fr.api2.fenetres.Fenetre;
import fr.api2.bdd.Requetes;
import fr.api2.config.Config;
import fr.api2.errors.ConfigError;

/**
 * La classe Api est le service qui orchestre le programme, elle est instanciée dans la classe principale :
 *  - lancer la configuration du programme ou récupérer la configuration du fichier Config.ser
 *  - récupérer les chemins d'accès de la base de données
 *  - vérifier que les fichiers existent
 *  - enregistrer une playlist musicale sur le bureau
 * 
 * Utiliser la classe : 
 * new Api().run();
 * 
 * Réinitialiser la configuration via l'arg config passé dans la classe Main: 
 * new Api().deleteConfig();
 * 
 */
public class Api {

    private Config config;
    private final String confEndName = "Config.ser"; 
    private final String confName = System.getProperty("user.dir") + System.getProperty("file.separator") + this.confEndName;

    public Api() {}

    /**
     * Vérification que le fichier de configuration Config.ser existe
     * Ce fichier conserve l'état de la configuration initiale.
     * Si le fichier n'existe pas, la configuration du programme est lancée et le fichier Config.ser créé.
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws ConfigError
     */
    private void getConfiguration() throws FileNotFoundException, ClassNotFoundException, IOException, ConfigError {
        File conf = new File(this.confName);

        if (conf.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(confName));
            this.config = (Config) objectInputStream.readObject();
            objectInputStream.close();
        } else {
            this.config = new Config();
            this.config.run();
            FileOutputStream fileOutputStream = new FileOutputStream(this.confEndName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.config);
            objectOutputStream.close();
        }
    }

    /**
     * gérer la configuration
     * récupérer les données de la db
     * vérifier que les fichiers existent
     * écrire les chemins d'accès dans un fichier playlist m3u
     * 
     * En cas d'erreur, ouvre une fenêtre Swing avec affichage de l'erreur
     */
    public void run() {
        try {
            this.getConfiguration();
            // 50 paths aléatoires
            String[] data = new Requetes(this.config.getDbPath(), this.config.getIrishMusicDirectory()).getAll();

            // vérification que les fichiers existent
            Verifications v = new Verifications();
            ArrayList<String> pathes = v.run(data);
            
            // mise en playlist musicale sur le bureau
            ToM3U writer = new ToM3U(this.config.getPlaylistFilePath());
            writer.writer(pathes);
        
        } catch (ConfigError e) {
            new Fenetre(e.getMessage());
            this.deleteConfig();
        } catch (ClassNotFoundException e) {
            new Fenetre(e.getMessage());
            this.deleteConfig();
        } catch (FileNotFoundException e) {
            new Fenetre(e.getMessage());
            this.deleteConfig();
        } catch (IOException e) {
            new Fenetre(e.getMessage());
            this.deleteConfig();
        } catch (Exception e) {
            new Fenetre(e.getMessage());
            this.deleteConfig();
        }
    }

    /**
     * réinitialiser la configuration
     * méthode utilisée par la classe principale par passage de l'arg config
     */
    public void deleteConfig() {
        File file = new File(this.confName);
            if (file.exists()) {
                file.delete();
            }
    }
}
