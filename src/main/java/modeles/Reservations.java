package modeles;

import java.util.HashMap;

// Classe générique
public class Reservations<S, K, V>{

    private S salarie;
    // HashMap contenant Le TimeStamp et le véhicule, formant à
    // la suite, la clé(K) et la valeur(K)
    private HashMap<K, V> listeReservations;

    public Reservations(S salarie) {
        this.listeReservations = new HashMap<>();
        this.salarie = salarie;
    }

    public S getSalarie() {
        return salarie;
    }

    public HashMap<K, V> getListeReservations() {
        return listeReservations;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "salarie=" + salarie +
                ", listeReservations=" + listeReservations +
                '}';
    }
}
