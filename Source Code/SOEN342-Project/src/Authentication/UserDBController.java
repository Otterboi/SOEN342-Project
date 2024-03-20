package Authentication;

import flights.Aircraft;
import flights.Airline;
import flights.Airport;
import flights.City;
import users.AirlineAdmin;
import users.AirportAdmin;
import users.EndUser;
import users.SystemAdmin;

import java.sql.*;
import java.util.ArrayList;

public class UserDBController {
    private static final String url = "jdbc:sqlite:src/Database/AirportSimulation.db";

    public Object[] authenticateUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url)) {
            // SQL query to check if username and password exist in the database
            String sql = "SELECT user_id, username, password, user_type FROM User WHERE username = ? AND password = ? ";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                // If the result set has at least one row, the user exists
                if (rs.next()) {
                    /*int count = rs.getInt(1);
                    return count > 0;*/
                    String userType = rs.getString("user_type");
                    String user = rs.getString("username");
                    String pass = rs.getString("password");
                    int userid = rs.getInt("user_id");

                    if(userType.equals("system_admin"))
                    {
                        SystemAdmin systemAdmin = new SystemAdmin(user, pass); // Assuming you have a SystemAdmin class
                        return new Object[]{userType, systemAdmin};
                    }
                    else if(userType.equals("airport_admin"))
                    {
                        String sql2 = "SELECT airport_id, airport_name, airport_code, city_id FROM Airport WHERE admin_id = ?";
                        PreparedStatement ps2 = conn.prepareStatement(sql2);
                        ps2.setInt(1, userid);
                        ResultSet rs2 = ps2.executeQuery();
                        int airportid = rs2.getInt("airport_id");
                        String airportName = rs2.getString("airport_name");
                        String airportCode = rs2.getString("airport_code");
                        int cityid = rs2.getInt("city_id");

                        String sql3 = "SELECT city_name, country, city_temperature FROM City WHERE city_id = ?";
                        PreparedStatement ps3 = conn.prepareStatement(sql3);
                        ps3.setInt(1, cityid);
                        ResultSet rs3 = ps3.executeQuery();
                        String cityname = rs3.getString("city_name");
                        String citycountry = rs3.getString("country");
                        int citytemp = rs3.getInt("city_temperature");

                        String sql4 = "SELECT ac.aircraft_name, ac.aircraft_id, ac.aircraft_status, ac.is_private FROM AirportFleet af\n" +
                                      "INNER JOIN Airport a ON a.airport_id = af.airport_id\n" +
                                      "INNER JOIN Aircraft ac ON ac.aircraft_id = af.aircraft_id\n" +
                                      "WHERE a.airport_id = ?";
                        PreparedStatement ps4 = conn.prepareStatement(sql4);
                        ps4.setInt(1, airportid);
                        ResultSet rs4 = ps4.executeQuery();

                        ArrayList <Aircraft> fleet = new ArrayList<>();
                        boolean flag = false;

                        while(rs4.next())
                        {
                            String aircraftname = rs4.getString("aircraft_name");
                            System.out.println(aircraftname);
                            String aircraftstatus = rs4.getString("aircraft_status");
                            int isPrivate = rs4.getInt("is_private");

                            if(isPrivate == 0){
                                flag = false;
                            }
                            else{
                                flag = true;
                            }
                            fleet.add(new Aircraft(aircraftname, aircraftstatus, flag));
                        }

                        City city = new City(cityname, citycountry, citytemp);
                        Airport airport = new Airport(airportName, airportCode, city, fleet);

                        AirportAdmin airportAdmin = new AirportAdmin(username, password, airport); // Assuming you have an AirportAdmin class
                        return new Object[]{userType, airportAdmin};
                    }
                    else if(userType.equals("airline_admin"))
                    {
                        String sql5 = "SELECT airline_id, airline_name FROM Airline WHERE admin_id = ?";
                        PreparedStatement ps5 = conn.prepareStatement(sql5);
                        ps5.setInt(1, userid);
                        ResultSet rs5 = ps5.executeQuery();
                        int airlineid = rs5.getInt("airline_id");
                        String airlinename = rs5.getString("airline_name");

                        String sql6 = "SELECT ac.aircraft_name, ac.aircraft_status, ac.is_private FROM AirlineFleet af\n" +
                                      "INNER JOIN Airline a ON a.airline_id = af.airline_id\n" +
                                      "INNER JOIN Aircraft ac ON ac.aircraft_id = af.aircraft_id\n" +
                                      "WHERE a.airline_id = ?;";
                        PreparedStatement ps6 = conn.prepareStatement(sql6);
                        ps6.setInt(1, airlineid);
                        ResultSet rs6 = ps6.executeQuery();

                        ArrayList <Aircraft> fleet = new ArrayList<>();
                        boolean flag = false;

                        while(rs6.next())
                        {
                            String aircraftname = rs6.getString("aircraft_name");
                            String aircraftstatus = rs6.getString("aircraft_status");
                            int isPrivate = rs6.getInt("is_private");

                            if(isPrivate == 0){
                                flag = false;
                            }
                            else{
                                flag = true;
                            }

                            fleet.add(new Aircraft(aircraftname, aircraftstatus, flag));
                        }

                        Airline airline = new Airline(airlinename, fleet);
                        AirlineAdmin airlineAdmin = new AirlineAdmin(username, password, airline); // Assuming you have an AirlineAdmin class
                        return new Object[]{userType, airlineAdmin};
                    }
                    else
                    {
                        EndUser endUser = new EndUser(username, password, true); // Assuming you have an EndUser class
                        return new Object[]{userType, endUser};
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
