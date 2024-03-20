package flights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlightDBController {

    private final String URL = "jdbc:sqlite:src/Database/AirportSimulation.db";
    private Connection connection;

    public FlightDBController(){
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Flight> getFlightsFromDB() throws SQLException {
        ArrayList<Flight> flights = new ArrayList<>();

        return flights;
    }
}
