package modeles;

public class Salarie {

    private String nom;
    private String prenom;
    private String matricule;

    public Salarie(String matricule, String prenom, String nom) {

        validation(matricule);
        this.matricule = matricule;
        this.prenom = prenom;
        this.nom = nom;
    }

    private void validation(String matricule) {
        if (!matricule.matches("\\d{6}")) {
            throw new IllegalArgumentException("Le matricule est invalide!");
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Override
    public String toString() {
        return "Salarie{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", matricule='" + matricule + '\'' +
                '}';
    }
}
