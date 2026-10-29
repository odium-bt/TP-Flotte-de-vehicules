package vue;

import java.util.Scanner;

public class MenuConsole {

    public static Scanner scanner = new Scanner(System.in);

    public void afficherMenu() {
        System.out.println("=============== Menu Principal ===============");
        System.out.println("VÉHICULES");
        System.out.println("1. Afficher la flotte");
        System.out.println("2. Ajouter un véhicule");
        System.out.println("3. Retirer un véhicule");
        System.out.println("4. Rechercher par immatriculation");
        System.out.println("5. Trier la flotte");
        System.out.println("SALARIÉS ET RÉSERVATIONS");
        System.out.println("6. Créer un salarié");
        System.out.println("7. Enregistrer un départ");
        System.out.println("8. Enregistrer un retour");
        System.out.println("9. Véhicules en déplacement");
        System.out.println("10. Historique d'un salarié");
        System.out.println("ÉTATS ET EXPORTS");
        System.out.println("11. Générer l'état PDF et l'envoyer par courriel");
        System.out.println("12. Exporter salariés et réservations");
        System.out.println("13. Lire un fichier exporté");
        System.out.println("0. Quitter");
        System.out.println("===============================================");
        System.out.print("Votre choix : ");
    }

    public void selection() {

        int choix = -1;

        while (choix != 0) {

            afficherMenu();
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    // TODO
                    break;
                case 2:
                    // TODO
                    break;
                case 3:
                    // TODO
                    break;
                case 4:
                    // TODO
                    break;
                case 5:
                    // TODO
                    break;
                case 6:
                    // TODO
                    break;
                case 7:
                    // TODO
                    break;
                case 8:
                    // TODO
                    break;
                case 9:
                    // TODO
                    break;
                case 10:
                    // TODO
                    break;
                case 11:
                    // TODO
                    break;
                case 12:
                    // TODO
                    break;
                case 13:
                    // TODO
                    break;
                case 0:
                    // TODO
                    break;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }
}
