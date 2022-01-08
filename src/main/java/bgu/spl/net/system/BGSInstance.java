package bgu.spl.net.system;

import bgu.spl.net.system.responses.Post;
import bgu.spl.net.system.responses.PrivateMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.system.responses.Ack;
import bgu.spl.net.system.responses.Error;
import bgu.spl.net.system.responses.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BGSInstance {

    private final HashMap<String, User> users;
    private HashMap<String, Post> posts;
    private HashMap<String, PrivateMessage> privateMessages;
    private HashMap<Integer,User> loggedIn;
    private String[] bannedWord;
    public BGSInstance() {
        this.users = new HashMap<>();
        posts = new HashMap<>();
        privateMessages = new HashMap<>();
        loggedIn=new HashMap<>();

    }

    public Response register(String userName,String password, String bday) {
        if (users.containsKey(userName))
            return new Error(1, "Username already exists");
        else {
            try {
                Date date = new Date(bday);
                User user = new User(date, userName, password);
                users.put(userName,user);
                return new Ack(1,"Register complete");
            }
            catch (IllegalArgumentException e){
                return new Error(1,"Invalid birth date");
            }
        }
    }
    public Response login(int id,String userName,String password, Byte captcha,ConnectionsImpl connections){
        if(loggedIn.containsKey(id))
            return new Error(2,"User is already logged in,logout before logging in to another user");
        if(captcha == 0)
            return new Error(2,"Captcha is 0");
        if(!users.containsKey(userName))
            return new Error(2,"Username does not exists");
        User user=users.get(userName);
        Response response=user.logIn(id,password);
        if(response.getType())
            loggedIn.put(user.getId(),user);
        return response;
    }
    public Response logout(int id){
        if(!loggedIn.containsKey(id))
            return new Error(2,"User not logged in");
        User user=loggedIn.get(id);
        Response response= user.logout();
        if(response.getType())
            loggedIn.remove(user.getId(),user);
        return response;
    }
    public Response follow(int id, Byte followUnfollow, String toFollow){
        if(!loggedIn.containsKey(id))
            return new Error(4,"User not Logged In");
        if(!users.containsKey(toFollow))
            return new Error(4,"user to follow does not exist");
        User user=loggedIn.get(id);
        if(followUnfollow == 0) {
                users.get(toFollow).addFollower(user.getUserName());
            return users.get(user.getUserName()).follow(toFollow);
        }
        else {
                users.get(toFollow).removeFollower(user.getUserName());
            return users.get(user.getUserName()).unfollow(toFollow);
        }
    }
    public Response post(int id, String content){
        if(!loggedIn.containsKey(id))
            return new Error(5,"User not Logged In");
        User user=loggedIn.get(id);
        Post p = new Post(content, user.getUserName());
        posts.put(user.getUserName(), p);
        users.get(user.getUserName()).post();
        return new Ack(5,"post sent");
    }
    public Response PM(int id, String content, String sendTo, Date date){
        if(!loggedIn.containsKey(id))
            return new Error(6,"User not Logged In");
        User user=loggedIn.get(id);
        if(!users.containsKey(sendTo))
            return new Error(6,"user to send to does not exist");
        if(!user.doesFollow(sendTo))
            return new Error(6,"Not following the user to send to");
        PrivateMessage pm = new PrivateMessage(content, user.getUserName(), sendTo, date);
        privateMessages.put(user.getUserName(), pm);
        return new Ack(6,"pm sent");
    }
    private String getStats(User user){
        return user.getUserName() + " " +
                user.getAge() + " " +
                user.getNumberOfPosts() + " " +
                user.getNumberOfFollowers() + " " +
                user.getNumberOfFollows() + " ";
    }
    public Response LOGSTAT(String userName){
        if(!users.get(userName).getLoggedIn())
            return new Error(7,"User not Logged In");
        String stats= "STATS-8 ";
        for (String un:users.keySet()) {
            User user = users.get(un);
            if(user.getLoggedIn()){
                stats=getStats(user);
            }
        }
        return new Ack(7, stats.toString());
    }
    public Response STAT(int id, String[] userList){
        if(!loggedIn.containsKey(id))
            return new Error(8,"User not Logged In");
        StringBuilder stats = new StringBuilder();
        stats.append("STATS-8 ");
        for (String un:userList) {
            if(!users.containsKey(un)){
                return new Error(8,"User does not exist");
            }
            User user = users.get(un);
            stats.append(getStats(user)).append("\n");
        }
        return new Ack(8, stats.toString());
    }
    public Response block(int id, String toBlock){
        if(!users.containsKey(toBlock))
            return new Error(12,"user to block does not exist");
        if(!loggedIn.containsKey(id))
            return new Error(12,"User not Logged In");
        User user=loggedIn.get(id);
        users.get(user.getUserName()).addBlock(toBlock);
        users.get(toBlock).addBlock(user.getUserName());
        return new Ack(12,toBlock+" is now blocked");
    }
}
