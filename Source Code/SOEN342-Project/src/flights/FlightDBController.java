package flights;

import java.sql.*;
import java.util.ArrayList;

public class FlightDBController {

    private static String URL; ;

    public FlightDBController() {
        URL = "jdbc:sqlite:src/Database/AirportSimulation.db";
    }

    public static ArrayList<Flight> getFlightsFromDB(String source, String destination) throws SQLException, ClassNotFoundException {
        ArrayList<Flight> foundFlights = new ArrayList<>();

        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(URL);
        String sql = "SELECT f.flight_number, f.scheduledDepartTime, f.actualDepartTime, f.scheduledArrivalTime, f.actualArrivalTime, \n" +
                "s.airport_code AS source_code,\n" +
                "d.airport_code AS destination_code, cd.city_name AS destination_city_name, cd.country AS city_country, cd.city_temperature AS destination_temp, \n" +
                "ac.aircraft_id, ac.aircraft_name, ac.aircraft_status, ac.is_private, \n" +
                "al.airline_name \n" +
                "FROM Flight f\n" +
                "INNER JOIN Airport s ON s.airport_id = f.source_id\n" +
                "INNER JOIN Airport d ON d.airport_id = f.destination_id\n" +
                "INNER JOIN Aircraft ac ON ac.aircraft_id = f.aircraft_id\n" +
                "INNER JOIN Airline al ON al.airline_id = f.airline_id\n" +
                "INNER JOIN City cd ON cd.city_id = d.city_id\n" +
                "WHERE s.airport_code = ? OR d.airport_code = ?;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, source);
        ps.setString(2, destination);
        ResultSet rs = ps.executeQuery();

        boolean isPrivate = false;

        while (rs.next()) {
            if(source.equals(rs.getString("source_code")) && destination.equals(rs.getString("destination_code"))){
                Flight flight = new Flight();
                City destinationCity = new City();
                Airport sourceAirport = new Airport();
                Airport destinationAirport = new Airport();
                Airline airline = new Airline();
                Aircraft aircraft = new Aircraft();

                // Information about the flight
                int flightID = rs.getInt("flight_number");
                String scheduledDepartTime = rs.getString("scheduledDepartTime");
                String actualDepartTime = rs.getString("actualDepartTime");
                String scheduledArrivalTime = rs.getString("scheduledArrivalTime");
                String actualArrivalTime = rs.getString("actualArrivalTime");

                flight.setNumber(flightID);
                flight.setScheduledDepartTime(scheduledDepartTime);
                flight.setActualDepartTime(actualDepartTime);
                flight.setScheduledArrivalTime(scheduledArrivalTime);
                flight.setActualArrivalTime(actualArrivalTime);

                // Information about the source and destination airports
                String sourceAirportCode = rs.getString("source_code");

                sourceAirport.setCode(sourceAirportCode);

                String destAirportCode = rs.getString("destination_code");
                String destCityName = rs.getString("destination_city_name");
                int destCityTemp = rs.getInt("destination_temp");
                String destCountry = rs.getString("city_country");

                destinationAirport.setCode(destAirportCode);
                destinationCity.setName(destCityName);
                destinationCity.setTemperature(destCityTemp);
                destinationCity.setCountry(destCountry);
                destinationAirport.setCity(destinationCity);

                // Information about the aircraft
                int aircraftID = rs.getInt("aircraft_id");
                String aircraftStatus = rs.getString("aircraft_status");
                int aircraftIsPrivateDB = rs.getInt("is_private");

                if (aircraftIsPrivateDB == 0) {
                    isPrivate = false;
                } else {
                    isPrivate = true;
                }

                aircraft.setIdentifier(aircraftID+"");
                aircraft.setStatus(aircraftStatus);
                aircraft.setPrivate(isPrivate);

                // Information about the airline
                String airlineName = rs.getString("airline_name");
                airline.setName(airlineName);

                flight.setSource(sourceAirport);
                flight.setDestination(destinationAirport);
                flight.setAirline(airline);
                flight.setAircraft(aircraft);

                foundFlights.add(flight);
            }
        }

        rs.close();
        ps.close();
        connection.close();
        return foundFlights;
    }
}
