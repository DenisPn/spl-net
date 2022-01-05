package bgu.spl.net.srv;

import bgu.spl.net.system.ConnectionsImpl;
import bgu.spl.net.system.responses.Ack;
import bgu.spl.net.system.responses.Error;
import bgu.spl.net.system.responses.Response;

import java.util.Date;

public class User {

    private final int id;
    private final Date birthDay;
    private final String userName;
    private final String password;
    private boolean loggedIn=false;


    public User(int id, Date birthDay, String userName, String password) {
        this.id = id;
        this.birthDay = birthDay;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Response logIn(String password){
        if(loggedIn)
            return new Error(2,"User Already Logged In, login failed");
        else if(password.equals(this.password)){
            loggedIn=true;
            return new Ack(2, "login successful");
        }
        else return new Error(2,"Incorrect Password. login failed");
    }
}
