import controllers.FlotteController;
import controllers.ReservationController;
import dao.DAOFactory;
import modeles.Flotte;
import service.ReservationService;
import service.VehiculeService;
import vue.MenuConsole;

public class Main {
    static void main(String[] args) {

        DAOFactory factory = DAOFactory.getInstance();

        Flotte flotte = new Flotte();

        ReservationService reservationService = new ReservationService(factory.getVehiculeDAO(), factory.getReservationDAO(), factory.getSalarieDAO());

        VehiculeService vehiculeService = new VehiculeService(factory.getVehiculeDAO(), factory.getReservationDAO(), flotte);

        ReservationController reservationController = new ReservationController(reservationService);
        FlotteController flotteController = new FlotteController(vehiculeService);

        new MenuConsole(flotteController, reservationController).demarrer();
    }
}
