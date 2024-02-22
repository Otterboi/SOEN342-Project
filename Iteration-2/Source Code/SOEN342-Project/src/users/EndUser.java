package users;

public class EndUser extends User {
	private boolean isRegistered;
	public EndUser()
	{
		super();
		isRegistered = false;
	}
	public EndUser(String username, String password, boolean isRegistered)
	{
		this.isRegistered = isRegistered;
		super(username, password);
	}
	public EndUser(EndUser endUser)
	{
		this.isRegistered = endUser.getIsRegistered();
		super(endUser.getUsername(), endUser.getPassword());
	}
	public boolean getIsRegistered()
	{
		return this.isRegistered;
	}
	public void setIsRegistered(boolean isRegistered)
	{
		this.isRegistered = isRegistered;
	}
}
