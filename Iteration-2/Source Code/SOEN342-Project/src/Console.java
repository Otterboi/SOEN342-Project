import flights.*;
import users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {
    public static List<Flight> flights = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static void main(String[] args) {
        User user = new EndUser();

        Airport air1 = new Airport("air1", "AIR", new City(), new ArrayList<>());
        Airport air2 = new Airport("air2", "AIRE", new City(), new ArrayList<>());
        Airport air3 = new Airport("air3", "AIRES", new City(), new ArrayList<>());
        Airport air4 = new Airport("air4", "AIRESE", new City(), new ArrayList<>());

        Aircraft p1 = new Aircraft("1", "flying", false);
        Aircraft p2 = new Aircraft("2", "landed", true);

        Flight f1 = new Flight(air1, air2, p1);
        Flight f2 = new Flight(air3, air4, p2);

        flights.add(f1);
        flights.add(f2);

        Scanner kb = new Scanner(System.in);
        System.out.print("Type 1 to Sign in or Type 2 to get flights: ");
        int option = kb.nextInt();
        kb.nextLine();

        //Create an instance of Authentication
        Authentication auth = new Authentication();

        // Register administrators
        User sysAdmin = new SystemAdmin("admin1", "admin1");
        User airportAdmin = new AirportAdmin("admin2", "admin2", air1);
        User airlineAdmin = new AirlineAdmin("admin3", "admin3", new Airline());
        User endUser1 = new EndUser();
        User endUser2 = new EndUser();
        ((EndUser) endUser2).setIsRegistered(true);

        users.add(sysAdmin);
        users.add(airportAdmin);
        users.add(airlineAdmin);
        users.add(endUser1);
        users.add(endUser2);

        if(option == 1){
            System.out.print("Username: ");
            String username = kb.nextLine();
            System.out.print("Password: ");
            String password = kb.nextLine();

            auth.login(username, password);
            // Login attempt
            if(/*auth.login(username, password)*/true){
                // Admin

                checkPrivate(getFlights("AIR", "AIRE"), user);
                checkPrivate(getFlights("AIRES", "AIRESE"), user);
            }else{
                // Registered End-User
                ((EndUser) user).setIsRegistered(true);
            }

            // get the flights searched for
            // check if planes are private
            // if private dont display to end user
            // if private check if admin if of that airport then keep it in list
            //checkPrivate(getFlights("AIR", "AIRE"), user);

        }else if (option == 2){

        }

        kb.close();
    }

    private static ArrayList<Flight> getFlights(String source, String destination){
        ArrayList<Flight> foundFlights = new ArrayList<>();
        for (Flight flight: flights){
            if(flight.getSource().getCode().equals(source) && flight.getDestination().getCode().equals(destination)){
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }

    private static void checkPrivate(ArrayList<Flight> foundFlights, User user){
        for(Flight flight: foundFlights){
            if(user instanceof AirportAdmin){
                if(flight.getAircraft().getIsPrivate()){
                    if((flight.getSource().getCode().equals(((AirportAdmin) user).getAirport().getCode())) || (flight.getDestination().getCode().equals(((AirportAdmin) user).getAirport().getCode()))){
                        System.out.println("AIRPORT ADMIN PRIVATE: " + flight.toString());
                    }
                }else{
                    System.out.println("AIRPORT ADMIN PUBLIC: " + flight.toString());
                }
            }else if (user instanceof EndUser){
                if(!flight.getAircraft().getIsPrivate()){
                    if(((EndUser) user).getIsRegistered()){
                        System.out.println("END-USER: " + flight.toString());
                    }else{
                        System.out.println("PRINT SOMETHING ELSE");
                    }
                }
            }else{
                if(!flight.getAircraft().getIsPrivate()){
                    System.out.println("OTHER ADMIN: " + flight.toString());
                }
            }
        }
    }
}