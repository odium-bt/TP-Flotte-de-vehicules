package controllers;

import vue.MenuConsole;

public class ApplicationController {

    private final MenuConsole vue;
    private final FlotteController flotteController;
    private final ReservationController reservationController;
    private final EtatController etatController;

    // Constructeur qui reçoit la vue et les trois contrôleurs
    public ApplicationController(MenuConsole vue, FlotteController flotteController, ReservationController reservationController, EtatController etatController) {
        this.vue = vue;
        this.flotteController = flotteController;
        this.reservationController = reservationController;
        this.etatController = etatController;
    }

    public void demarrer() {

        int choix;

        do {

            choix = vue.afficherMenuEtLireChoix();
            switch (choix) {
                case 1:
                    flotteController.afficherFlotte();
                    break;
                case 2:
                    flotteController.ajouterVehicule();
                    break;
                case 3:
                    flotteController.retirerVehicule();
                    break;
                case 4:
                    flotteController.rechercherVehicule();
                    break;
                case 5:
                    flotteController.trierFlotte();
                    break;
                case 6:
                    reservationController.ajouterSalarie();
                    break;
                case 7:
                    reservationController.departReservation();
                    break;
                case 8:
                    reservationController.retourReservation();
                    break;
                case 9:
                    reservationController.vehiculeEnDeplacement();
                    break;
                case 10:
                    reservationController.historiqueSalarie();
                    break;
                case 11:
                    // TODO
                    break;
                case 12:
                    // TODO
                    break;
                case 13:
                    // TODO
                    break;
                case 0:
                    // TODO
                    break;
                default:
                    vue.afficherRefus("Choix inconnu");
            }
        } while (choix != 0);
    }
}
