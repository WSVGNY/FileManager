package customExceptions;

/**
 * exception lancee si une operation rencontre un probleme
 */
public class CLIException extends Exception{
    public CLIException(String message) {
        super(message);
    }
}
