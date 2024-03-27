package flights;

import users.AirlineAdmin;
import users.AirportAdmin;
import users.EndUser;
import users.User;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Flights {
    private static volatile Flights INSTANCE;
    private static ArrayList<Flight> flights;
    private static FlightDBController flightDBController;

    private Flights() {
        flights = new ArrayList<>();
        flightDBController = new FlightDBController();
    }

    public static Flights getInstance() {
        if (INSTANCE == null) {
            synchronized (Flights.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Flights();
                }
            }
        }

        return INSTANCE;
    }

    public static ArrayList<Flight> getFlights() {
        return flights;
    }

    public static void setFlights(Flight flight) {
        flights.add(flight);
    }


    // public static void registerFlight(int number, Airport source, Airport destination, String departureTime, String arrivalTime, User user){
    public static void registerFlight(Airport source, Airport destination, String departureTime, String arrivalTime, User user) throws SQLException, ClassNotFoundException {
        Flight newFlight = new Flight();
        newFlight.setNumber(-1);
        newFlight.setSource(source);
        newFlight.setDestination(destination);
        newFlight.setScheduledDepartTime(departureTime);
        newFlight.setScheduledArrivalTime(arrivalTime);

        String url = "jdbc:sqlite:src/Database/AirportSimulation.db";

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement stm = conn.createStatement();
            Statement stm2 = conn.createStatement();
            Statement stm3 = conn.createStatement();
            ResultSet rs0 = stm.executeQuery("SELECT * FROM Airport");
            int code = -1;

            while (rs0.next()) {
                String un = rs0.getString("airport_code");
                if (source.getCode().equals(un)) {
                    code = rs0.getInt("airport_id");
                    break;
                }
            }
            rs0.close();
            ResultSet rs1 = stm.executeQuery("SELECT * FROM AirportFleet");
            ResultSet rs2 = stm2.executeQuery("SELECT * FROM AirlineFleet");
            boolean aircraftAvailable = false;
            while (rs1.next()) {
                aircraftAvailable = false;
                if (code == rs1.getInt("airport_id")) {
                    if(user instanceof AirportAdmin){
                        ResultSet rs3 = stm3.executeQuery("SELECT * FROM Aircraft WHERE aircraft_id=" + rs1.getInt("aircraft_id"));
                        if(rs3.getInt("is_private") == 1){
                            aircraftAvailable = true;
                            newFlight.setAirline(new Airline("Private Airline"));
                            newFlight.setAircraft(new Aircraft(Integer.toString(rs3.getInt("aircraft_id")), "landed", true));
                            rs3.close();
                            break;
                        }
                    }else if (user instanceof AirlineAdmin){
                        rs2 = stm2.executeQuery("SELECT * FROM AirlineFleet");
                        while(rs2.next()){
                            if(rs1.getInt("aircraft_id") == rs2.getInt("aircraft_id")){
                                aircraftAvailable = true;
                                newFlight.setAirline(((AirlineAdmin) user).getAirline());
                                newFlight.setAircraft(new Aircraft(Integer.toString(rs2.getInt("aircraft_id")), "landed", false));
                                break;
                            }
                        }
                    }

                    if(aircraftAvailable){
                        break;
                    }
                }
            }

            stm.close();
            stm2.close();
            stm3.close();
            rs1.close();
            rs2.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Aircraft assignment error");
            System.out.println(e.getMessage());
        }

        if (verifyFlight(newFlight)) {
            setFlights(newFlight);

            addFlightToDb(newFlight);

            //Put the flight number back into newFlight
            try {
                Connection conn = DriverManager.getConnection(url);

                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Airport");
                int source_id = -1;
                // int city_id = -1;
                while (rs.next()) {
                    if (newFlight.getSource().getCode().equals(rs.getString("airport_code"))) {
                        source_id = rs.getInt("airport_id");
                        //city_id = rs.getInt("city_id");
                        break;
                    }
                }

                rs.close();
                stm.close();

                Statement stm1 = conn.createStatement();
                ResultSet rs1 = stm1.executeQuery("SELECT * FROM Flight");
                while (rs1.next()) {
                    if (newFlight.getScheduledDepartTime().equals(rs1.getString("scheduledDepartTime")) && source_id == rs1.getInt("source_id")) {
                        newFlight.setNumber((rs1.getInt("flight_number")));
                        break;
                    }
                }

                rs1.close();
                stm1.close();

                conn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            newFlight = null;
            System.out.println("\nFlight Cannot be added");
        }
    }

    private static boolean verifyFlight(Flight newFlight) throws SQLException, ClassNotFoundException {
        boolean allowAdd = true;
        String newSource = newFlight.getSource().getCode();
        String newDestination = newFlight.getDestination().getCode();

        for (Flight currentFlight : getFlight(newSource, newDestination)) {
            String currentSource = currentFlight.getSource().getCode();

            String currentDestination = currentFlight.getDestination().getCode();

            if (currentSource.equals(newSource)) {
                if (currentFlight.getScheduledDepartTime().equals(newFlight.getScheduledDepartTime())) {
                    allowAdd = false;
                    break;
                }
            }

            if (currentDestination.equals(newDestination)) {
                if (currentFlight.getScheduledArrivalTime().equals(newFlight.getScheduledArrivalTime())) {
                    allowAdd = false;
                    break;
                }
            }
        }

        return allowAdd;
    }

    public static void addFlightToDb(Flight newFlight) {
        //Change this to a relative url or based on machine
        String url = "jdbc:sqlite:src/Database/AirportSimulation.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stm = conn.createStatement();
            ResultSet rs;

            String newScheduledDepart = newFlight.getScheduledDepartTime();

            // If there is no actualDepartureTime, set it to scheduledDepartureTime
            String newActualDepart = newFlight.getActualDepartTime();
            if (newActualDepart.equals("")) {
                newActualDepart = newScheduledDepart;
            }
            newFlight.setActualArrivalTime("");

            String newScheduledArrival = newFlight.getScheduledArrivalTime();

            // If there is no actualArrivalTime, set it to scheduledArrivalTime
            String newActualArrival = newFlight.getActualArrivalTime();
            if (newActualArrival.equals("")) {
                newActualArrival = newScheduledArrival;
            }
            newFlight.setActualDepartTime("");

            // Get source and destination airport id
            String newSourceCode = newFlight.getSource().getCode();
            int newSourceId = -1;
            String newDestinationCode = newFlight.getDestination().getCode();
            int newDestinationId = -1;
            rs = stm.executeQuery("SELECT * FROM Airport");
            while (rs.next()) {
                if (newSourceCode.equals(rs.getString("airport_code"))) {
                    newSourceId = rs.getInt("airport_id");
                }
                if (newDestinationCode.equals(rs.getString("airport_code"))) {
                    newDestinationId = rs.getInt("airport_id");
                }
            }

            String newAircraftId = newFlight.getAircraft().getIdentifier();

            // Get airline id
            String newAirlineName = newFlight.getAirline().getName();
            int newAirlineId = -1;
            rs.close();
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM Airline");
            while (rs1.next()) {
                if (newAirlineName.equals(rs1.getString("airline_name"))) {
                    newAirlineId = rs1.getInt("airline_id");
                    break;
                }
            }
            stm1.close();

            // Add new Flight to db
            Statement stm2 = conn.createStatement();
            String sql = "INSERT INTO Flight (scheduledDepartTime, actualDepartTime, scheduledArrivalTime, actualArrivalTime, source_id, destination_id, aircraft_id, airline_id) VALUES('"
                    + newScheduledDepart + "', '" + newActualDepart + "', '"
                    + newScheduledArrival + "', '" + newActualArrival + "', " + newSourceId + ", "
                    + newDestinationId + ", '" + newAircraftId + "', " + newAirlineId + ")";
            stm2.executeUpdate(sql);
            System.out.println("Flight added");

            rs.close();
            rs1.close();
            stm.close();
            stm2.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Exception found");
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Flight> getFlight(String source, String destination) throws SQLException, ClassNotFoundException {
        return flightDBController.getFlightsFromDB(source, destination);
    }

    public static void checkPrivate(ArrayList<Flight> foundFlights, User user) {
        for (Flight flight : foundFlights) {
            if (user instanceof AirportAdmin) {
                if (flight.getAircraft().getIsPrivate()) {
                    if ((flight.getSource().getCode().equals(((AirportAdmin) user).getAirport().getCode())) || (flight.getDestination().getCode().equals(((AirportAdmin) user).getAirport().getCode()))) {
                        System.out.print(flight.toString());
                    } else {
                        System.out.println("\nYou do not have the correct permissions to view this flight!");
                    }
                } else {
                    System.out.print(flight.toString());
                }
            } else if (user instanceof EndUser) {
                if (!flight.getAircraft().getIsPrivate()) {
                    if (((EndUser) user).getIsRegistered()) {
                        System.out.print(flight.toString());
                    } else {
                        System.out.print(flight.displayNonRegisteredUser());
                    }
                } else {
                    System.out.println("\nYou do not have the correct permissions to view this flight!");
                }
            } else {
                if (!flight.getAircraft().getIsPrivate()) {
                    System.out.print(flight.toString());
                } else {
                    System.out.println("\nYou do not have the correct permissions to view this flight!");
                }
            }
        }
    }

}
