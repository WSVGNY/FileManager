package commands;

import client.Client;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.File;
import java.nio.file.Files;

/**
 * instance de la commande download
 */
public class DownloadCommand extends Command {

    /**
     * envoie la commande complete (download + argument) au serveur,
     * attend la reponse du serveur puis ecrit le contenu recu dans un nouveau fichier
     * @param client:   L'instance du client qui appelle la commande
     * @param argument: Le path vers le dossier voulu
     * @throws CLIException si l'argument est null ou s'il y a eu un probleme de serialisation
     * @throws ServerOfflineException si une erreur survient dans le stream
     */
    public void execute(Client client, String argument) throws CLIException, ServerOfflineException {
        if (argument == null) {
            throw new CLIException("Erreur: Veuillez spécifier un nom de fichier");
        }

        this.sendCommandToServer(client, "download " + argument);

        try {
            byte[] retrievedContent = (byte[]) client.getInputStream().readObject();
            String completePath = System.getProperty("user.dir") + "\\" + argument;
            File localFile = new File(completePath);
            localFile.getParentFile().mkdirs();
            localFile.createNewFile();
            Files.write(localFile.toPath(), retrievedContent);
            System.out.println("Le fichier " + argument + " a bien ete telecharge");
        } catch (Exception e) {
            throw new CLIException("La commande upload a echouee");
        }
    }
}
