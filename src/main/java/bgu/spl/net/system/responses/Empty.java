package bgu.spl.net.system.responses;

public class Empty extends Response{
    public Empty(){super(-1);}
    @Override
    public String getContent() {
        return "";
    }

    @Override
    public boolean getType() {
        return true;
    }
}
