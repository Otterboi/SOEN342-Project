package users;

public class SystemAdmin extends User{
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
