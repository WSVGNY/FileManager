package commands;

import client.Client;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadCommand extends Command {
    /**
     * Envoie au serveur le fichier a televerser et attend la reponse du serveur avant de l'Afficher
     * @param client   L'instance du client qui appelle la commande
     * @param argument deuxieme partie de la commande
     * @throws CLIException
     * @throws ServerOfflineException
     */
    public void execute(Client client, String argument) throws CLIException, ServerOfflineException {
        if (argument == null) {
            throw new CLIException("Erreur: Veuillez spécifier un nom de fichier");
        }
        String filePath = System.getProperty("user.dir") + "\\" + argument;
        File localFile = new File(filePath);

        if (!localFile.exists()) {
            throw new CLIException("Le fichier " + argument + " n'existe pas dans le repertoire " + System.getProperty("user.dir"));
        }

        this.sendCommandToServer(client, "upload " + argument);

        try {
            byte[] content = Files.readAllBytes(localFile.toPath());
            client.getOutputStream().writeObject(content);
            client.getOutputStream().flush();
            System.out.println((String) client.getInputStream().readObject());

        } catch (Exception e) {
            throw new CLIException("La commande upload a echouee");
        }
    }
}
