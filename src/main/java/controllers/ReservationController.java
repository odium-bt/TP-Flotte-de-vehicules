package controllers;

import modeles.Reservations;
import modeles.Salarie;
import modeles.Vehicule;
import service.RegleGestionException;
import service.ReservationService;

import java.sql.Timestamp;
import java.util.List;

public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {

        this.reservationService = reservationService;
    }

    public String ajouterSalarie(String matricule, String prenom, String nom) {

        try {

            Salarie salarie = new Salarie(matricule, prenom, nom);
            // TODO Mettre le vrai nom de méthode et lever
            // exception si le matricule existe déjà
            //
            reservationService.ajouterSalarie(salarie);
            return "Le salarié matriculé " + matricule + " a bien été ajouté!";

        } catch (RegleGestionException e) {
            return "Refusé : " + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "Refusé : " + e.getMessage();
        }
    }

    public String departReservation(String immatricule, String matricule) {

        try {
            reservationService.enregistrerDepart(immatricule, matricule);
            return "Le départ a été enregistré";
        } catch (RegleGestionException e) {
            return "Refusé : " + e.getMessage();
        }
    }

    public String retourReservation(String immatriculation, int kilometrage) {

        try {
            reservationService.enregistrerRetour(immatriculation, kilometrage);
            return "Le retour a été enregistré";
        } catch (RegleGestionException e) {
            return "Refusé : " + e.getMessage();
        }
    }

    public Reservations<Salarie, Timestamp, Vehicule> historiqueSalarie(String matricule) {

        return reservationService.historiqueSalarie(matricule);
    }

    public List<Vehicule> vehiculeEnDeplacement() {

        return reservationService.vehiculeEnDeplacement();
    }
}
