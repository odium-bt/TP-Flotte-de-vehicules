package service;

import dao.ReservationDAO;
import dao.SalarieDAO;
import modeles.Salarie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SalarieService {
    private final Logger log = LogManager.getLogger(String.valueOf(VehiculeService.class));
    private final SalarieDAO salarieDAO;
    private final ReservationDAO reservationDAO;

    public SalarieService(SalarieDAO salarieDAO, ReservationDAO reservationDAO) {
        this.salarieDAO = salarieDAO;
        this.reservationDAO = reservationDAO;
    }

    public void ajouterSalarie(String matricule, String nom, String prenom) {
        Salarie salarie = new Salarie(matricule, prenom, nom);
        salarieDAO.creer(salarie);
    }
}
