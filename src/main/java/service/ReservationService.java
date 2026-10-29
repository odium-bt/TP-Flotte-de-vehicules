package service;

import dao.ReservationDAO;
import dao.VehiculeDAO;
import modeles.Vehicule;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Timestamp;

import modeles.Salarie;
import modeles.Reservations;
import java.util.List;
import java.util.ArrayList;


public class ReservationService {
    private final Logger log = LogManager.getLogger(String.valueOf(ReservationService.class));
    private final VehiculeDAO vehiculeDAO;
    private final ReservationDAO reservationDAO;

    public ReservationService(VehiculeDAO vehiculeDAO, ReservationDAO reservationDAO) {
        this.vehiculeDAO = vehiculeDAO;
        this.reservationDAO = reservationDAO;
    }

    public void enregistrerRetour(String immatriculation, int kmRetour) {
        Vehicule v = vehiculeDAO.trouverParImmatriculation(immatriculation);
        // Règles de gestion
        if (v == null) {
            throw new RegleGestionException("véhicule inconnu");
        }
        if (!reservationDAO.estEnDeplacement(v.getId())) {
            throw new RegleGestionException("ce véhicule n'est pas en déplacement");
        }
        if (kmRetour < v.getKilometrage()) {
            throw new RegleGestionException("kilométrage inférieur au compteur actuel");
        }
        // Persistance : deux DAO pour une seule action
        reservationDAO.cloturer(v.getId(), new Timestamp(System.currentTimeMillis()), kmRetour);
        v.setKilometrage(kmRetour);
        vehiculeDAO.modifier(v);

        log.info("Retour de {} à {} km", immatriculation, kmRetour);
    }

    public void ajouterSalarie(Salarie salarie) {
        // TODO Zadig
    }

    public void enregistrerDepart(String immatriculation, String matricule) {
        // TODO Zadig
    }

    public Reservations<Salarie, Timestamp, Vehicule> historiqueSalarie(String matricule) {
        return null;
    }

    public List<Vehicule> vehiculeEnDeplacement() {
        return new ArrayList<>();
    }
}