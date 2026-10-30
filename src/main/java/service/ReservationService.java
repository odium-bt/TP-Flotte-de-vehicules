package service;

import dao.DAOException;
import dao.ReservationDAO;
import dao.SalarieDAO;
import dao.VehiculeDAO;
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
    public List<String> afficherFlotte() {
        log.info("Affichage de la flotte");

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
}