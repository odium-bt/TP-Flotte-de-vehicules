package dao;

import modeles.Reservations;
import modeles.Salarie;
import modeles.Vehicule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
     * {@inheritDoc}
     */
    @Override
    public void creer(Reservations<Salarie, Timestamp, Vehicule> reservations) throws DAOException {
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
     * {@inheritDoc}
     */
    @Override
    public Reservations<Salarie, Timestamp, Vehicule> trouverParMatricule(String matricule) throws DAOException {
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
     * {@inheritDoc}
     */
    @Override
    public List<Reservations<Salarie, Timestamp, Vehicule>> lister() throws DAOException {
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
     * {@inheritDoc}
     */
    @Override
    public boolean estEnDeplacement(int idReservation) throws DAOException {
        String query = "SELECT COUNT(*) FROM " + table
                + " WHERE " + id + " = ?"
                + " AND " + dateDepart + " <= NOW()"
                + " AND " + dateRetour + " IS NULL";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setInt(1, idReservation);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la vérification du déplacement du véhicule");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifier(Reservations<Salarie, Timestamp, Vehicule> reservations) throws DAOException {
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
     * {@inheritDoc}
     */
    @Override
    public void cloturer(int idReservation, Timestamp timestamp, int kmRetour) {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void supprimer(int id) throws DAOException {
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
