package fr.api2.fenetres;
import javax.swing.JFileChooser;

import fr.api2.errors.ConfigError;


/**
 * File Chooser pour que l'utilisateur renseigne le dossier IRISH
 * Voir config.Config()
 */
public class ConfigWindow {
    
    public ConfigWindow() {}

    public String getIrishPath() throws ConfigError {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        chooser.setDialogTitle("choisir le chemin d'accès au dossier IRISH");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
            return chooser.getSelectedFile().getPath();
        } else {
            throw new ConfigError("Aucun dossier sélectionné");
        }
    }

}
