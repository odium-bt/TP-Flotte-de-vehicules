package controllers;

import service.AuthService;
import service.RegleGestionException;
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

            try {
                if (authService.verifier(login, motDePasse)) {
                    vue.afficherSucces("Connexion réussie.");
                    return true;
                } else {
                    vue.afficherRefus("Identifiants incorrects. Tentatives restantes : " + authService.getTentativesRestantes());
                }
            } catch (RegleGestionException e) {
                vue.afficherRefus(e.getMessage());
                return false;
            }
        }
    }
}
