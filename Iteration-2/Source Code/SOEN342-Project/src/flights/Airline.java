package flights;

import java.util.ArrayList;
import java.util.List;

public class Airline {
    private List<Aircraft> listOfAircrafts;
    private List<Flight> listOfFlights;

    public Airline(){
        this.listOfAircrafts = new ArrayList<Aircraft>();
        this.listOfFlights = new ArrayList<Flight>();
    }

    public Airline(ArrayList<Aircraft> listOfAircrafts, ArrayList<Flight> listOfFlights){
        this.listOfAircrafts = listOfAircrafts;
        this.listOfFlights = listOfFlights;
    }

    public Airline(Airline airline){
        this.listOfAircrafts = airline.getListOfAircrafts();
        this.listOfFlights = airline.getListOfFlights();
    }

    public List<Aircraft> getListOfAircrafts(){
        return this.listOfAircrafts;
    }

    public List<Flight> getListOfFlights(){
        return this.listOfFlights;
    }
}
