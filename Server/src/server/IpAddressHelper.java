package server;

public class IpAddressHelper {

    /**
     * Valide si l'adresse IP entree par l'utilisateur est valide
     * @param address l'adresse saisie par l'utilisateur
     * @return true/false si l'adresse est valide ou non
     */
    public static boolean validateServerAddress(String address) {
        try {
            if (address == null || address.isEmpty()) {
                System.out.println("\nERREUR: l'adresse ne peut etre vide ");

                return false;
            }

            String[] addressParts = address.split("\\.");
            if (addressParts.length != 4) {
                System.out.println("\nERREUR: Format de l'adresse invalide (4 octets obligatoires) ");

                return false;
            }

            for (String part : addressParts) {
                int byteValue = Integer.parseInt(part);

                if ((byteValue < 0) || (byteValue > 255)) {
                    System.out.println("\nERREUR: Format de l'adresse invalide  (octet invalide) ");

                    return false;
                }
            }

            if (address.endsWith(".")) {
                System.out.println("\nERREUR: L'adresse ne peut se terminer par un point ");

                return false;
            }

        } catch (NumberFormatException nfe) {
            System.out.println("\nERREUR: N'entrez que des chiffres et des points pour une adresse");

            return false;
        }

        return true;
    }

    /**
     * Valide si le port entre par l'utilisateur est valide
     * @param port le port saisi par l'utilisateur
     * @return true/false si le port est valide ou non
     */
    public static boolean validateServerPort(String port) {
        try {
            if (port == null || port.isEmpty()) {
                return false;
            }

            int portValue = Integer.parseInt(port);

            if ((portValue < 5000) || (portValue > 5050)) {
                return false;
            }

        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}
