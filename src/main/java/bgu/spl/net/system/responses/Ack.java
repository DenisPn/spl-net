package bgu.spl.net.system.responses;

public class Ack extends Response {
    private final String optional;
    public Ack(int opCode,String optional){
        super(opCode);
        this.optional=optional;


    }
    @Override
    public String getContent() {
        return opCode + " " + this.getClass() + " " + optional;
    }

    @Override
    public boolean getType() {
        return true;
    }
}
