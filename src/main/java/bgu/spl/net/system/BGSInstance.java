package bgu.spl.net.system;

import bgu.spl.net.system.responses.*;
import bgu.spl.net.system.responses.Error;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BGSInstance {

    private final ConcurrentHashMap<String, User> users;
    private List<Message> messages;
    private ConcurrentHashMap<Integer,User> loggedIn;
    private String[] bannedWord;
    public BGSInstance() {
        users = new ConcurrentHashMap<>();
        messages = new ArrayList<>();
        loggedIn=new ConcurrentHashMap<>();
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
        if(response.getType()){
            loggedIn.put(user.getId(),user);
            while (user.haveMail()){
                connections.send(id,user.popFromMailBox());
            }
        }
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
    public Response follow(int id, char followUnfollow, String toFollow){
        if(!loggedIn.containsKey(id))
            return new Error(4,"User not Logged In");
        if(!users.containsKey(toFollow))
            return new Error(4,"user to follow does not exist");
        User user=loggedIn.get(id);
        if(followUnfollow == '0') {
                users.get(toFollow).addFollower(user.getUserName());
                return user.follow(toFollow);
        }
        else {
            users.get(toFollow).removeFollower(user.getUserName());
            return user.unfollow(toFollow);
        }
    }
    public Response post(int id, String content,ConnectionsImpl connections){
        if(!loggedIn.containsKey(id))
            return new Error(5,"User not Logged In");
        User user=loggedIn.get(id);
        Post p = new Post(content, user.getUserName());
        String notification = "9 1 " + user.getUserName() + " 0 " + content + " 0";//TODO check format
        messages.add(p);
        user.post();
        for (String userName:user.getFollowers()) {
            User follower = users.get(userName);
            if(follower.getLoggedIn()){
                connections.send(follower.getId(),notification);
            }
            else
                follower.addToMailBox(notification);
        }
        List<String> tagged = new ArrayList<>();
        String[] words = content.split(" ");
        for (String word: words) {
            if(word.charAt(0) == '@'){
                if(users.values().contains(word.substring(1)) && !user.getFollowers().contains(word.substring(1))){
                    tagged.add(word);
                }
            }
        }
        for (String tagUserName:tagged) {
            User tagUser = users.get(tagUserName);
            if(tagUser.getLoggedIn()){
                connections.send(tagUser.getId(),notification);
            }
            else
                tagUser.addToMailBox(notification);
        }
        return new Ack(5,"post sent");
    }
    public Response PM(int id, String content, String sendTo, Date date,ConnectionsImpl connections){
        if(!loggedIn.containsKey(id))
            return new Error(6,"User not Logged In");
        User user=loggedIn.get(id);
        if(!users.containsKey(sendTo))
            return new Error(6,"user to send to does not exist");
        if(!user.doesFollow(sendTo))
            return new Error(6,"Not following the user to send to");
        PrivateMessage pm = new PrivateMessage(content, user.getUserName(), sendTo, date, bannedWord);
        String notification = "9 0 " + user.getUserName() + " 0 " + pm.getContent() + " 0";//TODO check format
        messages.add(pm);
        User sendToUser = users.get(sendTo);
        if(sendToUser.getLoggedIn()){
            connections.send(sendToUser.getId(),notification);
        }
        else
            sendToUser.addToMailBox(notification);
        return new Ack(6,"pm sent");
    }

    public Response LOGSTAT(int id){
        if(!loggedIn.containsKey(id))
            return new Error(7,"User not Logged In");
        StringBuilder stats= new StringBuilder();
        for (User user:loggedIn.values()) {
            if(!user.isBlocked(loggedIn.get(id).getUserName())){
                stats.append(user.getStats()).append("\n");
            }
        }
        return new Ack(7, stats.toString());
    }
    public Response STAT(int id, String[] userList){
        if(!loggedIn.containsKey(id))
            return new Error(8,"User not Logged In");
        StringBuilder stats = new StringBuilder();
        for (String un:userList) {
            if(!users.containsKey(un)){
                return new Error(8,"User does not exist");
            }
            User user = users.get(un);
            if(!user.isBlocked(loggedIn.get(id).getUserName())){
                stats.append(user.getStats()).append("\n");
            }
        }
        return new Ack(8, stats.toString());
    }
    public Response block(int id, String toBlock){
        if(!users.containsKey(toBlock))
            return new Error(12,"user to block does not exist");
        if(!loggedIn.containsKey(id))
            return new Error(12,"User not Logged In");
        User user=loggedIn.get(id);
        /**/
        user.addBlock(toBlock);
        user.removeFollow(toBlock);
        user.removeFollower(toBlock);
        users.get(toBlock).addBlock(user.getUserName());
        users.get(toBlock).removeFollow(user.getUserName());
        users.get(toBlock).removeFollower(user.getUserName());
        return new Ack(12,toBlock + " is now blocked");
    }
}
