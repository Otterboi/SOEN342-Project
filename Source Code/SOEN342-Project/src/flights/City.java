package flights;

public class City {
    private String name;
    private String country;
    private int temperature;

    public City(){
        this.name = "";
        this.country = "";
        this.temperature = 0;
    }

    public City(String name, String country, int temperature){
        this.name = name;
        this.country = country;
        this.temperature = temperature;
    }


    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(int temp){
        this.temperature = temp;
    }
}
