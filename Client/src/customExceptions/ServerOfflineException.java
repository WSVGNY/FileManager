package customExceptions;

/**
 * exception lancee si la connexion au serveur est interrompue
 */
public class ServerOfflineException extends Exception{
    public ServerOfflineException() {
        super();
    }
}
