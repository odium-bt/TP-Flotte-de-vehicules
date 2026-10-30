package modeles;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Flotte {

    private List<Vehicule> listeVehicules;

    public Flotte() {
        this.listeVehicules = new ArrayList<>();
    }

    public List<Vehicule> getListeVehicules() {
        return listeVehicules;
    }

    public void setListeVehicules(List<Vehicule> listeVehicules) {
        this.listeVehicules = listeVehicules;
    }

}
