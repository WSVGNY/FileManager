package commands;

import client.Client;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.IOException;

/**
 * instance de la commande cd
 */
public class CdCommand extends Command {

    /**
     * envoie la commande complete (cd + argument) au serveur, attend la reponse du serveur puis l'affiche
     * @param client:   L'instance du client qui appelle la commande
     * @param argument: Le path vers le dossier voulu
     * @throws CLIException si l'argument est null ou s'il y a eu un probleme de serialisation
     * @throws ServerOfflineException si une erreur survient dans le stream
     */
    public void execute(Client client, String argument) throws CLIException, ServerOfflineException {
        if (argument == null) {
            throw new CLIException("Erreur: Veuillez spécifier un path");
        }
        this.sendCommandToServer(client, "cd " + argument);

        try {
            System.out.println((String) client.getInputStream().readObject());
        } catch (IOException e) {
            throw new ServerOfflineException();
        } catch (ClassNotFoundException e) {
            throw new CLIException("La commande cd a echouee");
        }
    }
}
