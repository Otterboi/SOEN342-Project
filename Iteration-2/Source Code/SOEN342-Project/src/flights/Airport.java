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

    public Airport(Airport airport){
        this.name = airport.getName();
        this.code = airport.getCode();
        this.city = airport.getCity();
        this.listOfFlights = airport.getFlights();
    }

    public List<Flight> getFlights(){
        return this.listOfFlights;
    }

    public String getName(){
        return this.name;
    }

    public String getCode(){
        return this.code;
    }

    public City getCity(){
        return this.city;
    }
}
