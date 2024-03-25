package Authentication;

import users.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {

    public UserDBController userDB;
    public Authentication(UserDBController userDB) {
        this.userDB = userDB;
    }

    /*public ArrayList<Object> login(String username, String password){
        ArrayList<Object> loggedInUser = new ArrayList<>();

        for(User user: Console.users){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                loggedInUser.add(user.getClass().getName());
                loggedInUser.add(user);
            }
        }

        return loggedInUser;
    }*/

    public ArrayList<Object> loginUser(String username, String password) throws SQLException, ClassNotFoundException {

        Object[] login = userDB.authenticateUser(username, password);
        ArrayList <Object> list = new ArrayList<>();

        if (login != null)
        {
            list.add(login[0]);
            list.add(login[1]);
        }
        else
        {
            System.out.println("Invalid username or password!");
        }
        return list;
    }
}
