package commands;

import customExceptions.ClientOfflineException;
import server.FileHandler;

import java.io.File;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;


public class CdCommand extends Command {
    /**
     * Change le dpssier actuel du FileHandler pour celui saisi par l'utilisateur
     *
     * @param fileHandler: L'instance du fileHandler qui appelle la commande
     * @param argument: Le path vers le dossier voulu
     * @throws Exception
     */
    public void execute(FileHandler fileHandler, String argument) throws ClientOfflineException {
        String pathDirectory = parseDirectory(fileHandler.getCurrentDirectory(), argument);
        File dir = new File(pathDirectory);

        String message = null;

        if (dir.isDirectory() == true) {
            fileHandler.changeCurrentDirectory(dir.getAbsolutePath());
            message = "Vous etes dans le dossier " + fileHandler.getCurrentDirectory();

        } else {
            message = (pathDirectory + " is not a directory.");
        }
        this.sendMessageToClient(fileHandler, message);
    }

    /**
     * Change le path relatif entre par l'utilisateur en un path absolu
     *
     * @param currentDirectory Le path du dossier actuel
     * @param pathDirectory Le path relatif du nouveau dossier
     * @return le path absolu du nouveau dossier
     */
    private String parseDirectory(String currentDirectory, String pathDirectory) {
        pathDirectory = normalizePath(pathDirectory);
        String regexSlashAndBackslash = "\\\\|\\/";
        String[] subDirectories = pathDirectory.split(regexSlashAndBackslash);
        File rootFolder = new File(currentDirectory);
        pathDirectory = "";
        for (String dir : subDirectories) {
            if (dir.equals("..")) {
                rootFolder = new File(rootFolder.getParent());
            } else {
                pathDirectory += dir + "\\";
            }
        }
        return rootFolder.getPath().concat((pathDirectory.length() != 0) ? ("\\" + pathDirectory) : "");
    }

    /**
     * Normalise le path entre par l'utilisateur
     *
     * @param path le path brut entree par l'utilisateur
     * @return le path normalise
     */
    private String normalizePath(String path) {
        boolean isAbsolute = new File(path).isAbsolute();
        String SEPARATOR = "/";

        List<String> parts = new ArrayList<>();
        for (String part : path.split(SEPARATOR)) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }
            if (part.equals("..")) {
                if (parts.isEmpty()) {
                    if (isAbsolute) {
                        continue;
                    }
                } else {
                    if (!parts.get(parts.size() - 1).equals("..")) {
                        parts.remove(parts.size() - 1);
                        continue;
                    }
                }
            }
            parts.add(part);
        }

        String prefix = isAbsolute ? SEPARATOR : "";
        return prefix + String.join(SEPARATOR, parts);
    }
}
