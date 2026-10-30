package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AuthService {
    private final Logger log = LogManager.getLogger(String.valueOf(AuthService.class));

    private int tentativesRestantes = 3;

    public int getTentativesRestantes() {
        return tentativesRestantes;
    }

    public void setTentativesRestantes(int tentativesRestantes) {
        this.tentativesRestantes = tentativesRestantes;
    }

    /**
     * Tente d'identifier l'utilisateur avec les informations entrées.
     *
     * @param inputLogin Login entré par l'utilisateur
     * @param inputPw    Mot de passe entré par l'utilisateur
     * @return {@code true} si les identifiants sont valides<br>
     * {@code false} si les identifiants sont invalides
     */
    public boolean verifier(String inputLogin, String inputPw) throws RegleGestionException {
        if (getTentativesRestantes() == 0) {
            throw new RegleGestionException("Aucune tentative restante");
        }

        ObjectMapper mapper = new ObjectMapper();

        String userFilePath = "user.json";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(userFilePath)) {
            if (input == null) {
                throw new FileNotFoundException("Fichier introuvable : " + userFilePath);
            }

            JsonNode json = mapper.readTree(input);

            String login = json.get("login").asString();
            String pwHash = json.get("motDePasseHash").asString();

            if ((inputLogin.equals(login) && BCrypt.checkpw(inputPw, pwHash))) {
                setTentativesRestantes(3);
                log.info("Authentification réussie");
                return true;
            } else {
                setTentativesRestantes(getTentativesRestantes() - 1);
                log.info("Authentification échouée : reste {} tentative(s)", getTentativesRestantes());
            }
        } catch (JacksonException | IOException e) {
            log.error("Impossible de lire les contenus de {}", userFilePath);
            log.trace(e.getStackTrace());
        }
        return false;
    }
}
