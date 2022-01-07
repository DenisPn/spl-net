package bgu.spl.net.system;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.Post;
import bgu.spl.net.srv.PrivateMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.system.responses.Ack;
import bgu.spl.net.system.responses.Error;
import bgu.spl.net.system.responses.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BGSInstance {

    private final HashMap<String, User> users;
    private HashMap<String, Post> posts;
    private HashMap<String, PrivateMessage> privateMessages;
//    private final ConnectionsImpl connections;
    int idTracker=0;
    public BGSInstance() {
        this.users = new HashMap<>();
        posts = new HashMap<>();
        privateMessages = new HashMap<>();
     //   this.connections=connections;
    }

    public Response register(String userName,String password, Date bday) {
        if (users.containsKey(userName))
            return new Error(1, "Username already exists");
        else {
            User user = new User(idTracker, bday, userName, password);
            idTracker++;
            users.put(userName,user);
            return new Ack(1,"Register Complete");
        }
    }
    public Response login(String userName,String password, Byte captcha){
        if(captcha == 0)
            return new Error(2,"Captcha is 0");
        if(!users.containsKey(userName))
            return new Error(2,"Username does not exists");
        return users.get(userName).logIn(password);
    }
    public Response logout(String userName){
        return users.get(userName).logout();
    }
    public Response follow(String userName, Byte followUnfollow, String toFollow){
        if(!users.containsKey(toFollow))
            return new Error(4,"user to follow does not exist");
        if(followUnfollow == 0) {
            if (users.get(userName).getLoggedIn())
                users.get(toFollow).addFollower(userName);
            return users.get(userName).follow(toFollow);
        }
        else {
            if (users.get(userName).getLoggedIn())
                users.get(toFollow).removeFollower(userName);
            return users.get(userName).unfollow(toFollow);
        }
    }
    public Response post(String userName, String content){
        if(!users.get(userName).getLoggedIn())
            return new Error(5,"User not Logged In");
        Post p = new Post(content, userName);
        posts.put(userName, p);
        users.get(userName).post();
        return new Ack(5,"post sent");
    }
    public Response PM(String userName, String content, String sendTo, Date date){
        if(!users.get(userName).getLoggedIn())
            return new Error(6,"User not Logged In");
        if(!users.containsKey(sendTo))
            return new Error(6,"user to send to does not exist");
        if(!users.get(userName).doesFollow(sendTo))
            return new Error(6,"Not following the user to send to");
        PrivateMessage pm = new PrivateMessage(content, userName, sendTo, date);
        privateMessages.put(userName, pm);
        return new Ack(6,"pm sent");
    }
    public Response LOGSTAT(String userName){
        if(!users.get(userName).getLoggedIn())
            return new Error(7,"User not Logged In");
        String stats = "";
        for (String un:users.keySet()) {
            User user = users.get(un);
            if(user.getLoggedIn()){
                stats += user.getUserName() + " ";
                stats += user.getBirthDay() + " ";//TODO: age instead of birthday
                stats += user.getNumberOfPosts() + " ";
                stats += user.getNumberOfFollowers() + " ";
                stats += user.getNumberOfFollows() + " ";
            }
        }
        return new Ack(7,stats);
    }
    public Response STAT(String userName, ArrayList<String> userList){
        if(!users.get(userName).getLoggedIn())
            return new Error(8,"User not Logged In");
        String stats = "";
        for (String un:userList) {
            if(!users.containsKey(un)){
                return new Error(8,"User does not exist");
            }
            User user = users.get(un);
            stats += user.getUserName() + " ";
            stats += user.getBirthDay() + " ";//TODO: age instead of birthday
            stats += user.getNumberOfPosts() + " ";
            stats += user.getNumberOfFollowers() + " ";
            stats += user.getNumberOfFollows() + " ";
        }
        return new Ack(8,stats);
    }
    public Response block(String userName, String toBlock){
        if(!users.containsKey(toBlock))
            return new Error(12,"user to follow does not exist");
        if(!users.get(userName).getLoggedIn())
            return new Error(12,"User not Logged In");
        users.get(userName).addBlock(toBlock);
        users.get(toBlock).addBlock(userName);
        return new Ack(12,"blocked");
    }
}
