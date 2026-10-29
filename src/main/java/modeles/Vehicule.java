package modeles;

import java.time.LocalDate;

public class Vehicule {

    private int id;
    private String immatriculation;
    private String marque;
    private String modele;
    private LocalDate dateMiseEnCirculation;
    private int kilometrage;

    public Vehicule(int id, String immatriculation, String marque, String modele, LocalDate dateMiseEnCirculation, int kilometrage) {

        verification(immatriculation, kilometrage);
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.dateMiseEnCirculation = dateMiseEnCirculation;
        this.kilometrage = kilometrage;

    }

    public Vehicule(String immatriculation, String marque, String modele, LocalDate dateMiseEnCirculation, int kilometrage) {

        verification(immatriculation, kilometrage);
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.dateMiseEnCirculation = dateMiseEnCirculation;
        this.kilometrage = kilometrage;
    }

    private void verification(String immatriculation, int kilometrage) {

        if (!immatriculation.matches("[A-Z]{2}-\\d{3}-[A-Z]{2}")) {
            throw new IllegalArgumentException("Format d'immatriculation invalide");
        }

        if (kilometrage < 0 ) {
            throw new IllegalArgumentException("Le kilométrage ne peut être inférieur à 0!");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public LocalDate getDateMiseEnCirculation() {
        return dateMiseEnCirculation;
    }

    public void setDateMiseEnCirculation(LocalDate dateMiseEnCirculation) {
        this.dateMiseEnCirculation = dateMiseEnCirculation;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", immatriculation='" + immatriculation + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", dateMiseEnCirculation=" + dateMiseEnCirculation +
                ", kilometrage=" + kilometrage +
                '}';
    }
}
