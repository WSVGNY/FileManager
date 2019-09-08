package commands;

import client.Client;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.IOException;

public class MkdirCommand extends Command{

    /**
     * envoie la commande au serveur et attend la reponse du serveur avant de l'Afficher
     * @param client: L'instance du client qui appelle la commande
     * @param argument: Nom du fichier à créer
     * @throws Exception
     */
    public void execute(Client client, String argument) throws CLIException, ServerOfflineException {
        if(argument == null) {
            throw new CLIException("Erreur: Veuillez spécifier un nom de dossier");
        }
        sendCommandToServer(client, "mkdir " + argument);
        try{
        System.out.println((String) client.getInputStream().readObject());
        } catch (IOException e) {
            throw new ServerOfflineException();
        } catch (ClassNotFoundException e) {
            throw new CLIException("La commande mkdir a echouee");
        }
    }
}
