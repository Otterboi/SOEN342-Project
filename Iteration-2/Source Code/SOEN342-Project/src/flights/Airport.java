package flights;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String name;
    private String code;
    private City city;

    public Airport(){
        this.name = "";
        this.code = "";
        this.city = new City();
    }

    public Airport(String name, String code, City city){
        this.name = name;
        this.code = code;
        this.city = city;
    }

    public Airport(Airport airport){
        this.name = airport.getName();
        this.code = airport.getCode();
        this.city = airport.getCity();
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
