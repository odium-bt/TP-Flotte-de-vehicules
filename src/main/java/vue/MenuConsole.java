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

    private final FlotteController flotteController;
    private final ReservationController reservationController;


    public MenuConsole(FlotteController flotteController, ReservationController reservationController) {
        this.flotteController = flotteController;
        this.reservationController = reservationController;
    }

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


    // Méthodes appelées dans le switch du menu
    /*-------------------------------------------------*/

    private void afficherFlotte() {
        for (Vehicule vehicule : flotteController.afficherFlotte()) {
            System.out.println(vehicule);
        }
    }

    private void ajouterVehicule() {
        System.out.print("Immatriculation (AA-123-AA) : ");
        String immatriculation = scanner.nextLine().toUpperCase();
        System.out.print("Marque : ");
        String marque = scanner.nextLine();
        System.out.print("Modèle : ");
        String modele = scanner.nextLine();
        System.out.print("Mise en circulation (jj/mm/aaaa) : ");
        String dateSaisie = scanner.nextLine();
        System.out.print("Kilométrage : ");
        int kilometrage = scanner.nextInt();
        scanner.nextLine();
        try {
            LocalDate dateMiseEnCirculation = LocalDate.parse(dateSaisie, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println(flotteController.ajouterVehicule(immatriculation, marque, modele, dateMiseEnCirculation, kilometrage));
        } catch (DateTimeParseException e) {
            System.out.println("Refusé : date invalide, format attendu jj/mm/aaaa.");
        }
    }

    private void retirerVehicule() {
        System.out.print("Immatriculation du véhicule à retirer : ");
        String immatriculation = scanner.nextLine().toUpperCase();
        System.out.print("Confirmer le retrait de " + immatriculation + " ? (o/n) : ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("o")) {
            System.out.println(flotteController.retirerVehicule(immatriculation));
        } else {
            System.out.println("Retrait annulé.");
        }
    }

    private void rechercherVehicule() {
        System.out.print("Immatriculation : ");
        String immatriculation = scanner.nextLine().toUpperCase();
        Vehicule vehicule = flotteController.rechercherVehicule(immatriculation);
        if (vehicule == null) {
            System.out.println("Véhicule introuvable.");
        } else {
            System.out.println(vehicule);
        }
    }

    private void trierFlotte() {
        System.out.println("1. Par marque, puis modèle");
        System.out.println("2. Par date de mise en circulation (plus ancien d'abord)");
        System.out.println("3. Par kilométrage décroissant");
        System.out.print("Tri : ");
        int choixTri = scanner.nextInt();
        scanner.nextLine();
        for (Vehicule vehicule : flotteController.trierFlotte(choixTri)) {
            System.out.println(vehicule);
        }
    }

    private void creerSalarie() {
        System.out.print("Matricule (6 chiffres) : ");
        String matricule = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.println(reservationController.ajouterSalarie(matricule, nom, prenom));
    }

    private void enregistrerDepart() {
        System.out.print("Matricule du salarié : ");
        String matricule = scanner.nextLine();
        System.out.print("Immatriculation : ");
        String immatriculation = scanner.nextLine().toUpperCase();
        System.out.println(reservationController.departReservation(immatriculation, matricule));
    }

    private void enregistrerRetour() {
        System.out.print("Immatriculation : ");
        String immatriculation = scanner.nextLine().toUpperCase();
        System.out.print("Kilométrage au compteur : ");
        int kilometrage = scanner.nextInt();
        scanner.nextLine();
        System.out.println(reservationController.retourReservation(immatriculation, kilometrage));
    }

    private void vehiculesEnDeplacement() {
        for (Vehicule vehicule : reservationController.vehiculeEnDeplacement()) {
            System.out.println(vehicule);
        }
    }

    private void historiqueSalarie() {
        System.out.print("Matricule : ");
        String matricule = scanner.nextLine();
        Reservations<Salarie, Timestamp, Vehicule> historique = reservationController.historiqueSalarie(matricule);
        if (historique == null) {
            System.out.println("Salarié introuvable.");
        } else {
            for (Timestamp dateDepart : historique.getListeReservations().keySet()) {
                System.out.println(dateDepart + " : " + historique.getListeReservations().get(dateDepart));
            }
        }
    }
}
