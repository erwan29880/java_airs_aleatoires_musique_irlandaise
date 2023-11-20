package fr.api2.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * écrire un fichier playlist musicale
 * voir classe services.Api()
 */
public class ToM3U {
    
    private final String enTete = "#EXTM3U";
    private String playlistFilePath;

    public ToM3U(String playlistFilePath) {
        this.playlistFilePath = playlistFilePath;
    }

    /**
     * @param content les chemins d'accès des fichiers musicaux
     * @throws IOException
     */
    public void writer(ArrayList<String> content) throws IOException{
        File file = new File(this.playlistFilePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        // écrire l'entête pour renseigner que le fichier est un m3u
        bw.write(this.enTete);
        bw.newLine();

        // écrire les chemins d'accès
        for (String s: content) {
            bw.write(s);
            bw.newLine();
        }

        bw.close();
    }
}
