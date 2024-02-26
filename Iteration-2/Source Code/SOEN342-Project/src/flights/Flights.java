package flights;

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

}
