package fr.api2.utils;

import java.io.File;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * vérification que les fichiers existent en fonction de leur chemin d'accès
 * voir classe services.Api()
 */
public class Verifications {

    public Verifications() {}

    private ArrayList<String> chechPathExists(String[] pathes) {
        ArrayList<String> existingPathes = new ArrayList<String>();
        int l = pathes.length;

        for (int i = 0; i < l ; i++) {
            File f = new File(pathes[i]);
            Path path = Paths.get(pathes[i]);
            if (!Files.isDirectory(path) && f.exists()) {
                existingPathes.add(pathes[i]);
            }
        }

        return existingPathes;
    }


    public ArrayList<String> run(String[] pathes) {
        return this.chechPathExists(pathes);
    }
}
