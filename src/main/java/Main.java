import controllers.FlotteController;
import controllers.ReservationController;
import dao.DAOFactory;
import service.ReservationService;
import service.VehiculeService;
import vue.MenuConsole;

public class Main {
    static void main(String[] args) {

        DAOFactory factory = DAOFactory.getInstance();

        ReservationService reservationService = new ReservationService(factory.getVehiculeDAO(), factory.getReservationDAO());

        VehiculeService vehiculeService = new VehiculeService(factory.getVehiculeDAO(), factory.getReservationDAO());

        ReservationController reservationController = new ReservationController(reservationService);
        FlotteController flotteController = new FlotteController(VehiculeService);

        new MenuConsole(flotteController, reservationController).demarrer();
    }
}
