package commands;

import customExceptions.ServerException;
import server.FileHandler;

import java.io.File;
import java.nio.file.Files;

public class DownloadCommand extends Command {
    /**
     * Envoie au client le fichier a telecharger sur sa machine locale
     *
     * @param fileHandler Le fileHandler duquel la commande est executee
     * @param argument    le nom du fichier
     * @throws ServerException
     */
    public void execute(FileHandler fileHandler, String argument) throws ServerException {
        try {
            String filePath = fileHandler.getCurrentDirectory() + "\\" + argument;
            File localFile = new File(filePath);
            byte[] content = Files.readAllBytes(localFile.toPath());

            fileHandler.getOutputStream().writeObject(content);
            fileHandler.getOutputStream().flush();

        } catch (Exception e) {
            throw new ServerException("La commande a echouee cote serveur");
        }
    }
}
