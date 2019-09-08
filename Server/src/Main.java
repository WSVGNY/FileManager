import server.FileHandler;
import server.IpAddressHelper;
import server.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    /**
     *  Main du programme
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("+-------------------------------------------+");
        System.out.println("|   Bienvenue dans l'interface du serveur   |");
        System.out.println("+-------------------------------------------+");

        Server server = new Server();
        server.start(getServerAddress(), getServerPort());

        scanner.close();
        int clientNb = 0;
        while (true) {
            FileHandler client = new FileHandler(server.acceptClient(), clientNb++, System.getProperty("user.dir"));
            client.start();
        }
    }

    /**
     * Demande et recupere l'adresse IP du serveur a l'utilisateur
     *
     * @return Une adresse IP valide
     */
    private static String getServerAddress() {
        System.out.print("Entrez l'adresse IP du serveur: ");
        String serverAddress = scanner.next();
        while (!IpAddressHelper.validateServerAddress(serverAddress)) {
            System.out.print("\nEntrez l'adresse IP du serveur:");
            serverAddress = scanner.next();
        }

        return serverAddress;
    }

    /**
     * Demande et recupere le port du serveur a l'utilisateur
     *
     * @return Un port valide entre 5000 et 5050
     */
    private static int getServerPort() {
        System.out.print("Entrez le numero du port du serveur: ");
        String serverPort = scanner.next();
        while (!IpAddressHelper.validateServerPort(serverPort)) {
            System.out.print("\nERREUR: entrez un port valide (entre 5000 et 5050): ");
            serverPort = scanner.next();
        }

        return Integer.parseInt(serverPort);
    }
}
