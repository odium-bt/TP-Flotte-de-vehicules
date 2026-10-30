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
     * Trouve si une réservation est actuellement en cours de déplacement.
     *
     * @param idReservation Id de la réservation
     * @return {@code boolean}
     */
    boolean estEnDeplacement(int idReservation);

    /**
     * Modifie une réservation.
     *
     * @param idReservation Id de la réservation
     * @param dateDepart    Date de départ
     * @param vehicule      Véhicule
     */
    void modifier(int idReservation, Timestamp dateDepart, Vehicule vehicule);

    /**
     * Clotûre une réservation.
     *
     * @param idReservation Id de la réservation
     * @param timestamp     Date de la fermeture
     * @param kmRetour      Longueur du trajet à la cloturation
     */
    void cloturer(int idReservation, Timestamp timestamp, int kmRetour);

    /**
     * Supprime une réservation par son id.
     *
     * @param id Id d'une réservation
     */
    void supprimer(int id);
}
