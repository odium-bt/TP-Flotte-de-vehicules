package dao;

import modeles.Vehicule;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour les véhicules
 */
public class VehiculeDAOImpl implements VehiculeDAO {
    private final String table = "vehicule";
    private final String id = "id";
    private final String immatriculation = "immatriculation";
    private final String marque = "marque";
    private final String modele = "modele";
    private final String date_mise_circulation = "date_mise_circulation";
    private final String kilometrage = "kilometrage";

    /**
     * Création d'un nouveau véhicule dans la base de données.
     *
     * @param vehicule Le véhicule à créer
     */
    @Override
    public void creer(Vehicule vehicule) throws DAOException {
        String query = "insert into " + table + " (" +
                id + ", " +
                immatriculation + ", " +
                marque + ", " +
                modele + ", " +
                date_mise_circulation + ", " +
                kilometrage + ") values (?,?,?,?,?,?)";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setInt(1, vehicule.getId());
            ps.setString(2, vehicule.getImmatriculation());
            ps.setString(3, vehicule.getMarque());
            ps.setString(4, vehicule.getModele());
            ps.setDate(5, Date.valueOf(vehicule.getDateMiseEnCirculation()));
            ps.setInt(6, vehicule.getKilometrage());

            ps.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la création du véhicule");
        }
    }

    /**
     * Cherche un véhicule dans la base de données selon son id.
     *
     * @param id L'id du véhicule
     * @return Un objet {@code Vehicule}, ou {@code null} si le véhicule n'a pas été trouvé
     */
    @Override
    public Vehicule trouverParId(int id) throws DAOException {
        String query = "select * from " + table + " where " + this.id + "=?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Vehicule v = null;

            while (rs.next()) {
                int code = rs.getInt(this.id);
                String immatriculation = rs.getString(this.immatriculation);
                String marque = rs.getString(this.marque);
                String modele = rs.getString(this.modele);
                LocalDate date_mise_circulation = rs.getDate(this.date_mise_circulation).toLocalDate();
                int kilometrage = rs.getInt(this.kilometrage);

                v = new Vehicule(code, immatriculation, marque, modele, date_mise_circulation, kilometrage);
            }

            return v;
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la recherche du véhicule");
        }
    }

    /**
     * Cherche un véhicule dans la base de données selon son immatriculation.
     *
     * @param immat L'immatriculation du véhicule
     * @return Un objet {@code Vehicule}, ou {@code null} si le véhicule n'a pas été trouvé
     */
    @Override
    public Vehicule trouverParImmatriculation(String immat) throws DAOException {
        String query = "select * from " + table + " where " + immatriculation + "=?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, immat);

            ResultSet rs = ps.executeQuery();
            Vehicule v = null;

            while (rs.next()) {
                int code = rs.getInt(this.id);
                String immatriculation = rs.getString(this.immatriculation);
                String marque = rs.getString(this.marque);
                String modele = rs.getString(this.modele);
                LocalDate date_mise_circulation = rs.getDate(this.date_mise_circulation).toLocalDate();
                int kilometrage = rs.getInt(this.kilometrage);

                v = new Vehicule(code, immatriculation, marque, modele, date_mise_circulation, kilometrage);
            }

            return v;
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la recherche du véhicule");
        }
    }

    /**
     * Récupère tous les véhicules stockés en base.
     *
     * @return Un objet {@code List<Vehicule>} contenant chaque véhicule stocké en base
     */
    @Override
    public List<Vehicule> lister() throws DAOException {
        List<Vehicule> vehicules = new ArrayList<>();
        String query = "SELECT * FROM " + table;

        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(this.id);
                String immatriculation = rs.getString(this.immatriculation);
                String marque = rs.getString(this.marque);
                String modele = rs.getString(this.modele);
                LocalDate date_mise_circulation = rs.getDate(this.date_mise_circulation).toLocalDate();
                int kilometrage = rs.getInt(this.kilometrage);

                Vehicule v = new Vehicule(id, immatriculation, marque, modele, date_mise_circulation, kilometrage);
                vehicules.add(v);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la recherche des véhicules");
        }

        return vehicules;
    }

    /**
     * Modifie le véhicule dans la base de donnée possédant le même id que l'objet fourni.
     *
     * @param vehicule L'objet véhicule modifié
     */
    @Override
    public void modifier(Vehicule vehicule) throws DAOException {
        String query = "update " + table + " set "
                + immatriculation + "=?,"
                + marque + "=?,"
                + modele + "=?,"
                + date_mise_circulation + "=?"
                + " WHERE " + id + "=?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, vehicule.getImmatriculation());
            ps.setString(2, vehicule.getMarque());
            ps.setString(3, vehicule.getModele());
            ps.setDate(4, Date.valueOf(vehicule.getDateMiseEnCirculation()));

            ps.setInt(5, vehicule.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la modification du véhicule");
        }
    }

    /**
     * Supprime le véhicule selon l'id fourni.
     *
     * @param id Id du véhicule
     */
    @Override
    public void supprimer(int id) throws DAOException {
        String query = "delete from " + table + " where " + this.id + " =?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la suppression du véhicule");
        }
    }
}
