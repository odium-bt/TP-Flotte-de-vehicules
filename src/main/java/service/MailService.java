package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailService {
    private final Logger log = LogManager.getLogger(String.valueOf(MailService.class));

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final String sender;
    private final String key;

    private String destinataire;
    private String sujet;
    private String corps;

    public MailService() {
        // Identifiants de l'envoyeur :
        Properties properties = new Properties();

        String mailPropertiesPath = "mail.properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(mailPropertiesPath)) {
            if (input == null) {
                throw new FileNotFoundException("Fichier introuvable : " + mailPropertiesPath);
            }

            properties.load(input);
        } catch (IOException e) {
            log.error("Erreur lors de la lecture de mail.properties", e);
        }

        sender = properties.getProperty("sender");
        key = properties.getProperty("key");

        // Propriétés du mail :
        ObjectMapper mapper = new ObjectMapper();


        String mailFilePath = "mail.json";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(mailFilePath)) {
            if (input == null) {
                throw new FileNotFoundException("Fichier introuvable : " + mailFilePath);
            }

            JsonNode json = mapper.readTree(new File(mailFilePath));

            destinataire = json.get("destinataire").asString();
            sujet = json.get("sujet").asString();
            corps = json.get("corps").asString();
        } catch (JacksonException | IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void send(File attachment) {
        executor.submit(() -> {
            // Configuration des propriétés SMTP pour Gmail
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            // Authentification
            Session session = Session.getInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, key);
                }
            });

            try {
                // Création du message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender, "Zadig Mouquet"));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(destinataire)
                );
                message.setSubject(sujet);

                // Création du contenu
                message.setText(corps);

                // Envoi du message
                Transport.send(message);

                log.info("E-mail envoyé avec succès");

            } catch (MessagingException e) {
                log.error("Erreur lors de l'envoi de l'e-mail");
                log.trace(e);
            } catch (Exception e) {
                log.error("Erreur générale : {}", e.getMessage());
            }
        });
    }
}