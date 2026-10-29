package dao;

import modeles.Reservations;
import modeles.Salarie;

import java.util.List;

public interface SalarieDAO {
    void creer(Salarie salarie);

    Salarie trouverParMatricule(String matricule);

    List<Salarie> lister();

    void modifier(Salarie salarie);

    void supprimer(String matricule);
}
