package bgu.spl.net.system.responses;

public class Error extends Response {
    private final String optional;
    public Error(int opCode, String optional) {
        super(opCode);
        this.optional=optional;

    }

    @Override
    public String getContent() {
        return "ACK " + opCode+" " + optional;
    }

    @Override
    public boolean getType() {
        return false;
    }
}
