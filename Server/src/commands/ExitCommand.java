package commands;

import customExceptions.ClientOfflineException;
import server.FileHandler;

public class ExitCommand extends Command {
    /**
     * Desactive le FileHandler
     *
     * @param fileHandler Le fileHandler duquel la commande est executee
     * @param argument    aucun
     * @throws ClientOfflineException
     */
    public void execute(FileHandler fileHandler, String argument) throws ClientOfflineException {
        sendMessageToClient(fileHandler, "Disconnected Successfully");
        System.out.println("Client # " + fileHandler.getClientId() + " deconnecte avec succes");
        fileHandler.deactivate();
    }
}
