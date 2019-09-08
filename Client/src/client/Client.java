package client;

import commands.*;
import customExceptions.CLIException;
import customExceptions.ServerOfflineException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * classe qui represente l'instance d'un client qui est connecté au serveur
 */
public class Client {

    private Socket socket;
    private HashMap<String, Command> availableCommandMap;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean isActive;

    /**
     * constructeur de la classe qui est Active par defaut
     */
    public Client() {
        initializeAvailableCommandMap();
        isActive = true;
    }

    /**
     * initialise les differentes commandes possibles et les place dans une HashMap
     */
    private void initializeAvailableCommandMap() {
        availableCommandMap = new HashMap<String, Command>();
        availableCommandMap.put("cd", new CdCommand());
        availableCommandMap.put("ls", new LsCommand());
        availableCommandMap.put("mkdir", new MkdirCommand());
        availableCommandMap.put("upload", new UploadCommand());
        availableCommandMap.put("download", new DownloadCommand());
        availableCommandMap.put("exit", new ExitCommand());
    }

    /**
     * Tente de se connecter au serveur et initialise les streams avec celui-ci
     * et attend que le serveur lui envoie les messages de confirmation
     * @return vrai ou faux, si le client a reussi a se connecter au serveur
     */
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            initializeIOStreams();
            displayConnectionConfirmationFromServer();
            return true;
        } catch (Exception e) {
            System.out.println("Connection impossible avec le serveur. Veuillez reessayer");
            return false;
        }
    }

    /**
     * initialise les streams en entree et en sortie pour communiquer avec le serveur
     */
    private void initializeIOStreams() throws Exception {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * attend de recevoir un message de confirmation du serveur
     * lance une exception attrapee plus haut si une erreur survient
     */
    private void displayConnectionConfirmationFromServer() throws Exception {
        System.out.println((String) inputStream.readObject());
    }

    /**
     * s'occupe de gerer la commande en la separant en 2,
     * va chercher la commande euivalent dans la hashMap et l'execute
     * @param input: la commande complete de l'usager (ex cd .. ou upload allo.txt)
     *
     * les exceptions de logique sont affichees à l'utilisateur et il peut entrer d'autres commandes
     * si le serveur est deconnecte durant lexecution dune commande, le client se deconnecte lui aussi
     */
    public void handleCommand(String input) {
        try {
            String[] params = input.split(" ");
            if (params.length == 0) {
                throw new CLIException("Erreur: Veuillez entrer une commande");
            }

            Command command = availableCommandMap.get(params[0]);
            String argument = (params.length <= 1) ? null : params[1];

            if (command == null) {
                throw new CLIException("Erreur: La commande " + params[0] + " est invalide");
            }

            if (params.length > 2) {
                throw new CLIException("Erreur: Trop d'arguments");
            }

            command.execute(this, argument);

        } catch (ServerOfflineException e) {
            System.out.println("Oh oh, la connexion au serveur a ete interrompue");
            disconnect();
        } catch (CLIException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * ferme le socket du client et cest streams de communication avec le serveur
     * Le client n'est plus actif
     * si une errur survient on ferme lapplication car le but est de sortir de toute facon
     */
    public void disconnect() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            isActive = false;
        } catch (IOException e) {
            System.exit(0); // doit sortir anyways
        }
    }

    /**
     * retourne le parametre isActive si le client est toujours active
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * retourne le stream de communication entrant venant du serveur
     */
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    /**
     * retourne le stream de communication sortant vers le serveur
     */
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
