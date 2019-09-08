package commands;

import customExceptions.ClientOfflineException;
import server.FileHandler;

import java.io.File;
import java.rmi.ServerError;

public class MkdirCommand extends Command {

    /**
     * Cree un nouveau dossier dans le repertoire actuel du FileHandler
     *
     * @param fileHandler: L'instance du fileHandler qui appelle la commande
     * @param argument:    Nom du dossier a creer
     * @throws Exception
     */
    public void execute(FileHandler fileHandler, String argument) throws ClientOfflineException {
        String cwd = fileHandler.getCurrentDirectory() + "\\" + argument;
        System.out.println(cwd);
        File f = new File(cwd);

        boolean directoryCreated = f.mkdir();
        String message = null;
        if (directoryCreated) {
            message = "Le dossier " + argument + " a ete cree.";
        } else {
            message = "une erreur est survenue au moment de cr�er le dossier " + argument;
        }

        this.sendMessageToClient(fileHandler, message);
    }
}
