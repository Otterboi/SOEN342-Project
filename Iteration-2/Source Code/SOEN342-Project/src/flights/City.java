package flights;

public class City {
    private String name;
    private String country;
    private double temperature;

    public City(){
        this.name = "";
        this.country = "";
        this.temperature = 0;
    }

    public City(String name, String country, double temperature){
        this.name = name;
        this.country = country;
        this.temperature = temperature;
    }
}
