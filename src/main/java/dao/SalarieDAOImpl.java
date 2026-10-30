package dao;

import modeles.Salarie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalarieDAOImpl implements SalarieDAO {
    private final String table = "salarie";
    private final String matricule = "matricule";
    private final String nom = "nom";
    private final String prenom = "prenom";

    /**
     * {@inheritDoc}
     */
    @Override
    public void creer(Salarie salarie) throws DAOException {
        String query = "insert into " + table + " (" +
                matricule + ", " +
                nom + ", " +
                prenom + ") " +
                "values (?,?,?)";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, salarie.getMatricule());
            ps.setString(2, salarie.getNom());
            ps.setString(3, salarie.getPrenom());

            ps.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la création du salarié");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Salarie trouverParMatricule(String matricule) throws DAOException {
        String query = "select * from " + table + " where " + this.matricule + "=?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, matricule);

            ResultSet rs = ps.executeQuery();
            Salarie s = null;

            while (rs.next()) {
                String id = rs.getString(this.matricule);
                String nom = rs.getString(this.nom);
                String prenom = rs.getString(this.prenom);

                s = new Salarie(id, nom, prenom);
            }

            return s;
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la recherche du salarié");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Salarie> lister() throws DAOException {
        List<Salarie> salaries = new ArrayList<>();
        String query = "SELECT * FROM " + table;

        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString(this.matricule);
                String nom = rs.getString(this.nom);
                String prenom = rs.getString(this.prenom);

                Salarie s = new Salarie(id, nom, prenom);
                salaries.add(s);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la recherche des salariés");
        }

        return salaries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifier(Salarie salarie) throws DAOException {
        String query = "update " + table + " set "
                + nom + "=?,"
                + prenom + "=?"
                + " WHERE " + matricule + "=?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, salarie.getNom());
            ps.setString(2, salarie.getPrenom());

            ps.setString(3, salarie.getMatricule());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la modification du salarié");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void supprimer(String matricule) throws DAOException {
        String query = "delete from " + table + " where " + this.matricule + " =?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, matricule);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la suppression du salarié");
        }
    }
}
