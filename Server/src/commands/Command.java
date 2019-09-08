package commands;

import customExceptions.ClientOfflineException;
import customExceptions.ServerException;
import server.FileHandler;

import java.io.IOException;

/**
 * Squelette d'une commande
 */
public abstract class Command {
    /**
     * Execute la commande
     * @param fileHandler Le fileHandler duquel la commande est executee
     * @param argument  l'argument passe a la commande
     * @throws ClientOfflineException
     * @throws ServerException
     */
    public void execute(FileHandler fileHandler, String argument) throws ClientOfflineException, ServerException {
    }

    /**
     *
     * @param fileHandler Le fileHandler duquel la commande est executee
     * @param message Le messaga a transmettre au client
     * @throws ClientOfflineException
     */
    protected void sendMessageToClient(FileHandler fileHandler, String message) throws ClientOfflineException{
        try {
            fileHandler.getOutputStream().writeObject(message);
            fileHandler.getOutputStream().flush();
        } catch (IOException e) {
            throw new ClientOfflineException();
        }
    }
}
