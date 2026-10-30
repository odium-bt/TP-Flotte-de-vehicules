package controllers;

import vue.MenuConsole;
import modeles.Vehicule;
import service.RegleGestionException;
import service.VehiculeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FlotteController {

    private VehiculeService vehiculeEnService;
    private MenuConsole vue;

    public FlotteController(VehiculeService vehiculeEnService, MenuConsole vue) {

        this.vehiculeEnService = vehiculeEnService;
        this.vue = vue;
    }

    public void ajouterVehicule() {

        String immatriculation = vue.saisieTexte("Immatriculation (AA-123-AA)").toUpperCase();
        String marque = vue.saisieTexte("Marque : ");
        String modele = vue.saisieTexte("Modèle : ");
        String dateSaisie = vue.saisieTexte("Mise en circulation (jj/MM/aaaa) : ");
        int kilometrage = vue.saisieEntier("Kilométrage : ");

        try {

            LocalDate dateMiseEnCirculation = LocalDate.parse(dateSaisie, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            vehiculeEnService.ajouterVehicule(immatriculation, marque, modele, dateMiseEnCirculation, kilometrage);
            vue.afficherSucces("Le véhicule immatriculé " + immatriculation + " a été ajouté");

        } catch (RegleGestionException e) {
            vue.afficherRefus(e.getMessage());
        } catch (IllegalArgumentException e) {
            vue.afficherRefus(e.getMessage());
        } catch (DateTimeParseException e) {
            vue.afficherRefus(e.getMessage());
        }
    }

    public void afficherFlotte() {

        List<Vehicule> vehicules = vehiculeEnService.afficherFlotte();
        if (vehicules == null) {
            vue.afficherRefus("Impossible de récupérer la liste");
            return;
        }
        vue.afficherVehicules(vehicules);
    }

    public void retirerVehicule() {
        String immatriculation = vue.saisieTexte("Immatriculation du véhicule à retirer").toUpperCase();
        String confirmation = vue.saisieTexte("Confirmer le retrait de " + immatriculation + " ? (o/n)");

        if (confirmation.equalsIgnoreCase("o")) {
            Vehicule vehicule = vehiculeEnService.rechercheParImmatriculation(immatriculation);
            if (vehicule == null) {
                vue.afficherRefus("véhicule introuvable.");
            } else {
                vehiculeEnService.retirerVehicule(immatriculation);
                vue.afficherSucces("Véhicule " + immatriculation + " retiré de la flotte.");
            }
        } else {
            vue.afficherSucces("Retrait annulé.");
        }
    }


    public void rechercherVehicule() {

        String immatriculation = vue.saisieTexte("Saisissez l'immatriculation :").toUpperCase();
        Vehicule vehicule = vehiculeEnService.rechercheParImmatriculation(immatriculation);

        if (vehicule == null) {
            vue.afficherRefus("Véhicule introuvable");
        } else {
            // List.of pour un rendu plus propre que le toString
            vue.afficherVehicules(List.of(vehicule));
        }
    }

    public void trierFlotte() {
        vue.afficherSucces("1. Par marque, puis modèle");
        vue.afficherSucces("2. Par date de mise en circulation (plus ancien d'abord)");
        vue.afficherSucces("3. Par kilométrage décroissant");
        int choix = vue.saisieEntier("Tri");

        switch (choix) {
            case 1:
                vehiculeEnService.trierMarque();
                break;
            case 2:
                vehiculeEnService.trierDate();
                break;
            case 3:
                vehiculeEnService.trierKilometrageDecroissant();
                break;
            default:
                vue.afficherRefus("choix de tri inconnu.");
                return;
        }
        afficherFlotte();
    }


}
