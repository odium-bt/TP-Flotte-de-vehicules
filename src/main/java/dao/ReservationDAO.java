package dao;

import modeles.Reservations;
import modeles.Salarie;
import modeles.Vehicule;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationDAO {
    /**
     * Création de nouvelles réservations dans la base de données.
     *
     * @param reservations Les réservations à créer
     */
    void creer(Reservations<Salarie, Timestamp, Vehicule> reservations);

    /**
     * Cherche les réservations associées au salarié à qui appartient la matricule donnée
     *
     * @param matricule Le matricule du salarié
     * @return Un objet Reservations contenant les réservations associées à un salarié
     */
    Reservations<Salarie, Timestamp, Vehicule> trouverParMatricule(String matricule);

    /**
     * Récupère toutes les réservations stockées en base.
     *
     * @return Un objet {@code List<Reservations<Salarie, Timestamp, Vehicule>>} contenant chaque réservation stockée en base
     */
    List<Reservations<Salarie, Timestamp, Vehicule>> lister();

    /**
     * Trouve si un véhicule est actuellement utilisé par une réservation.
     *
     * @param id Id du véhicule
     * @return {@code boolean}
     */
    boolean estEnDeplacement(int id);

    /**
     * Modifie les réservations d'un salarié.
     *
     * @param reservations Les réservations du salarié
     */
    void modifier(Reservations<Salarie, Timestamp, Vehicule> reservations);

    /**
     * Clotûre une réservation.
     *
     * @param id        Id de la réservation
     * @param timestamp Date de la fermeture
     * @param kmRetour  Longueur du trajet à la cloturation
     */
    void cloturer(int id, Timestamp timestamp, int kmRetour);

    /**
     * Supprime une réservation par son id.
     *
     * @param id Id d'une réservation
     */
    void supprimer(int id);
}
