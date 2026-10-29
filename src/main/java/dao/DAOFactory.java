package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
    private static DAOFactory instance;

    private final String url;
    private final String user;
    private final String password;

    private DAOFactory() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("dao.properties")) {
            if (input == null) {
                throw new DAOException("Impossible de trouver dao.properties");
            }

            properties.load(input);
        } catch (IOException e) {
            throw new DAOException("Erreur lors de la lecture de dao.properties");
        }

        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        String driver = properties.getProperty("driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DAOException("Impossible de charger le driver JDBC : " + driver);
        }
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }

        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la connexion à la base de données");
        }
    }

    public VehiculeDAO getVehiculeDAO() {
        return new VehiculeDAOImpl();
    }

    public SalarieDAO getSalarieDAO() {
        return new SalarieDAOImpl();
    }

    public ReservationDAO getReservationDAO() {
        return new ReservationDAOImpl();
    }
}
