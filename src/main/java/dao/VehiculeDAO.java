package dao;

public interface VehiculeDAO {
    void creer(Vehicule vehicule); // renseigne l'id généré

    Vehicule trouverParId(int id);

    Vehicule trouverParImmatriculation(String immat);

    List<Vehicule> lister();

    void modifier(Vehicule vehicule); // ex. : mise à jour du kilométrage

    void supprimer(int id);
}