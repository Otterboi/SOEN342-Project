package flights;

import users.AirlineAdmin;
import users.AirportAdmin;
import users.EndUser;
import users.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class Flights {
    private static volatile Flights INSTANCE;
    private static ArrayList<Flight> flights;
    private static FlightDBController flightDBController;

    private Flights(){
        flights = new ArrayList<>();
        flightDBController = new FlightDBController();
    }

    public static Flights getInstance(){
        if(INSTANCE == null){
            synchronized (Flights.class){
                if (INSTANCE == null){
                    INSTANCE = new Flights();
                }
            }
        }

        return INSTANCE;
    }

    public static ArrayList<Flight> getFlights(){
        return flights;
    }

    public static void setFlights(Flight flight){
        flights.add(flight);
    }


    public static void registerFlight(int number, Airport source, Airport destination, String departureTime, String arrivalTime, User user){
        Flight newFlight = new Flight();
        newFlight.setNumber(number);
        newFlight.setSource(source);
        newFlight.setDestination(destination);
        newFlight.setScheduledDepartTime(departureTime);
        newFlight.setScheduledArrivalTime(arrivalTime);

        if(user instanceof AirlineAdmin){
            Airline airline = ((AirlineAdmin) user).getAirline();
            newFlight.setAirline(airline);

            for(Aircraft aircraft: source.getFleet()){
                if(airline.getFleet().contains(aircraft)){
                    newFlight.setAircraft(aircraft);
                    source.getFleet().remove(aircraft);
                    break;
                }
            }
        }else if (user instanceof AirportAdmin){
            for(Aircraft aircraft: source.getFleet()){
                if(aircraft.getIsPrivate()){
                    newFlight.setAirline(new Airline("Private Airline"));
                    newFlight.setAircraft(aircraft);
                    source.getFleet().remove(aircraft);
                    break;
                }
            }
        }

        if(verifyFlight(newFlight)){
            setFlights(newFlight);
            System.out.println("\nFlight Added");
        }else{
            newFlight = null;
            System.out.println("\nFlight Cannot be added");
        }

    }

    private static boolean verifyFlight(Flight newFlight){
        boolean allowAdd = true;

        for(Flight currentFlight: getFlights()){
            String newSource = newFlight.getSource().getCode();
            String currentSource = currentFlight.getSource().getCode();

            String newDestination = newFlight.getDestination().getCode();
            String currentDestination = currentFlight.getDestination().getCode();

            if(currentSource.equals(newSource)){
                if(currentFlight.getScheduledDepartTime().equals(newFlight.getScheduledDepartTime())){
                   allowAdd = false;
                   break;
                }
            }

            if(currentDestination.equals(newDestination)){
                if(currentFlight.getScheduledArrivalTime().equals(newFlight.getScheduledArrivalTime())){
                    allowAdd = false;
                    break;
                }
            }
        }

        return allowAdd;
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
                    }else{
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
                }else{
                    System.out.println("\nYou do not have the correct permissions to view this flight!");
                }
            } else {
                if (!flight.getAircraft().getIsPrivate()) {
                    System.out.print(flight.toString());
                }else{
                    System.out.println("\nYou do not have the correct permissions to view this flight!");
                }
            }
        }
    }

}
