package modeles;

import java.util.HashMap;

public class Reservations<S, K, V>{

    private S salarie;
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
