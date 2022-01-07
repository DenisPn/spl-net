package bgu.spl.net.srv;

import bgu.spl.net.system.ConnectionsImpl;
import bgu.spl.net.system.responses.Ack;
import bgu.spl.net.system.responses.Error;
import bgu.spl.net.system.responses.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    private final int id;
    private final Date birthDay;
    private final String userName;
    private final String password;
    private boolean loggedIn=false;
    private List<String> followers;
    private List<String> follows;
    private List<String> blocks;
    private int numberOfPosts;


    public User(int id, Date birthDay, String userName, String password) {
        this.id = id;
        this.birthDay = birthDay;
        this.userName = userName;
        this.password = password;
        followers = new ArrayList<>();
        follows = new ArrayList<>();
        blocks = new ArrayList<>();
        numberOfPosts = 0;
    }
    public Date getBirthDay(){
        return  birthDay;
    }
    public int getNumberOfPosts(){
        return numberOfPosts;
    }
    public int getNumberOfFollowers() {
        return followers.size();
    }
    public int getNumberOfFollows() {
        return follows.size();
    }
        public int getId() {
        return id;
    }

    public boolean getLoggedIn(){
        return loggedIn;
    }
    public boolean isFollowed(String userName){
        return follows.contains(userName);
    }
    public void post(){
        numberOfPosts++;
    }
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void addFollower (String userName){
        if(!followers.contains(userName))
            followers.add(userName);
    }
    public boolean addFollows (String userName){
        if(follows.contains(userName))
            return false;
        follows.add(userName);
        return true;
    }
    public void removeFollower(String userName){
        followers.remove(userName);
    }
    public boolean removeFollow(String userName){
        if(!follows.contains(userName))
            return false;
        follows.remove(userName);
        return true;
    }
    public boolean removeBlock(String userName){
        if(!blocks.contains(userName))
            return false;
        blocks.remove(userName);
        return true;
    }
    public void addBlock (String userName){
        if(!blocks.contains(userName))
            blocks.add(userName);
        removeFollower(userName);
        removeFollow(userName);
    }
    public boolean isFollowedBy(String userName){
        return followers.contains(userName);
    }
    public boolean doesFollow(String userName){
        return follows.contains(userName);
    }
    public boolean isBlocked(String userName){
        return blocks.contains(userName);
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
    public Response logout(){
        if (loggedIn)
            return new Ack(3, "logout successful");
        else
            return new Error(3,"User not Logged In");
    }
    public Response follow(String userName){
        if(!loggedIn)
            return new Error(4,"User not Logged In");
        if(addFollows(userName))
            return new Ack(4, "follow successful");//TODO: not in format
        else
            return new Error(4,"already followed");
    }
    public Response unfollow(String userName){
        if(!loggedIn)
            return new Error(4,"User not Logged In");
        if(removeFollow(userName))
            return new Ack(4, "unfollow successful");//TODO: not in format
        else
            return new Error(4,"already unfollowed");
    }
}
