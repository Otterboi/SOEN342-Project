package flights;

public class Airline {

    private String name;

    public Airline(){
        this.name = "";
    }

    public Airline(String name){
        this.name = name;
    }

    public Airline(Airline airline){
        this.name = airline.getName();
    }

    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
