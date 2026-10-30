package dao;

import modeles.Salarie;

import java.util.List;

public interface SalarieDAO {
    /**
     * Création d'un nouveau salarié dans la base de données.
     *
     * @param salarie Le salarié à créer
     */
    void creer(Salarie salarie) throws DAOException;

    /**
     * Cherche un salarié dans la base de données selon son matricule.
     *
     * @param matricule Le matricule du salarié
     * @return Un objet {@code Salarie}, ou {@code null} si le salarié n'a pas été trouvé
     */
    Salarie trouverParMatricule(String matricule) throws DAOException;

    /**
     * Récupère tous les salariés stockés en base.
     *
     * @return Un objet {@code List<Salarie>} contenant chaque salarié stocké en base
     */
    List<Salarie> lister() throws DAOException;

    /**
     * Modifie le salarié dans la base de donnée possédant le même matricule que l'objet fourni.
     *
     * @param salarie L'objet salarié modifié
     */
    void modifier(Salarie salarie) throws DAOException;

    /**
     * Supprime le salarié selon le matricule fourni.
     *
     * @param matricule Matricule du salarié
     */
    void supprimer(String matricule) throws DAOException;
}
