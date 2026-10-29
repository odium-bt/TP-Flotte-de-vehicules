package service;

import dao.ReservationDAO;
import dao.VehiculeDAO;
import modeles.Flotte;
import modeles.Vehicule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VehiculeService {
    private final Logger log = LogManager.getLogger(String.valueOf(VehiculeService.class));
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

    /**
     * Récupère chaque véhicule de la flotte sous forme de string.
     *
     * @return Retourne une {@code List<String>} contenant chaque véhicule sous forme de string
     */
    public List<String> afficherFlotte() {
        List<String> res = new ArrayList<>();

        for (Vehicule v : flotte.getListeVehicules()) {
            res.add(v.toString());
        }

        return res;
    }

    public void rechercheParImmatriculation(String immatriculation) {
    }

    public void retirerVehicule(int id) {
        // TODO Chercher si le véhicule est actuellement utilisé dans une réservation
        //      Sinon le supprimer
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
                Comparator.comparing(Vehicule::getDateMiseEnCirculation);
    }

    public void trierKilometrageDecroissant() {
        Comparator<Vehicule> parKmDecroissant =
                Comparator.comparingInt(Vehicule::getKilometrage)
                        .reversed();
    }
}
