package server;

import commands.*;
import customExceptions.ClientOfflineException;
import customExceptions.ServerException;

import java.io.*;
import java.util.*;
import java.net.Socket;

/**
 * Gestionnaire de commandes un utilisateur connecte.
 *
 * Un nouveau FileHandler est cree pour chaque nouveau client
 */
public class FileHandler extends Thread {

    private Socket socket;
    private int clientId;
    private boolean isActive;
    private String currentDirectory;
    private HashMap<String, Command> availableCommandMap;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    /**
     *  Constructeur de la classe FileHanler
     *
     * @param client Le socket du client
     * @param clientId L'identifiant unique du client
     * @param currentDirectory Le path du dossier actuel du client
     */
    public FileHandler(Socket client, int clientId, String currentDirectory) {
        socket = client;
        isActive = true;
        this.clientId = clientId;
        this.currentDirectory = currentDirectory;

        initializeAvailableCommandMap();
        initializeIOStreams();
        System.out.println("Nouvelle connexion etablie avec le client# " + clientId + " et le socket: " + socket);
    }

    /**
     * Initialise les commandes possibles par le fileHandler
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
     * Initialise les ObjectStreams pour communiquer avec le client
     */
    private void initializeIOStreams() {
        try {
            inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Boucle de vie du FileHandler
     *
     * run est appellee lorqu'on appelle FileHandler.start()
     */
    public void run() {
        sendMessageToClient("Connexion au serveur réussie");

        while (isActive) {
            try {
                String input = retrieveCommandFromClient();
                handleCommand(input);
            } catch (ServerException e) {
                System.out.println(e.getMessage());
                sendMessageToClient(e.getMessage());
            } catch (ClientOfflineException e) {
                System.out.println("Client # " + clientId + " deconnecte de facon innattendue");
                deactivate();
            }
        }

        close();
    }


    /**
     * Traite a commande recue du client et execute la bonne operation
     * @param input La commande brute recue du client
     * @throws ClientOfflineException
     * @throws ServerException
     */
    private void handleCommand(String input) throws ClientOfflineException, ServerException {
        String[] params = input.split(" ");
        Command command = this.availableCommandMap.get(params[0]);
        String argument = (params.length <= 1) ? null : params[1];
        command.execute(this, argument);
    }

    /**
     * Desactive le FileHandler
     */
    public void deactivate() {
        isActive = false;
    }

    /**
     * Deconnecte le FileHandler et ferme les ObjectStreams
     */
    public void close() {
        try {
            socket.close();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Envoie un message au client
     * @param message Le message a envoyer au client
     */
    private void sendMessageToClient(String message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi du message au client");
        }
    }

    /**
     *
     * @return La commande recue du client
     * @throws ClientOfflineException
     */
    private String retrieveCommandFromClient() throws ClientOfflineException {
        try {
            String input = (String) inputStream.readObject();
            System.out.println("Received [ " + input + " ] from client [ " + clientId + " ]");
            return input;
        } catch (IOException | ClassNotFoundException e) {
            throw new ClientOfflineException();
        }
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public int getClientId() {
        return clientId;
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    /**
     * Change le dossier actuel du FileHandler
     * @param newDirectory le nouveau path vers le dossier actuel
     */
    public void changeCurrentDirectory(String newDirectory) {
        currentDirectory = newDirectory;
    }
}
