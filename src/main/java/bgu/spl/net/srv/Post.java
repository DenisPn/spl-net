package bgu.spl.net.srv;

import java.util.Arrays;
import java.util.Locale;

public class Post {
    private String content;
    private final String creator;

    public Post(String content, String creator){
        this.content = content;
        this.creator = creator;
    }
    public String getContent(){
        return content;
    }
    public String getCreator(){
        return creator;
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
                words[i] = "<filtered>";//TODO: CHANGE TO ACTUAL SYNTAX
            }
        }
        content = String.join(" ", words);
    }
}
