package commands;

import client.Client;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.IOException;

/**
 * classe abstraite Commande: chaque commande peut etre éxécutée.
 * elles peuvent aussi envoyer un message au serveur
 */
public abstract class Command {

    /**
     * envoie une String au serveur
     *
     * @param client   L'instance du client qui appelle la commande
     * @param argument string a envoyé au serveur
     * @throws ServerOfflineException si le serveur est fermé
     */
    protected void sendCommandToServer(Client client, String argument) throws ServerOfflineException {
        try {
            client.getOutputStream().writeObject(argument);
            client.getOutputStream().flush();
        } catch (IOException e) {
            throw new ServerOfflineException();
        }
    }

    /**
     * execute la commande: le code de chaque commande chane en fonction de sa nature
     * @param client   L'instance du client qui appelle la commande
     * @param argument deuxieme partie de la commande
     * @throws CLIException           pour une erreur de logique dans le code
     * @throws ServerOfflineException si le serveur est fermé
     */
    public abstract void execute(Client client, String argument) throws CLIException, ServerOfflineException;
}
