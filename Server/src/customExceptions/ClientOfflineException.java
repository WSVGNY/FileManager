package customExceptions;

/**
 * exception lancee si la connexion au client est interrompue
 */
public class ClientOfflineException extends Exception{
    public ClientOfflineException() {
        super();
    }
}
