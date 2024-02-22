package users;

public class AirlineAdmin extends User {
	private Airline airline;
	public AirlineAdmin()
	{
		airline = new Airline();
		super();
	}
	public AirlineAdmin(String username, String password, Airline airline)
	{
		super (username, password);
		this.airline = new Airline(airline);
	}
	public AirlineAdmin(AirlineAdmin airlineAdmin)
	{
		super(airlineAdmin.getUsername(), airlineAdmin.getPassword());
		this.airline = new Airline(airlineAdmin.getAirline());
	}
	public Airline getAirline()
	{
		return this.airline;
	}
	public void setAirline(Airline airline)
	{
		this.airline = new Airline(airline);
	}
}
