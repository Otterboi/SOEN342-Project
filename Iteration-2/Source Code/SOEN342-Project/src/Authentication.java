import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Enum for different types of administrators
enum AdministratorType {
    SYSTEM_ADMIN,
    AIRPORT_ADMIN,
    AIRLINE_ADMIN
}

public class Authentication {
    private Set<String> systemAdmins;
    private Set<String> airportAdmins;
    private Set<String> airlineAdmins;
    private Map<String, String> registeredUsers;

    public Authentication() {
        systemAdmins = new HashSet<>();
        airportAdmins = new HashSet<>();
        airlineAdmins = new HashSet<>();
        registeredUsers = new HashMap<>();
    }

    public void registerAdministrator(String username, AdministratorType type) {
        switch (type) {
            case SYSTEM_ADMIN:
                systemAdmins.add(username);
                break;
            case AIRPORT_ADMIN:
                airportAdmins.add(username);
                break;
            case AIRLINE_ADMIN:
                airlineAdmins.add(username);
                break;
        }
    }

    public void registerUser(String username, String password) {
        registeredUsers.put(username, password);
    }

    public boolean login(String username, String password) {
        if (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password)) {
            if(systemAdmins.contains(username) || airportAdmins.contains(username) || airlineAdmins.contains(username)) {
                return true; // User is an admin
            }
            else return false; // User is not an admin
        }
        return false; // User is not registered
    }

    public void convertToAdmin(String username, AdministratorType type) {
        switch(type) {
            case SYSTEM_ADMIN:
                systemAdmins.add(username);
                break;
            case AIRPORT_ADMIN:
                airportAdmins.add(username);
                break;
            case AIRLINE_ADMIN:
                airlineAdmins.add(username);
                break;
        }
    }

    /*
     // Create an instance of Authentication
        Authentication auth = new Authentication();

        // Register administrators
        auth.registerAdministrator("admin1", AdministratorType.SYSTEM_ADMIN);
        auth.registerAdministrator("admin2", AdministratorType.AIRPORT_ADMIN);
        auth.registerAdministrator("admin3", AdministratorType.AIRLINE_ADMIN);

        // Register some end-users
        auth.registerUser("user1", "password1");
        auth.registerUser("user2", "password2");

        // Login attempt
        System.out.println("User1 login result: " + auth.login("user1", "password1")); // Should be false

        // Convert user1 to admin
        auth.convertToAdmin("user1", AdministratorType.SYSTEM_ADMIN);

        // Re-login attempt after conversion
        System.out.println("User1 login result after conversion: " + auth.login("user1", "password1")); // Should be true
    }
     */
}
