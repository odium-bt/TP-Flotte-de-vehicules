package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;

public class AuthService {
    private final Logger log = LogManager.getLogger(String.valueOf(AuthService.class));

    private int counter = 0;

    /**
     * Tente d'identifier l'utilisateur avec les informations entrées.
     *
     * @param inputLogin Login entré par l'utilisateur
     * @param inputPw    Mot de passe entré par l'utilisateur
     * @return {@code int} - 0 si login réussi, sinon retourne le nombre de tentatives échouées,
     * ou -1 si la lecture du fichier des identifiants a échoué
     */
    public int login(String inputLogin, String inputPw) {
        ObjectMapper mapper = new ObjectMapper();

        String userFilePath = "user.json";

        try {
            JsonNode json = mapper.readTree(new File(userFilePath));

            String login = json.get("login").asString();
            String pwHash = json.get("motDePasseHash").asString();

            if ((inputLogin.equals(login) && BCrypt.checkpw(inputPw, pwHash))) {
                counter = 0;
                log.info("Authentification réussie");
                return 0;
            } else {
                counter++;
                log.info("Authentification échouée : {} tentative(s) incorrecte(s)", counter);
            }
        } catch (JacksonException e) {
            log.error("Impossible de lire les contenus de {}", userFilePath);
            log.trace(e.getStackTrace());
        }
        return -1;
    }
}
