package controllers;

public class AuthController {

    private final AuthService authService;
    private boolean connecte = false;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public String authentifier(String login, String motDePasse) {
        // verifier en nom de méthode (booléen?) de la lecture des identifiants (json)
        // et encryptage BCrypt puis renvoi true ou false pour compter les échecs
        //
        connecte = authService.verifier(login, motDePasse);
        if (connecte) {
            return "Connexion réussie. Bienvenue, " + login + ".";
        }
        // getTentativesRestantes() sera le int pour décompter les tentatives
        // dans le Authservice
        if (authService.getTentativesRestantes() <= 0) {
            return "Identifiants incorrects. Nombre de tentatives atteinte.\nFermeture de l'application.";
        }
        return "Identifiants incorrects. Tentatives restantes : " + authService.getTentativesRestantes();
    }

    public boolean estConnecte() {
        return connecte;
    }

    public boolean tentativesEpuisees() {
        return authService.getTentativesRestantes() <= 0;
    }
}
