package users;

public class User {
	private String username, password;
	public User()
	{
		username = "";
		password = "";
	}
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	public User(User user)
	{
		this.username = user.getUsername();
		this.password = user.getPassword();
	}
	public User getUser(String username)
	{
		if (this.username.equals(username))
			return this;
		return null;
	}
	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void displayFlights()
	{
		System.out.println("List of flights");
	}
}
