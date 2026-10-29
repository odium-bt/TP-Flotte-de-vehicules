package service;

import dao.ReservationDAO;
import dao.SalarieDAO;
import modeles.Salarie;
import modeles.Vehicule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SalarieService {
    private final Logger log = LogManager.getLogger(String.valueOf(VehiculeService.class));
    private final SalarieDAO salarieDAO;
    private final ReservationDAO reservationDAO;

    public SalarieService(SalarieDAO salarieDAO, ReservationDAO reservationDAO) {
        this.salarieDAO = salarieDAO;
        this.reservationDAO = reservationDAO;
    }

    /**
     * Ajout d'un salarié.
     *
     * @param matricule Le matricule
     * @param nom       Le nom
     * @param prenom    Le prénom
     */
    public void ajouterSalarie(String matricule, String nom, String prenom) {
        Salarie salarie = new Salarie(matricule, prenom, nom);
        salarieDAO.creer(salarie);
    }

    /**
     * Récupère chaque salarié sous forme de string.
     *
     * @return Une {@code List<String>} contenant chaque salarié sous forme de string
     */
    public List<String> afficherFlotte() {
        List<String> res = new ArrayList<>();

        for (Salarie s : salarieDAO.lister()) {
            res.add(s.toString());
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
        return salarieDAO.trouverParMatricule(matricule);
    }
}
