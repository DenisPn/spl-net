package bgu.spl.net.system.responses;

import java.util.Arrays;
import java.util.Locale;

public class Post implements Message{
    private String content;
    private final String creator;
    private final int type;

    public Post(String content, String creator){
        this.content = content;
        this.creator = creator;
        type = 0;
    }
    public String getContent(){
        return content;
    }

    public String getCreator(){
        return creator;
    }
    public int getType(){
        return type;
    }
}
