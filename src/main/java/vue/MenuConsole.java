package vue;

import controllers.FlotteController;
import controllers.ReservationController;
import modeles.Reservations;
import modeles.Salarie;
import modeles.Vehicule;
import tools.jackson.databind.ext.javatime.DateTimeParseException;

import javax.lang.model.type.ArrayType;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuConsole {

    public final Scanner scanner = new Scanner(System.in);

    public int afficherMenuEtLireChoix() {
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

        return saisieEntier("Votre choix");
    }

    public String saisieTexte(String libelle) {

        System.out.print(libelle + " : ");
        return scanner.nextLine().trim();
    }

    public int saisieEntier(String libelle) {

        while (true) {
            try {
                return Integer.parseInt(saisieTexte(libelle));
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre.");
            }
        }
    }

    public void afficherVehicules(List<Vehicule> vehicules) {
        for (Vehicule vehicule : vehicules) {
            System.out.println(vehicule);
        }
    }

    public void afficherHistorique(Reservations<Salarie, Timestamp, Vehicule> historique) {
        System.out.println("Historique du salarié : " + historique.getSalarie());
        for (Timestamp dateDepart : historique.getListeReservations().keySet()) {
            System.out.println(dateDepart + " : " + historique.getListeReservations().get(dateDepart));
        }
    }

    public void afficherSucces(String message) {
        System.out.println(message);
    }

    public void afficherRefus(String message) {
        System.out.println("Refusé : " + message);
    }

}
