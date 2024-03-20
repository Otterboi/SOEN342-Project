import Authentication.Authentication;
import Authentication.UserDBController;
import flights.*;
import users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {
    public static List<User> users = new ArrayList<>();


    public static void main(String[] args) throws ClassNotFoundException {

        UserDBController userDB = new UserDBController();
        Authentication auth = new Authentication(userDB);

        // Creates Instance of Singleton Flight class
        Flights.getInstance();

        Class.forName("org.sqlite.JDBC");

        /*String url = "jdbc:sqlite:src/Database/AirportSimulation.db";

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM User");

            while(rs.next()){
                String un =  rs.getString("username");
                System.out.println(un);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        // Default user is created
        User user = new EndUser();

        // Instantiating aircrafts
        Aircraft p1 = new Aircraft("1", "landed", false);
        Aircraft p2 = new Aircraft("2", "landed", true);
        Aircraft p3 = new Aircraft("3", "landed", true);
        Aircraft p4 = new Aircraft("4", "landed", true);
        Aircraft p5 = new Aircraft("5", "landed", true);
        Aircraft p6 = new Aircraft("6", "landed", false);
        Aircraft p7 = new Aircraft("7", "landed", false);
        Aircraft p8 = new Aircraft("8", "landed", false);
        Aircraft p9 = new Aircraft("9", "landed", false);
        Aircraft p10 = new Aircraft("10", "landed", false);
        Aircraft p11 = new Aircraft("11", "landed", false);
        Aircraft p12 = new Aircraft("12", "landed", false);
        Aircraft p13 = new Aircraft("13", "landed", false);

        ArrayList<Aircraft> air1Planes = new ArrayList<>();
        air1Planes.add(p2);
        air1Planes.add(p6);
        air1Planes.add(p7);

        ArrayList<Aircraft> air2Planes = new ArrayList<>();
        air2Planes.add(p3);
        air2Planes.add(p8);
        air2Planes.add(p9);

        ArrayList<Aircraft> air3Planes = new ArrayList<>();
        air3Planes.add(p4);
        air3Planes.add(p10);
        air3Planes.add(p11);

        ArrayList<Aircraft> air4Planes = new ArrayList<>();
        air4Planes.add(p5);
        air4Planes.add(p12);
        air4Planes.add(p13);

        ArrayList<Aircraft> airlinePlanes = new ArrayList<>();
        airlinePlanes.add(p1);
        airlinePlanes.add(p6);
        airlinePlanes.add(p7);
        airlinePlanes.add(p8);
        airlinePlanes.add(p9);
        airlinePlanes.add(p10);
        airlinePlanes.add(p11);
        airlinePlanes.add(p12);
        airlinePlanes.add(p13);

        // Instantiating cities
        City city1 = new City("city1", "country1", 15.0);
        City city2 = new City("city2", "country2", -126.3);
        City city3 = new City("city3", "country3", 60.45);
        City city4 = new City("city4", "country4", 0.0);

        // Instantiating airports
        Airport air1 = new Airport("air1", "AIR", city1, air1Planes);
        Airport air2 = new Airport("air2", "YUL", city2, air2Planes);
        Airport air3 = new Airport("air3", "AIRES", city3, air3Planes);
        Airport air4 = new Airport("air4", "AIRESE", city4, air4Planes);

        Airport[] airports = {air1, air2, air3, air4};

        // Instantiating airlines
        Airline airline1 = new Airline("Airline1", airlinePlanes);
        Airline airline2 = new Airline("Private Airline");

        // Instantiating flights
        Flight f1 = new Flight("FLIGHT1", air1, air2, "1am", "3am", "12pm", "5am", p1, airline1);
        Flight f2 = new Flight("FLIGHT2", air2, air3, "5am", "5am", "1pm", "2pm", p2, airline2);

        // Adding flights to Flights
        Flights.setFlights(f1);
        Flights.setFlights(f2);

        // User input for either signing in or getting flight
        Scanner kb = new Scanner(System.in);
        System.out.print("Type 1 to Sign in or Type 2 to get flights: ");
        int option = kb.nextInt();
        kb.nextLine();

        //Create an instance of Authentication.Authentication
        //Authentication auth = new Authentication();

        /*// Instantiating administrators
        User sysAdmin = new SystemAdmin("admin1", "admin1");
        User airportAdmin = new AirportAdmin("admin2", "admin2", air1);
        User airlineAdmin = new AirlineAdmin("admin3", "admin3", airline1);*/

        // Instantiating end user
        User endUser2 = new EndUser("user1", "user1", false);

        /*// Adding useres to users list
        users.add(sysAdmin);
        users.add(airportAdmin);
        users.add(airlineAdmin);
        users.add(endUser2);*/

        if (option == 1) {
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
            System.out.print("Type 1 to Search Flights or Type 2 to Add a New Flight: ");
            String option2 = kb.nextLine();

            if(option2.equals("1")){
                System.out.print("Enter Source of Flight: ");
                String source = kb.nextLine();
                System.out.print("Enter Destination of Flight: ");
                String destination = kb.nextLine();

                // Checks if user has authentication to view the flights
                Flights.checkPrivate(Flights.getFlight(source, destination), user);
            }else{
                System.out.print("Enter Flight Number: ");
                String fNumber = kb.nextLine();
                System.out.print("Enter Source of Flight: ");
                String fSource = kb.nextLine();
                System.out.print("Enter Destination of Flight: ");
                String fDestination = kb.nextLine();
                System.out.print("Enter Departure Time of Flight: ");
                String fDepartureTime = kb.nextLine();
                System.out.print("Enter Arrival Time of Flight: ");
                String fArrivalTime = kb.nextLine();

                Flights.registerFlight(fNumber, getAirportByCode(airports, fSource), getAirportByCode(airports, fDestination), fDepartureTime, fArrivalTime, user);
            }

        } else if (option == 2) {
            // If user is not logged in and wants to see flights
            System.out.print("Enter Airport Code for Source: ");
            String source = kb.nextLine();
            System.out.print("Enter Airport Code for Destination: ");
            String destination = kb.nextLine();

            Flights.checkPrivate(Flights.getFlight(source, destination), user);
        }

        kb.close();
    }

    private static Airport getAirportByCode(Airport[] airports, String code){

        for(int i = 0; i < airports.length; i++){
            if(airports[i].getCode().equals(code)){
                return airports[i];
            }
        }
        return null;
    }

}