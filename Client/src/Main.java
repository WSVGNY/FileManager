
import client.Client;
import client.IpAddressHelper;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    /**
     * Demande a l'usager les infos de connexions, tente de se connecter
     * et demander des commandes a l'usager tant quil na pas quitté
     */
    public static void main(String[] args) {
        System.out.println("+---------------------------------------+");
        System.out.println("|   Bienvenue dans l'interface client   |");
        System.out.println("+---------------------------------------+");

        Client client = new Client();
        boolean isConnected;
        do {
            isConnected = client.connect(getServerAddress(), getServerPort());
        } while (!isConnected);

        while (client.getIsActive()) {
            String input = getUserCommand();
            client.handleCommand(input);
        }

        scanner.close();
    }

    /**
     * demande a l'usager une commande tant qu'elle n'est pas vide
     */
    private static String getUserCommand() {
        String command = null;
        while (command == null || command.length() == 0) {
            System.out.print(">");
            command = scanner.nextLine();
        }
        return command;
    }

    /**
     * demande a l'usager l'Adresse du serveur, tant qu'elle n'est pas valide
     */
    private static String getServerAddress() {
        System.out.print("Entrez l'adresse IP du serveur: ");
        String serverAddress = scanner.next();
        while (!IpAddressHelper.validateServerAddress(serverAddress)) {
            System.out.print("\nEntrez l'adresse IP du serveur: ");
            serverAddress = scanner.next();
        }

        return serverAddress;
    }

    /**
     * demande a l'usager le port du serveur, tant qu'il n'est pas valide
     * @return
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
