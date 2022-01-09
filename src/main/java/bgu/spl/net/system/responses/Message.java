package bgu.spl.net.system.responses;

public interface Message {
    public String getContent();

    public int getType();

    public String getCreator();
}
