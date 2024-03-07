package flights;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String name;
    private String code;
    private City city;
    private List<Aircraft> fleet;

    public Airport(){
        this.name = "";
        this.code = "";
        this.city = new City();
    }

    public Airport(String name, String code, City city){
        this.name = name;
        this.code = code;
        this.city = city;
        this.fleet = new ArrayList<>();
    }

    public Airport(Airport airport){
        this.name = airport.getName();
        this.code = airport.getCode();
        this.city = airport.getCity();
        this.fleet = airport.getFleet();
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

    public void addAircraftToFleet(Aircraft aircraft){
        this.fleet.add(aircraft);
    }

    public List<Aircraft> getFleet() {
        return fleet;
    }

    public void setFleet(List<Aircraft> fleet) {
        this.fleet = fleet;
    }
}
