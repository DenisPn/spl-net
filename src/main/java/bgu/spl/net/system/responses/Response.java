package bgu.spl.net.system.responses;

public abstract class Response {
    protected final int opCode;
    protected final String optional;

    protected Response(int opCode,String optional) {
        this.opCode = opCode;
        this.optional=optional;
    }

    public abstract String getContent();
}
