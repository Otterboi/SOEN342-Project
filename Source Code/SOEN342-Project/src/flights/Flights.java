package flights;

import users.AirlineAdmin;
import users.AirportAdmin;
import users.EndUser;
import users.User;

import java.util.ArrayList;

public class Flights {
    private static Flights INSTANCE;
    private static ArrayList<Flight> flights;

    private Flights(){
        flights = new ArrayList<>();
    }

    public static Flights getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Flights();
        }

        return INSTANCE;
    }

    public static ArrayList<Flight> getFlights(){
        return flights;
    }

    public static void setFlights(Flight flight){
        flights.add(flight);
    }


    public static void registerFlight(String number, Airport source, Airport destination, String departureTime, String arrivalTime, User user){
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
                }
            }
        }else if (user instanceof AirportAdmin){
            for(Aircraft aircraft: source.getFleet()){
                if(aircraft.getIsPrivate()){
                    newFlight.setAirline(new Airline("Private Airline"));
                    newFlight.setAircraft(aircraft);
                    source.getFleet().remove(aircraft);
                }
            }
        }

        if(verifyFlight(newFlight)){
            setFlights(newFlight);
            System.out.println("Flight Added");
        }else{
            newFlight = null;
            System.out.println("Flight Cannot be added");
        }

        for(Flight f : getFlights()){
            System.out.println(f.toString());
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

    public static ArrayList<Flight> getFlight(String source, String destination) {
        ArrayList<Flight> foundFlights = new ArrayList<>();
        for (Flight flight : Flights.getFlights()) {
            if (flight.getSource().getCode().equals(source) && flight.getDestination().getCode().equals(destination)) {
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }

    public static void checkPrivate(ArrayList<Flight> foundFlights, User user) {
        for (Flight flight : foundFlights) {
            if (user instanceof AirportAdmin) {
                if (flight.getAircraft().getIsPrivate()) {
                    if ((flight.getSource().getCode().equals(((AirportAdmin) user).getAirport().getCode())) || (flight.getDestination().getCode().equals(((AirportAdmin) user).getAirport().getCode()))) {
                        System.out.println(flight.toString());
                    }
                } else {
                    System.out.println(flight.toString());
                }
            } else if (user instanceof EndUser) {
                if (!flight.getAircraft().getIsPrivate()) {
                    if (((EndUser) user).getIsRegistered()) {
                        System.out.println(flight.toString());
                    } else {
                        System.out.println(flight.displayNonRegisteredUser());
                    }
                }
            } else {
                if (!flight.getAircraft().getIsPrivate()) {
                    System.out.println(flight.toString());
                }
            }
        }
    }

}
