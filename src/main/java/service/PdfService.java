package service;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import dao.ReservationDAO;
import modeles.Flotte;
import modeles.Vehicule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PdfService {
    private final Logger log = LogManager.getLogger(String.valueOf(PdfService.class));

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final ReservationDAO reservationDAO;
    private final Flotte flotte;

    private final String path = "etat-flotte.pdf";

    public PdfService(ReservationDAO reservationDAO, Flotte flotte) {
        this.reservationDAO = reservationDAO;
        this.flotte = flotte;
    }

    public void ecrireFlotteStatut() {
        executor.submit(() -> {
            try {
                // Gestionnaire de l'écriture dans le fichier pdf
                PdfWriter writer = new PdfWriter(path);
                // Création d'un document avec plusieurs pages
                PdfDocument pdfDoc = new PdfDocument(writer);
                // Création du contenu de la page du document
                Document doc = new Document(pdfDoc);

                // Couleurs
                DeviceRgb bleu = new DeviceRgb(35, 60, 89);
                DeviceRgb gris = new DeviceRgb(100, 116, 139);
                DeviceRgb vert = new DeviceRgb(22, 163, 74);
                DeviceRgb rouge = new DeviceRgb(220, 38, 38);

                // Titre principal
                Paragraph titre = new Paragraph("ÉTAT DE LA FLOTTE")
                        .setFontSize(22)
                        .setFontColor(bleu)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(5);

                doc.add(titre);

                // Sous-titre
                doc.add(new Paragraph("RAPPORT DE DISPONIBILITÉ DES VÉHICULES")
                        .setFontSize(10)
                        .setFontColor(gris)
                        .setTextAlignment(TextAlignment.CENTER));

                // Date de génération
                doc.add(new Paragraph("Généré le " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .setFontSize(9)
                        .setFontColor(gris)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(25));

                int compteur = 1;

                for (Vehicule vehicule : flotte.getListeVehicules()) {

                    // En-tête du véhicule
                    doc.add(new Paragraph("Véhicule n°" + compteur++)
                            .setFontSize(15)
                            .setFontColor(bleu)
                            .setMarginBottom(8));

                    // Tableau des informations
                    Table table = new Table(UnitValue.createPercentArray(new float[]{35, 65})).useAllAvailableWidth();
                    ajouterLigne(table, "Immatriculation", vehicule.getImmatriculation());
                    ajouterLigne(table, "Marque", vehicule.getMarque());
                    ajouterLigne(table, "Modèle", vehicule.getModele());
                    ajouterLigne(table, "Mise en circulation", vehicule.getDateMiseEnCirculation().toString());
                    ajouterLigne(table, "Kilométrage", vehicule.getKilometrage() + " km");
                    doc.add(table);

                    // Statut du véhicule
                    boolean enDeplacement = reservationDAO.estEnDeplacement(vehicule.getId());
                    Paragraph statut = new Paragraph("Disponibilité : "
                            + (enDeplacement ? "En déplacement" : "Disponible"))
                            .setFontSize(11)
                            .setFontColor(enDeplacement ? rouge : vert)
                            .setMarginTop(10)
                            .setMarginBottom(20);
                    doc.add(statut);

                    // Séparateur
                    doc.add(new Paragraph("────────────────────────────────────────")
                            .setFontColor(gris)
                            .setMarginBottom(15));
                }

                doc.close();

                log.info("Fichier \"{}\" écrit avec succès", path);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                log.trace(e.getStackTrace());
            }
        });
    }

    private void ajouterLigne(Table table, String libelle, String valeur) {
        DeviceRgb grisClair = new DeviceRgb(241, 245, 249);

        Cell celluleLibelle = new Cell()
                .add(new Paragraph(libelle).setFontSize(10))
                .setBackgroundColor(grisClair);

        Cell celluleValeur = new Cell()
                .add(new Paragraph(valeur).setFontSize(10));

        table.addCell(celluleLibelle);
        table.addCell(celluleValeur);
    }
}
