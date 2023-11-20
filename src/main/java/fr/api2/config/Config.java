package fr.api2.config;

import java.io.Serializable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.api2.fenetres.ConfigWindow;
import fr.api2.errors.ConfigError;


/**
 * configuration initiale : installation du programme si nécessaire
 * voir classes services.Api(), Main
 * 
 * Cette classe est sauvegardée dans le fichier Config.ser lors de la première utilisation du programme
 * Il est possible de ré-initialiser la configuration :
 *     -> java -jar api2.jar config
 * 
 * L'OS est vérifié, et change la configuration entre linux et windows
 * La configuration : 
 *     -> récupère le dossier du programme
 *     -> vérifie que le fichier sqlite music.db existe
 *     -> créé le chemin d'accès au fichier playlist musicale sur le bureau
 *     -> ouvre une fenêtre pour récupérer le chemin du dossier IRISH
 */
public class Config implements Serializable {
    
    private String osName;
    private String userHome;
    private String programDir;
    private String fileSeparator;
    private String irishMusicDirectory;
    private String folderM3uPath;
    private String dbPath;
    private String playlistFilePath;
    private final String dbName = "music.db";

    public Config() {}

    /**
     * méthode pour lancer la configuration
     * le getter de cette méthode est la méthode run()
     * @throws ConfigError
     */
    private void allSetters() throws ConfigError{
        this.osName = System.getProperty("os.name").toLowerCase();
        this.userHome = System.getProperty("user.home");
        this.programDir = System.getProperty("user.dir");
        this.fileSeparator = System.getProperty("file.separator");

        this.osVerification();
        this.dbVerification();
        this.m3uVerification();
        this.musicFolder();
    }


    /**
     * vérifie si l'OS est windows ou linux
     * vérifie si l'OS est en anglais ou français
     *     -> en fonction, le bureau est soit Desktop, soit Bureau
     * @throws ConfigError
     */
    private void osVerification() throws ConfigError {
        if (this.osName.indexOf("windows") != -1) {
            Path path = Paths.get(userHome + this.fileSeparator + "Desktop");
            this.folderM3uPath = Files.isDirectory(path) ? "Desktop" : "Bureau";
        } else if (this.osName.indexOf("linux") != -1) {
            Path path = Paths.get(userHome + this.fileSeparator + "Desktop");
            this.folderM3uPath = Files.isDirectory(path) ? "Desktop" : "Bureau";
        } else {
            throw new ConfigError("le système d'exploitation n'est pas reconnu");
        }
    }

    /**
     * vérifie que le fichier sqlite existe
     * ce fichier doit être à la racine du projet, et au même niveau que le jar généré
     * @throws ConfigError
     */
    private void dbVerification() throws ConfigError{
        this.dbPath = new StringBuilder()
                        .append(this.programDir)
                        .append(this.fileSeparator)
                        .append(this.dbName)
                        .toString();
        File db = new File(this.dbPath);
        if (!db.exists()) {
            throw new ConfigError("la base de données sqlite n'a pas pu être trouvée");
        }
    }

    /**
     * génère le chemin d'accès du fichier playlist musicale playlist.m3u
     * @throws ConfigError
     */
    private void m3uVerification() throws ConfigError{
        String desktopPath = new StringBuilder()
                        .append(this.userHome)
                        .append(this.fileSeparator)
                        .append(this.folderM3uPath)
                        .append(this.fileSeparator)
                        .toString();
        Path path = Paths.get(desktopPath);
        if (!Files.isDirectory(path)) {
            throw new ConfigError("le chemin d'enregistrement du fichier n'a pas pu être trouvé");
        }
        this.playlistFilePath = desktopPath + this.fileSeparator + "playlist.m3u";
    }

    /**
     * lance une fenêtre Swing pour que l'utilisateur renseigne l'emplacement du dossier IRISH
     * @throws ConfigError
     */
    private void musicFolder() throws ConfigError{
        ConfigWindow w = new ConfigWindow();
        String irishPath = w.getIrishPath();

        // vérification que l'utilisateur a bien renseigné le dossier IRISH
        int endIndex = irishPath.indexOf("IRISH");
        if (endIndex == -1) {
            throw new ConfigError("Le chemin d'accès ne correspond au dossier demandé");
        }
        this.irishMusicDirectory = irishPath.substring(0, endIndex);
    }


    // getter pour allSetters()
    public void run() throws ConfigError{
        this.allSetters();
    }

    // getters ------------------------------

    public String getIrishMusicDirectory() {
        return this.irishMusicDirectory;
    }

    public String getPlaylistFilePath() {
        return this.playlistFilePath;
    }

    public String getDbPath() {
        return this.dbPath;
    }

    public String playlistFilePath() {
        return this.playlistFilePath;
    }

    public void setIrishMusicDirectory(String directory) {
        this.irishMusicDirectory = directory;
    }

    

    
}
