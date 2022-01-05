package bgu.spl.net.system;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.User;
import bgu.spl.net.system.responses.Ack;
import bgu.spl.net.system.responses.Error;
import bgu.spl.net.system.responses.Response;

import java.util.Date;
import java.util.HashMap;

public class BGSInstance {

    private final HashMap<String, User> users;
//    private final ConnectionsImpl connections;
    int idTracker=0;
    public BGSInstance() {
        this.users = new HashMap<>();
     //   this.connections=connections;
    }

    public Response register(String userName,String password, Date bday) {
        if (users.containsKey(userName))
            return new Error(0, "Username already exists");
        else {
            User user = new User(idTracker, bday, userName, password);
            idTracker++;
            users.put(userName,user);
            return new Ack(0,"Register Complete");
        }
    }
    public Response login(String userName,String password){
        if(!users.containsKey(userName))
            return new Error(1,"Username does not exists");
        return users.get(userName).logIn(password);
    }
}
