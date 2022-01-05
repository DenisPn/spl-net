package bgu.spl.net.system;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Serializable> {
    private final BGSInstance bgs;
    private int id;
    private ConnectionsImpl connections;
    private  HashMap<Short, Supplier<BidiMessagingProtocol<String>>> commandFactory;


    public BidiMessagingProtocolImpl(BGSInstance bgs) {
        this.bgs = bgs;
    }
    @Override
    public void start(int connectionId, Connections<Serializable> connections) {
        id=connectionId;
        this.connections= (ConnectionsImpl) connections;
    }

    @Override
    public void process(Serializable message) {

    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
