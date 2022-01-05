package bgu.spl.net.system.responses;

public class Error extends Response {
    public Error(int opCode, String optional) {
        super(opCode, optional);
    }

    @Override
    public String getContent() {
        return opCode + " " + this.getClass() + " " + optional;
    }
}
