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
		super(username, password);
		this.isRegistered = isRegistered;
	}
	public EndUser(EndUser endUser)
	{
		super(endUser.getUsername(), endUser.getPassword());
		this.isRegistered = endUser.getIsRegistered();
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
