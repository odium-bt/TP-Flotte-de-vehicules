package dao;

import modeles.Reservations;
import modeles.Salarie;
import modeles.Vehicule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationDAOImpl implements ReservationDAO {

    private final String table = "reservation";
    private final String id = "id";
    private final String matricule = "matricule";
    private final String idVehicule = "id_vehicule";
    private final String dateDepart = "date_depart";
    private final String dateRetour = "date_retour";
    private final String kmRetour = "km_retour";


    /**
     * Création de nouvelles réservations dans la base de données.
     *
     * @param reservations Les réservations à créer
     */
    @Override
    public void creer(Reservations<Salarie, Timestamp, Vehicule> reservations) {
        String query = "INSERT INTO " + table + " ("
                + matricule + ", "
                + idVehicule + ", "
                + dateDepart + ") "
                + "VALUES( ?, ?, ?)";

        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {

            for (Map.Entry<Timestamp, Vehicule> entry : reservations.getListeReservations().entrySet()) {

                ps.setString(1, reservations.getSalarie().getMatricule());
                ps.setInt(2, entry.getValue().getId());
                ps.setTimestamp(3, entry.getKey());
                // Les dates et kilomètres de retour sont null avant la clotûre

                ps.executeQuery();
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la création de la réservation");
        }
    }

    /**
     * Cherche les réservations associées au salarié à qui appartient la matricule donnée
     *
     * @param matricule Le matricule du salarié
     * @return Un objet Reservations contenant les réservations associées à un salarié
     */
    @Override
    public Reservations<Salarie, Timestamp, Vehicule> trouverParMatricule(String matricule) {
        String query = "SELECT * FROM " + table
                + " WHERE " + this.matricule + " = ?";

        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {

            ps.setString(1, matricule);

            try (ResultSet rs = ps.executeQuery()) {
                Salarie salarie = DAOFactory.getInstance().getSalarieDAO().trouverParMatricule(matricule);

                if (salarie == null) {
                    return null;
                }

                Reservations<Salarie, Timestamp, Vehicule> reservations = new Reservations<>(salarie);

                while (rs.next()) {
                    Timestamp depart = rs.getTimestamp(dateDepart);
                    int vehiculeId = rs.getInt(idVehicule);

                    Vehicule vehicule = DAOFactory.getInstance().getVehiculeDAO().trouverParId(vehiculeId);

                    reservations.getListeReservations().put(depart, vehicule);
                }

                return reservations;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la recherche de la réservation");
        }
    }

    /**
     * Récupère toutes les réservations stockées en base.
     *
     * @return Un objet {@code List<Reservations<Salarie, Timestamp, Vehicule>>} contenant chaque réservation stockée en base
     */
    @Override
    public List<Reservations<Salarie, Timestamp, Vehicule>> lister() {
        List<Reservations<Salarie, Timestamp, Vehicule>> liste = new ArrayList<>();

        // Récupère uniquement les matricules qui ont des réservations, sans doublons
        String query = "SELECT DISTINCT " + matricule + " FROM " + table;

        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Pour chaque matricule, va chercher les réservations associées et les ajoute à la liste
                String matricule = rs.getString(this.matricule);

                Reservations<Salarie, Timestamp, Vehicule> reservations = trouverParMatricule(matricule);

                if (reservations != null) {
                    liste.add(reservations);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la recherche des réservations");
        }

        return liste;
    }

    /**
     * Trouve si un véhicule est actuellement utilisé par une réservation.
     *
     * @param id Id du véhicule
     * @return {@code boolean}
     */
    @Override
    public boolean estEnDeplacement(int id) {
        // TODO
        return false;
    }

    /**
     * Modifie les réservations d'un salarié.
     *
     * @param reservations Les réservations du salarié
     */
    @Override
    public void modifier(Reservations<Salarie, Timestamp, Vehicule> reservations) {
        String query = "UPDATE " + table + " SET "
                + matricule + " = ?, "
                + idVehicule + " = ?, "
                + dateDepart + " = ?"
                + " WHERE " + id + " = ?";

        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {

            for (Map.Entry<Timestamp, Vehicule> entry : reservations.getListeReservations().entrySet()) {

                ps.setString(1, reservations.getSalarie().getMatricule());
                ps.setInt(2, entry.getValue().getId());
                ps.setTimestamp(3, entry.getKey());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la modification de la réservation");
        }
    }

    /**
     * Clotûre une réservation.
     *
     * @param id        Id de la réservation
     * @param timestamp Date de la fermeture
     * @param kmRetour  Longueur du trajet à la cloturation
     */
    @Override
    public void cloturer(int id, Timestamp timestamp, int kmRetour) {
        // TODO
    }

    /**
     * Supprime une réservation par son id.
     *
     * @param id Id d'une réservation
     */
    @Override
    public void supprimer(int id) {
        // TODO Corriger
        String query = "DELETE FROM " + table
                + " WHERE " + this.id + " = ?";

        try (PreparedStatement ps =
                     DAOFactory.getInstance().getConnection()
                             .prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la suppression de la réservation");
        }
    }
}
