package service;

import dao.VehiculeDAO;

import java.util.Comparator;

public class VehiculeService {
    private final VehiculeDAO vehiculeDAO;
    private final ReservationDAO reservationDAO;
    private final Flotte flotte;

    public VehiculeService(VehiculeDAO vehiculeDAO, ReservationDAO reservationDAO, Flotte flotte) {
        this.vehiculeDAO = vehiculeDAO;
        this.reservationDAO = reservationDAO;
        this.flotte = flotte;
    }

    public void ajouterVehicule() {
        // TODO Ajouter véhicule, mais vérifier les règles de gestion (immatriculation unique etc)
        //      Lever RegleGestionException en cas d'erreur
    }

    public void afficherFlotte() {

    }

    public void retirerVehicule(int id) {
        // TODO Chercher si le véhicule est actuellement utilisé dans une réservation
        //      Sinon le supprimer
    }

    public void rechercheParImmatriculation(String immatriculation) {
    }

    public void trierMarque() {
        Comparator<Vehicule> parMarqueModele = Comparator.comparing(Vehicule::getMarque)
                .thenComparing(Vehicule::getModele);
    }

    public void trierModele() {
        Comparator<Vehicule> parModeleMarque = Comparator.comparing(Vehicule::getModele)
                .thenComparing(Vehicule::getMarque);
    }

    public void trierDate() {
        Comparator<Vehicule> parDate =
                Comparator.comparing(Vehicule::getDateMiseCirculation);
    }

    public void trierKilometrageDecroissant() {
        Comparator<Vehicule> parKmDecroissant =
                Comparator.comparingInt(Vehicule::getKilometrage)
                        .reversed();
    }
}
