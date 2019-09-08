package client;

public class IpAddressHelper {

    /**
     * verifier la validité de l'adresse entrée par l'usager, selon les criteres mentinonnés dans l'énoncé (format etc)
     * @return vrai/faux si l'adresse est valide
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
     * verifier la validité du port entrée par l'usager, selon les criteres mentinonnés dans l'énoncé (format etc)
     * @return vrai/faux si le port est valide
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
