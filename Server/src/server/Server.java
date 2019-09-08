package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.*;

public class Server {

    private ServerSocket socket;

    public Server() {
    }

    /**
     *
     * @param host l'adresse ip du serveur
     * @param port le port du serveur
     */
    public void start(String host, int port) {
        try {
            InetAddress localIp = InetAddress.getByName(host);
            socket = new ServerSocket();
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(localIp, port));
            System.out.println("A l'ecoute du port: " + port);

        } catch (IOException e) {
            System.err.println("Erreur: Ecoute impossible du port: " + port);
            System.err.println(e);
            System.exit(1);
        }
    }

    /**
     * Accepte une nouvelle connexion avec un client
     * @return le socket du client connecte
     */
    public Socket acceptClient() {
        try {
            return socket.accept();
        } catch (Exception e) {
            return null;
        }
    }
}
