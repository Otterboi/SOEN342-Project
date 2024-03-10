package flights;

import java.util.ArrayList;
import java.util.List;

public class Airline {

    private String name;
    private List<Aircraft> fleet;

    public Airline(){
        this.name = "";
        this.fleet = new ArrayList<>();
    }

    public Airline(String name){
        this.name = name;
        this.fleet = new ArrayList<>();
    }

    public Airline(String name, List<Aircraft> fleet){
        this.name = name;
        this.fleet = fleet;
    }

    public Airline(Airline airline){
        this.name = airline.getName();
        this.fleet = new ArrayList<>();
    }

    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
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
