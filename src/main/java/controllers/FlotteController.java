package controllers;

import modeles.Vehicule;
import service.RegleGestionException;
import service.VehiculeService;

import java.time.LocalDate;
import java.util.List;

public class FlotteController {

    private VehiculeService vehiculeEnService;

    public FlotteController(VehiculeService vehiculeEnService) {

        this.vehiculeEnService = vehiculeEnService;
    }

    public String ajouterVehicule(String immatriculation, String marque, String modele, LocalDate dateMiseEnCirculation, int kilometrage) {

        try {

            Vehicule vehicule = new Vehicule(immatriculation, marque, modele, dateMiseEnCirculation, kilometrage);
            vehiculeEnService.ajouterVehicule(vehicule);
            return "Le véhicule immatriculé " + immatriculation + " a été ajouté";

        } catch (RegleGestionException e) {
            // TODO Définir le message d'erreur
            return "Refusé : " + e.getMessage();
        } catch (IllegalArgumentException e) {
        return "Refusé : " + e.getMessage();
    }
    }

    public List<Vehicule> afficherFlotte() {

        return vehiculeEnService.afficherFlotte();
    }

    public String retirerVehicule(String immatriculation) {

        try {
            vehiculeEnService.retirerVehicule(immatriculation);
            return "Le véhicule " + immatriculation + " a été retiré";
        } catch (IllegalArgumentException e) {
            return " Refusé : " + e.getMessage();
        }
    }

    public Vehicule rechercherVehicule(String immatriculation) {

        return vehiculeEnService.rechercheParImmatriculation(immatriculation);
    }

    public List<Vehicule> trierFlotte(int choix) {

        switch (choix) {
            case 1:
                return vehiculeEnService.trierMarque() ;
                break;
            case 2:
                return vehiculeEnService.trierDate();
                break;
            case 3:
                return vehiculeEnService.trierKilometrageDecroissant();
            default:
                return vehiculeEnService.afficherFlotte();
        }

    }


}
