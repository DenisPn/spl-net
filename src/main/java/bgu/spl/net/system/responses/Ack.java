package bgu.spl.net.system.responses;

public class Ack extends Response {

    public Ack(int opCode,String optional){
        super(opCode,optional);

    }
    @Override
    public String getContent() {
        return opCode + " " + this.getClass() + " " + optional;
    }
}
