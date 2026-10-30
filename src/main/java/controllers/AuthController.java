package controllers;

import service.AuthService;
import vue.MenuConsole;

public class AuthController {

    private static final int MAX_TENTATIVES = 3;
    private final AuthService authService;
    private final MenuConsole vue;

    public AuthController(AuthService authService, MenuConsole vue) {
        this.authService = authService;
        this.vue = vue;
    }

    public boolean connecter() {

        while (true) {
            String login = vue.saisieTexte("Login");
            String motDePasse = vue.saisieTexte("Mot de passe");
            int resultat = authService.login(login, motDePasse);

            if (resultat == 0) {
                vue.afficherSucces("Connexion réussie. Bienvenue, " + login + ".");
                return true;
            }
            if (resultat == -1) {
                vue.afficherRefus("impossible de lire le fichier des identifiants.");
                return false;
            }
            if (resultat >= MAX_TENTATIVES) {
                vue.afficherRefus("nombre maximal de tentatives atteint. Fermeture de l'application.");
                return false;
            }
            vue.afficherRefus("identifiants incorrects. Tentatives restantes : " + (MAX_TENTATIVES - resultat));
        }
    }
}
