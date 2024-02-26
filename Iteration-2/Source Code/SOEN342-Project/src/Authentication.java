import users.User;

import java.util.*;

public class Authentication {

    public Authentication() {
    }

    public ArrayList<Object> login(String username, String password){
        ArrayList<Object> loggedInUser = new ArrayList<>();

        for(User user: Console.users){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                loggedInUser.add(user.getClass().getName());
                loggedInUser.add(user);
            }
        }

        return loggedInUser;
    }

}
