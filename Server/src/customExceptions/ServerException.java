package customExceptions;

/**
 * exception lancee si une operation rencontre un probleme
 */
public class ServerException extends Exception{
    public ServerException(String message) {
        super(message);
    }
}
