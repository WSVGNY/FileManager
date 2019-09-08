package commands;

import customExceptions.ClientOfflineException;
import server.FileHandler;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerError;

public class LsCommand extends Command {

    /**
     * Liste les dossiers et fichiers contenu dans le dossier actuel du FileHandler
     *
     * @param fileHandler: L'instance du FileHandler qui appelle la commande
     * @param argument:    Aucun
     * @throws ClientOfflineException
     */
    public void execute(FileHandler fileHandler, String argument) throws ClientOfflineException {
        File dir = new File(fileHandler.getCurrentDirectory());
        String[] children = dir.list();

        for (int i = 0; i < children.length; i++) {
            File file = new File(dir + "\\" + children[i]);
            String fileType = null;
            if (file.isDirectory()) {
                fileType = "[Folder] ";
            } else {
                fileType = "[File] ";

            }
            children[i] = fileType + children[i];
        }

        try {
            fileHandler.getOutputStream().writeObject(children);
            fileHandler.getOutputStream().flush();
        } catch (IOException e) {
            throw new ClientOfflineException();
        }
    }
}
