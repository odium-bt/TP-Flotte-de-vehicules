package modeles;

import java.sql.Timestamp;

public class Reservation {

    private int idReservation;
    private Vehicule vehicule;
    private Timestamp dateDepart;
    private Timestamp dateRetour;
    private int kilometrage;

    //Constructeur retour
    public Reservation(int idReservation, Vehicule vehicule, Timestamp dateDepart, Timestamp dateRetour, int kilometrage) {
        this.idReservation = idReservation;
        this.vehicule = vehicule;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
        this.kilometrage = kilometrage;
    }

    //Constructeur départ


    public Reservation(Vehicule vehicule, Timestamp dateDepart) {
        this.vehicule = vehicule;
        this.dateDepart = dateDepart;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Timestamp getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Timestamp dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Timestamp getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Timestamp dateRetour) {
        this.dateRetour = dateRetour;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", vehicule=" + vehicule +
                ", dateDepart=" + dateDepart +
                ", dateRetour=" + dateRetour +
                ", kilometrage=" + kilometrage +
                '}';
    }
}
