package flights;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String name;
    private String code;
    private City city;
    private List<Flight> listOfFlights;

    public Airport(){
        this.name = "";
        this.code = "";
        this.city = new City();
        this.listOfFlights = new ArrayList<Flight>();
    }

    public Airport(String name, String code, City city, ArrayList<Flight> listOfFlights){
        this.name = name;
        this.code = code;
        this.city = city;
        this.listOfFlights = listOfFlights;
    }

    public List<Flight> getFlights(){
        return this.listOfFlights;
    }
}
