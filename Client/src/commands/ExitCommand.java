package commands;

import client.Client;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.IOException;

public class ExitCommand extends Command {

    /**
     * envoie la commande au serveur pour dire quil veut se deconnecter
     * attend la confirmation du serveur
     * se deconnecte lui mem
     * @param client   L'instance du client qui appelle la commande
     * @param argument null dans ce cas car aucun arguemtn avec exit
     * @throws ServerOfflineException si le serveur est ferme, le client sera deconnecte dans la gestion de lexception dans la classe client
     * @throws CLIException
     */
    public void execute(Client client, String argument) throws ServerOfflineException, CLIException {
        sendCommandToServer(client, "exit");

        try {
            System.out.println((String) client.getInputStream().readObject());
        } catch (IOException e) {
            throw new ServerOfflineException();
        } catch (ClassNotFoundException e) {
            throw new CLIException("La commande exit a echouee");
        }

        client.disconnect();
    }
}
