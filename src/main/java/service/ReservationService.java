package service;

import dao.*;
import modeles.Salarie;
import modeles.Vehicule;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ReservationService {
    private final Logger log = LogManager.getLogger(String.valueOf(ReservationService.class));
    private final VehiculeDAO vehiculeDAO;
    private final ReservationDAO reservationDAO;
    private final SalarieDAO salarieDAO;

    public ReservationService(VehiculeDAO vehiculeDAO, ReservationDAO reservationDAO, SalarieDAO salarieDAO) {
        this.vehiculeDAO = vehiculeDAO;
        this.reservationDAO = reservationDAO;
        this.salarieDAO = salarieDAO;
    }

    /**
     * Ajout d'un salarié.
     *
     * @param matricule Le matricule
     * @param nom       Le nom
     * @param prenom    Le prénom
     */
    public void ajouterSalarie(String matricule, String nom, String prenom) {
        log.info("Création d'un salarié");

        Salarie salarie = new Salarie(matricule, prenom, nom);

        try {
            salarieDAO.creer(salarie);

            log.info("Salarié créé");
        } catch (DAOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Récupère chaque salarié sous forme de string.
     *
     * @return Une {@code List<String>} contenant chaque salarié sous forme de string
     */
    public List<String> afficherSalaries() {
        log.info("Affichage des salariés");

        List<String> res = new ArrayList<>();

        try {
            for (Salarie s : salarieDAO.lister()) {
                res.add(s.toString());
            }
        } catch (DAOException e) {
            log.error(e.getMessage());
        }

        return res;
    }

    /**
     * Récupère un salarié par son matricule.
     *
     * @param matricule Le matricule du salarié
     * @return {@code Salarie}
     */
    public Salarie rechercheParMatricule(String matricule) {
        log.info("Affichage du salarié {}", matricule);

        try {
            return salarieDAO.trouverParMatricule(matricule);
        } catch (DAOException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * Supprime le compte du salarié.
     *
     * @param matricule Son matricule
     */
    public void supprimerParMatricule(String matricule) {
        log.info("Suppression du salarié {}", matricule);

        // TODO Check si le salarié est en déplacement
    }

    /**
     * Créé une nouvelle réservation.
     */
    public void creerReservation() {
        // TODO new Reservation();
    }

    /**
     * Affiche toutes les réservations.
     *
     * @return {@code List<String>} - Liste des réservations sous forme de string
     */
    public List<String> afficherReservations() {
        // TODO Afficher réservations
        return List.of();
    }

    /**
     * Enregistre le retour d'un véhicule et la clotûre d'une réservation.
     *
     * @param immatriculation Immatriculation du véhicule
     * @param kmRetour        Kilométrage au retour
     */
    public void enregistrerRetour(String immatriculation, int kmRetour) {
        log.info("Enregistrement d'un retour");

        try {
            Vehicule v = vehiculeDAO.trouverParImmatriculation(immatriculation);

            // Règles de gestion
            if (v == null) {
                throw new RegleGestionException("Véhicule inconnu");
            }
            if (!reservationDAO.estEnDeplacement(v.getId())) {
                throw new RegleGestionException("Ce véhicule n'est pas en déplacement");
            }
            if (kmRetour < v.getKilometrage()) {
                throw new RegleGestionException("Kilométrage inférieur au compteur actuel");
            }

            // Persistance : deux DAO pour une seule action
            reservationDAO.cloturer(v.getId(), new Timestamp(System.currentTimeMillis()), kmRetour);
            v.setKilometrage(kmRetour);
            vehiculeDAO.modifier(v);

            log.info("Retour de {} à {} km", immatriculation, kmRetour);
        } catch (DAOException | RegleGestionException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Supprime la réservation.
     *
     * @param idReservation Id de la réservation
     */
    public void supprimerReservation(int idReservation) {
        log.info("Tentative de suppression d'une réservation");

        try {
            if (reservationDAO.estEnDeplacement(idReservation)) {
                throw new RegleGestionException("Cette réservation est en cours");
            }

            reservationDAO.supprimer(idReservation);

            log.info("Réservation supprimée avec succès");
        } catch (DAOException | RegleGestionException e) {
            log.error(e.getMessage());
        }
    }
}