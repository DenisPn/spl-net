package bgu.spl.net.system.responses;

public abstract class Response {
    protected final int opCode;
 //   protected final int clientId;

    protected Response(int opCode) {
        this.opCode = opCode;

    //    this.optional=optional;
    }

    public abstract String getContent();
    public abstract boolean getType();
}
