package controllers;

import modeles.Reservations;
import modeles.Salarie;
import modeles.Vehicule;
import service.RegleGestionException;
import service.ReservationService;
import vue.MenuConsole;

import java.sql.Timestamp;
import java.util.List;

public class ReservationController {

    private ReservationService reservationService;
    private MenuConsole vue;

    public ReservationController(ReservationService reservationService, MenuConsole vue) {

        this.reservationService = reservationService;
        this.vue = vue;
    }

    public void ajouterSalarie() {

        String matricule = vue.saisieTexte("Matricule (6 chiffres) : ");
        String nom = vue.saisieTexte("Nom : ");
        String prenom = vue.saisieTexte("Prénom : ");
        try {
            Salarie salarie = new Salarie(matricule, prenom, nom);
            reservationService.ajouterSalarie(salarie);
            vue.afficherSucces("Le salarié matriculé " + matricule + " a bien été ajouté!");
        } catch (RegleGestionException | IllegalArgumentException e) {
            vue.afficherRefus(e.getMessage());
        }
    }

    public void departReservation() {

        String matricule = vue.saisieTexte("Matricule du salarié : ");
        String immatricule = vue.saisieTexte("Immatriculation : ").toUpperCase();

        try {
            reservationService.enregistrerDepart(immatricule, matricule);
            vue.afficherSucces("La réservation a été validée avec succès");
        } catch (RegleGestionException e) {
            vue.afficherRefus(e.getMessage());
        }
    }

    public void retourReservation() {

        String immatriculation = vue.saisieTexte("Immatriculation : ").toUpperCase();
        int kilometrage = vue.saisieEntier("Kilométrage au compteur : ");

        try {
            reservationService.enregistrerRetour(immatriculation, kilometrage);
            vue.afficherSucces("Le retour a bien été enregistré.");
        } catch (RegleGestionException e) {
            vue.afficherRefus(e.getMessage());
        }
    }

    public Reservations<Salarie, Timestamp, Vehicule> historiqueSalarie(String matricule) {

        return reservationService.historiqueSalarie(matricule);
    }

    public void vehiculeEnDeplacement() {

        vue.afficherVehicules(reservationService.vehiculeEnDeplacement());
    }


    public void historiqueSalarie() {
        String matricule = vue.saisirTexte("Matricule");
        Reservations<Salarie, Timestamp, Vehicule> historique = reservationService.historiqueSalarie(matricule);

        if (historique == null) {
            vue.afficherRefus("salarié introuvable.");
        } else {
            vue.afficherHistorique(historique);
        }
    }
}
