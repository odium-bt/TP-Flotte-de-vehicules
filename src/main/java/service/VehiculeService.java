package service;

import dao.DAOException;
import dao.ReservationDAO;
import dao.VehiculeDAO;
import modeles.Flotte;
import modeles.Vehicule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
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

    public void ajouterVehicule(String immatriculation, String marque, String modele, LocalDate dateMiseEnCirculation, int kilometrage) {
        log.info("Création d'un véhicule");

        Vehicule vehicule = new Vehicule(immatriculation, marque, modele, dateMiseEnCirculation, kilometrage);

        try {
            if (vehiculeDAO.trouverParImmatriculation(immatriculation) != null) {
                throw new RegleGestionException("Un véhicule avec cette immatriculation existe déjà");
            }

            vehiculeDAO.creer(vehicule);

            log.info("Véhicule créé");
        } catch (DAOException | RegleGestionException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Récupère chaque véhicule de la flotte sous forme de string.
     *
     * @return Retourne une {@code List<String>} contenant chaque véhicule sous forme de string
     */
    public List<String> afficherFlotte() {
        log.info("Affichage de la flotte");

        List<String> res = new ArrayList<>();

        try {
            for (Vehicule v : flotte.getListeVehicules()) {
                res.add(v.toString());
            }

            return res;
        } catch (DAOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Vehicule rechercheParImmatriculation(String immatriculation) {
        log.info("Recherche du véhicule immatriculé {}", immatriculation);
        try {
            Vehicule v = vehiculeDAO.trouverParImmatriculation(immatriculation);
            log.info("Véhicule trouvé avec succès");
            return v;
        } catch (DAOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void retirerVehicule(int id) {
        log.info("Tentative de suppression du véhicule {}", id);

        try {
            if (vehiculeDAO.trouverParId(id) == null) {
                throw new RegleGestionException("Ce véhicule n'existe pas");
            }

            if (reservationDAO.estEnDeplacement(id)) {
                throw new RegleGestionException("Ce véhicule est actuellement en déplacement");
            }

            vehiculeDAO.supprimer(id);

            log.info("Véhicule {} supprimé avec succès", id);
        } catch (DAOException | RegleGestionException e) {
            log.error(e.getMessage());
        }
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
