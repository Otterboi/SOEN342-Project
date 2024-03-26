import Locks.Lock;
import Authentication.Authentication;
import Authentication.UserDBController;
import flights.*;
import users.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Console {
    public static List<User> users = new ArrayList<>();
    public static Airport[] airports;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //User Authentication with database
        UserDBController userDB = new UserDBController();
        Authentication auth = new Authentication(userDB);

        //Adding an Airport to database
        AirportDBController airportDB = new AirportDBController();

        // Creates Instance of Singleton Flight class
        Flights.getInstance();
        Lock lock = new Lock();
        lock.setLock("write", "unlocked");
        lock.setLock("read", "unlocked");

        // User Interface
        boolean exit = false;
        while (!exit) {
            Flights.getInstance();
            // Default user is created
            User user = new EndUser();

            // User input for either signing in or getting flight
            Scanner kb = new Scanner(System.in);
            System.out.print("\nType 1 to SIGN IN or Type 2 to GET FLIGHTS or Type 3 to EXIT: ");
            String option = kb.nextLine();

            //Create an instance of Authentication
            //Authentication auth = new Authentication();

            if (Integer.parseInt(option) == 1) {
                // All information for if the user decided to sign in
                System.out.print("Username: ");
                String username = kb.nextLine();
                System.out.print("Password: ");
                String password = kb.nextLine();

                // Attempts to log in the user
                ArrayList<Object> login = auth.loginUser(username, password);

                // Decides what kind of user has been logged in
                if (login.get(0).equals("system_admin")) {
                    user = (SystemAdmin) login.get(1);
                } else if (login.get(0).equals("airport_admin")) {
                    user = (AirportAdmin) login.get(1);
                } else if (login.get(0).equals("airline_admin")) {
                    user = (AirlineAdmin) login.get(1);
                } else if (login.get(0).equals("end_user")) {
                    ((EndUser) user).setIsRegistered(true);
                }
                if (!(user instanceof EndUser)) {
                    if (!(user instanceof SystemAdmin)) {
                        System.out.print("\nType 1 to SEARCH FLIGHTS or Type 2 to ADD A NEW FLIGHT: ");
                    } else {
                        System.out.print("\nType 1 to SEARCH FLIGHTS or Type 2 to ADD A NEW FLIGHT Type 4 to ADD A NEW AIRPORT: ");
                    }
                } else {
                    System.out.print("\nType 1 to SEARCH FLIGHTS: ");
                }

                String option2 = kb.nextLine();

                if (option2.equals("1")) {
                    allowRead(lock, user, kb);
                } else if (option2.equals("2")) {
                    if (!lock.getLock("write")) {
                        if (!lock.getLock("read")) {
                            lock.setLock("write", "locked");

                            System.out.print("Enter Source of Flight: ");
                            String fSource = kb.nextLine();
                            System.out.print("Enter Destination of Flight: ");
                            String fDestination = kb.nextLine();
                            System.out.print("Enter Departure Time of Flight: ");
                            String fDepartureTime = kb.nextLine();
                            System.out.print("Enter Arrival Time of Flight: ");
                            String fArrivalTime = kb.nextLine();

                            Flights.registerFlight(getAirportByCode(fSource), getAirportByCode(fDestination), fDepartureTime, fArrivalTime, user);
                            lock.setLock("write", "unlocked");
                        } else {
                            System.out.println("Someone is already interacting with the database!\nPlease try again later!");
                        }
                    } else {
                        System.out.println("Someone is already interacting with the database!\nPlease try again later!");
                    }

                } else {
                    //Add airport UI
                    System.out.print("Enter New Airport Name: ");
                    String aName = kb.nextLine();
                    System.out.print("Enter New Airport Code: ");
                    String aCode = kb.nextLine();
                    System.out.print("Pick the following cities: Montreal, New York, Cancun, Reykjavik: ");
                    String cityName = kb.nextLine();

                    airportDB.addAirport(aName, aCode, cityName);
                }

            } else if (Integer.parseInt(option) == 2) {
                allowRead(lock, user, kb);
            } else {
                exit = true;
            }
        }
    }

    private static void allowRead(Lock lock, User user, Scanner kb) throws SQLException, ClassNotFoundException {
        if (!lock.getLock("write")) {
            lock.setLock("read", "locked");

            System.out.print("Enter Source of Flight: ");
            String source = kb.nextLine();
            System.out.print("Enter Destination of Flight: ");
            String destination = kb.nextLine();

            // Checks if user has authentication to view the flights
            Flights.checkPrivate(Flights.getFlight(source, destination), user);

            lock.setLock("read", "unlocked");
        } else {
            System.out.println("Someone is already interacting with the database!\nPlease try again later!");
        }
    }

    private static Airport getAirportByCode(String code) throws SQLException {

        String url = "jdbc:sqlite:src/Database/AirportSimulation.db";

        Airport newAirport = new Airport();
        try {
            Connection conn = DriverManager.getConnection(url);

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Airport");


            while (rs.next()) {
                String un = rs.getString("airport_code");
                if (un.equals(code)) {
                    newAirport = new Airport(rs.getString("airport_name"), code, new City(), null);
                    break;
                }
            }
            rs.close();
            stm.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return newAirport;
    }

    private static String getLock() {
        String lock = "";

        try {
            File file = new File("src/WriteLock.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                lock = fileScanner.nextLine();
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return lock;
    }

    private static void setLock(String lock) {
        try {
            FileWriter writer = new FileWriter("src/WriteLock.txt");
            writer.write(lock);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}