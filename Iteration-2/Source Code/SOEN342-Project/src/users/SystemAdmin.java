package users;

public class SystemAdmin {
	public SystemAdmin()
	{
		super();
	}
	public SystemAdmin(String username, String password)
	{
		super(username, password);
	}
	public SystemAdmin(SystemAdmin systemAdmin)
	{
		super(systemAdmin.getUsername(), systemAdmin.getPassword());
	}
}
