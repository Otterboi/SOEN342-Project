import flights.*;
import users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {
    public static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Flights.getInstance();
        User user = new EndUser();

        City city1 = new City("city1", "country1", 15.0);
        City city2 = new City("city2", "country2", -126.3);
        City city3 = new City("city3", "country3", 60.45);
        City city4 = new City("city4", "country4", 0.0);

        Airport air1 = new Airport("air1", "AIR", city1);
        Airport air2 = new Airport("air2", "AIRE", city2);
        Airport air3 = new Airport("air3", "AIRES", city3);
        Airport air4 = new Airport("air4", "AIRESE", city4);

        Aircraft p1 = new Aircraft("1", "flying", false);
        Aircraft p2 = new Aircraft("2", "landed", true);

        Airline airline1 = new Airline("Airline1");
        Airline airline2 = new Airline("Airline2");

        Flight f1 = new Flight("FLIGHT1", air1, air2, "1am", "3am", "12pm", "12:30pm", p1, airline1);
        Flight f2 = new Flight("FLIGHT1", air3, air4, "5am", "5am", "1pm", "2pm", p2, airline2);

        Flights.setFlights(f1);
        Flights.setFlights(f2);

        Scanner kb = new Scanner(System.in);
        System.out.print("Type 1 to Sign in or Type 2 to get flights: ");
        int option = kb.nextInt();
        kb.nextLine();

        //Create an instance of Authentication
        Authentication auth = new Authentication();

        // Register administrators
        User sysAdmin = new SystemAdmin("admin1", "admin1");
        User airportAdmin = new AirportAdmin("admin2", "admin2", air3);
        User airlineAdmin = new AirlineAdmin("admin3", "admin3", new Airline());

        // Register end user
        User endUser2 = new EndUser("user1", "user1", false);

        users.add(sysAdmin);
        users.add(airportAdmin);
        users.add(airlineAdmin);
        users.add(endUser2);

        if (option == 1) {
            System.out.print("Username: ");
            String username = kb.nextLine();
            System.out.print("Password: ");
            String password = kb.nextLine();

            ArrayList<Object> login = auth.login(username, password);

            if (login.get(0).equals("users.SystemAdmin")) {
                user = (SystemAdmin) login.get(1);
            } else if (login.get(0).equals("users.AirportAdmin")) {
                user = (AirportAdmin) login.get(1);
            } else if (login.get(0).equals("users.AirlineAdmin")) {
                user = (AirlineAdmin) login.get(1);
            } else if (login.get(0).equals("users.EndUser")) {
                ((EndUser) user).setIsRegistered(true);
            }

            System.out.print("Enter Source of Flight: ");
            String source = kb.nextLine();
            System.out.print("Enter Destination of Flight: ");
            String destination = kb.nextLine();

            checkPrivate(getFlights(source, destination), user);

        } else if (option == 2) {
            System.out.print("Enter Airport Code for Source: ");
            String source = kb.nextLine();
            System.out.print("Enter Airport Code for Destination: ");
            String destination = kb.nextLine();

            checkPrivate(getFlights(source, destination), user);
        }

        kb.close();
    }

    private static Flight getFlights(String source, String destination) {
        Flight foundFlight = null;
        for (Flight flight : Flights.getFlights()) {
            if (flight.getSource().getCode().equals(source) && flight.getDestination().getCode().equals(destination)) {
                foundFlight = flight;
            }
        }
        return foundFlight;
    }

    private static void checkPrivate(Flight flight, User user) {
        if (user instanceof AirportAdmin) {
            if (flight.getAircraft().getIsPrivate()) {
                if ((flight.getSource().getCode().equals(((AirportAdmin) user).getAirport().getCode())) || (flight.getDestination().getCode().equals(((AirportAdmin) user).getAirport().getCode()))) {
                    System.out.println("AIRPORT ADMIN PRIVATE: " + flight.toString());
                }
            } else {
                System.out.println("AIRPORT ADMIN PUBLIC: " + flight.toString());
            }
        } else if (user instanceof EndUser) {
            if (!flight.getAircraft().getIsPrivate()) {
                if (((EndUser) user).getIsRegistered()) {
                    System.out.println("(PUBLIC) END-USER: " + flight.toString());
                } else {
                    System.out.println("PRINT SOMETHING ELSE");
                }
            }
        } else {
            if (!flight.getAircraft().getIsPrivate()) {
                System.out.println("(PUBLIC) OTHER ADMIN: " + flight.toString());
            }
        }
    }
}