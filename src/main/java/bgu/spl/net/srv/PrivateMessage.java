package bgu.spl.net.srv;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class PrivateMessage {
    private String content;
    private final String creator;
    private final String toUser;
    private final Date date;

    public PrivateMessage(String content, String creator, String toUser, Date date){
        this.content = content;
        this.creator = creator;
        this.toUser = toUser;
        this.date = date;
        //filterContent();//TODO: filter in construction
    }
    public String getContent(){
        return content;
    }
    public String getCreator(){
        return creator;
    }
    public String getToUser(){
        return toUser;
    }
    public Date getDate(){
        return date;
    }
    public void filterContent(String[] realBannedWords){
        String post = "This is my first Post about banned words like trump and war and corona";//EXAMPLE
        String[] bannedWords = {"banned", "trump", "war", "corona"};//TODO: EXAMPLE, to replace with real banned words
        String[] words = post.split(" ");//TODO: REPLACE WITH CONTENT
        for (int i = 0; i < bannedWords.length; i++) {
            bannedWords[i] = bannedWords[i].toLowerCase(Locale.ROOT);
        }
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase(Locale.ROOT);
            if(Arrays.asList(bannedWords).contains(word)){
                words[i] = "<filtered>";
            }
        }
        content = String.join(" ", words);
    }
}
