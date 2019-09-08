package commands;

import customExceptions.ClientOfflineException;
import customExceptions.ServerException;
import server.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadCommand extends Command {
    /**
     * Sauvegarde le fichier televerse par le client
     *
     * @param fileHandler Le fileHandler duquel la commande est executee
     * @param argument    le nom du fichier
     * @throws ClientOfflineException
     * @throws ServerException
     */
    public void execute(FileHandler fileHandler, String argument) throws ClientOfflineException, ServerException {
        try {
            byte[] retrievedContent = (byte[]) fileHandler.getInputStream().readObject();
            String completePath = fileHandler.getCurrentDirectory() + "\\" + argument;
            File localFile = new File(completePath);
            localFile.getParentFile().mkdirs();
            localFile.createNewFile();

            Files.write(localFile.toPath(), retrievedContent);
            sendMessageToClient(fileHandler, "Le fichier " + argument + " a bien televersé");

        } catch (IOException | ClassNotFoundException e) {
            throw new ServerException("La commande a echouee cote serveur");
        }
    }
}
