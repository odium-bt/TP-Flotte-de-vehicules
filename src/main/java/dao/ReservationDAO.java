package dao;

import modeles.Reservations;
import modeles.Salarie;
import modeles.Vehicule;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationDAO {
    void creer(Reservations<Salarie, Timestamp, Vehicule> reservations);

    Reservations<Salarie, Timestamp, Vehicule> trouverParMatricule(String matricule);

    List<Reservations<Salarie, Timestamp, Vehicule>> lister();

    boolean estEnDeplacement(int id);

    void modifier(Reservations<Salarie, Timestamp, Vehicule> reservations);

    void cloturer(int id, Timestamp timestamp, int kmRetour);

    void supprimer(int id);
}
