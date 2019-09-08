package commands;

import client.Client;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.IOException;

public class LsCommand extends Command{

    /**
     * envoie la commande au serveur et affiche la liste des fichiers recus par celui-ci
     * @param client: L'instance du client qui appelle la commande
     * @param argument: Aucun
     * @throws Exception
     */
    public void execute(Client client, String argument) throws CLIException, ServerOfflineException {
        if(argument != null) {
            throw new CLIException("Erreur: Trop d'arguments");
        }

        sendCommandToServer(client, "ls");

        try {
            String[] childs = (String[]) client.getInputStream().readObject();
            for (String child : childs) {
                System.out.println(child);
            }
        } catch (IOException e) {
            throw new ServerOfflineException();
        } catch (ClassNotFoundException e) {
            throw new CLIException("La commande ls a echouee");
        }
    }
}
