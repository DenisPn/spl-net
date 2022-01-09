package bgu.spl.net.system.responses;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class PrivateMessage implements Message{
    private String content;
    private final String creator;
    private final String toUser;
    private final Date date;
    private final int type;


    public PrivateMessage(String content, String creator, String toUser, Date date, String[]badwords){
        this.content = content;
        this.creator = creator;
        this.toUser = toUser;
        this.date = date;
        type = 1;
        filterContent(badwords);
    }
    public String getContent(){
        return content;
    }

    public int getType() {
        return type;
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
    public void filterContent(String[] bannedWords){
        String[] words = content.split(" ");
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
