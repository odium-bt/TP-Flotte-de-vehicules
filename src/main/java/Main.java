import controllers.*;
import dao.DAOFactory;
import dao.VehiculeDAOImpl;
import modeles.Flotte;
import service.AuthService;
import service.ReservationService;
import service.VehiculeService;
import vue.MenuConsole;

public class Main {
    public static void main(String[] args) {

        DAOFactory factory = DAOFactory.getInstance();
        MenuConsole vue = new MenuConsole();

        VehiculeService vehiculeService = new VehiculeService(
                factory.getVehiculeDAO(), factory.getReservationDAO(), new Flotte());

        ReservationService reservationService = new ReservationService(
                factory.getVehiculeDAO(), factory.getReservationDAO(), factory.getSalarieDAO());

        FlotteController flotteController = new FlotteController(vehiculeService, vue);
        ReservationController reservationController = new ReservationController(reservationService, vue);
        EtatController etatController = new EtatController();

        AuthController authController = new AuthController(new AuthService(), vue);
        if (authController.connecter()) {
            new ApplicationController(vue, flotteController, reservationController, etatController).demarrer();
        }

        vehiculeService.fermer();
        reservationService.fermer();
    }

    /*
        public static void main(String[] args) {
            MenuConsole vue = new MenuConsole();

            VehiculeService vehiculeService = new VehiculeService(new VehiculeDAOImpl(), null, new Flotte());
            ReservationService reservationService = new ReservationService(null, null, null);

            FlotteController flotteController = new FlotteController(vehiculeService, vue);
            ReservationController reservationController = new ReservationController(reservationService, vue);
            EtatController etatController = new EtatController();

            new ApplicationController(vue, flotteController, reservationController, etatController).demarrer();

            vehiculeService.fermer();
            reservationService.fermer();
        }

     */

}
