package bgu.spl.net.system;

import bgu.spl.net.system.ConnectionsImpl;
import bgu.spl.net.system.responses.Ack;
import bgu.spl.net.system.responses.Error;
import bgu.spl.net.system.responses.Message;
import bgu.spl.net.system.responses.Response;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    private int connectedClientId;
    private final Date birthDay;
    private final String userName;
    private final String password;
    private List<String> followers;
    private List<String> follows;
    private List<String> blocks;
    private int numberOfPosts;
    private ArrayDeque<String> mailBox;
    private final Object followBlockLock=new Object();


    public User(Date birthDay, String userName, String password) {
        this.birthDay = birthDay;
        this.userName = userName;
        this.password = password;
        connectedClientId=-1;
        followers = new ArrayList<>();
        follows = new ArrayList<>();
        blocks = new ArrayList<>();
        numberOfPosts = 0;
        mailBox = new ArrayDeque<>();
    }
    public String getBirthDay(){
        return  birthDay.toString();
    }
    public int getAge(){
        Date date=new Date(System.currentTimeMillis());
        return date.getYear()-birthDay.getYear();
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
        return connectedClientId;
    }

    public boolean getLoggedIn(){
        return connectedClientId!=-1;
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
    public List<String> getFollowers(){
        return followers;
    }

    public String getPassword() {
        return password;
    }

    public void addFollower (String userName){
        synchronized (followBlockLock) {
            if (!followers.contains(userName))
                followers.add(userName);
        }
    }
    public boolean addFollows (String userName){
        if(follows.contains(userName))
            return false;
        follows.add(userName);
        return true;
    }
    public void removeFollower(String userName){
        synchronized (followBlockLock) {
            followers.remove(userName);
        }
    }
    public boolean removeFollow(String userName){
        synchronized (followBlockLock) {
            if (!follows.contains(userName))
                return false;
            follows.remove(userName);
            return true;
        }
    }
    public void addBlock (String userName){
        synchronized (followBlockLock) {
            if (!blocks.contains(userName))
                blocks.add(userName);
            removeFollower(userName);
            removeFollow(userName);
        }
    }
    public String getStats(){
        return userName + " " +
                getAge() + " " +
                numberOfPosts + " " +
                getNumberOfFollowers() + " " +
                getNumberOfFollows() + " ";
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

    public Response logIn(int id,String password){
        if(connectedClientId!=-1)
            return new Error(2,"requested user is already in use, login failed");
        else if(password.equals(this.password)){
            connectedClientId=id;
            return new Ack(2, "login successful");
        }
        else return new Error(2,"Incorrect Password. login failed");
    }
    public Response logout(){
        if (connectedClientId != -1){
            connectedClientId=-1;
            return new Ack(3, "logout successful");
        }
        else
            return new Error(3,"no user Logged In");
    }
    public Response follow(String userName){
        synchronized (followBlockLock) {
            if (connectedClientId == -1)
                return new Error(4, "User not Logged In");
            if (addFollows(userName))
                return new Ack(4, "0" + userName);

            else
                return new Error(4, "already followed");
        }
    }
    public Response unfollow(String userName){
        synchronized (followBlockLock) {
            if (connectedClientId == -1)
                return new Error(4, "User not Logged In");
            if (removeFollow(userName))
                return new Ack(4, "1" + userName);
            else
                return new Error(4, "already unfollowed");
        }
    }
    public void addToMailBox(String message){
        mailBox.add(message);
    }
    public String popFromMailBox(){
        return mailBox.pop();
    }
    public boolean haveMail(){
        return !mailBox.isEmpty();
    }
}
