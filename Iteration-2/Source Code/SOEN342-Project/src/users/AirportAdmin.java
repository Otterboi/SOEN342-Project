package users;

import flights.Airport;

public class AirportAdmin extends User {
	private Airport airport;
	public AirportAdmin()
	{
		super();
		airport = new Airport();
	}
	public AirportAdmin(String username, String password, Airport airport)
	{
		super (username, password);
		this.airport = new Airport(airport);
	}
	public AirportAdmin(AirportAdmin airportAdmin)
	{
		super(airportAdmin.getUsername(), airportAdmin.getPassword());
		this.airport = new Airport(airportAdmin.getAirport());
	}
	public Airport getAirport()
	{
		return this.airport;
	}
	public void setAirport(Airport airport)
	{
		this.airport = new Airport(airport);
	}
}
