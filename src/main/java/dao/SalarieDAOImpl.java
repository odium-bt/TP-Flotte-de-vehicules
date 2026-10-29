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
     * Création d'un nouveau salarié dans la base de données.
     *
     * @param salarie Le salarié à créer
     */
    @Override
    public void creer(Salarie salarie) {
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
     * Cherche un salarié dans la base de données selon son matricule.
     *
     * @param matricule Le matricule du salarié
     * @return Un objet {@code Salarie}, ou {@code null} si le salarié n'a pas été trouvé
     */
    @Override
    public Salarie trouverParMatricule(String matricule) {
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
     * Récupère tous les salariés stockés en base.
     *
     * @return Un objet {@code List<Salarie>} contenant chaque salarié stocké en base
     */
    @Override
    public List<Salarie> lister() {
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
     * Modifie le salarié dans la base de donnée possédant le même matricule que l'objet fourni.
     *
     * @param salarie L'objet salarié modifié
     */
    @Override
    public void modifier(Salarie salarie) {
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
     * Supprime le salarié selon le matricule fourni.
     *
     * @param matricule Matricule du salarié
     */
    @Override
    public void supprimer(String matricule) {
        String query = "delete from " + table + " where " + this.matricule + " =?";
        try (PreparedStatement ps = DAOFactory.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, matricule);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la suppression du salarié");
        }
    }
}
