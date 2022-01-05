package bgu.spl.net.system;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.BlockingConnectionHandler;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.io.Serializable;
import java.util.HashMap;

public class ConnectionsImpl implements Connections<Serializable> {
    private final HashMap<Integer, ConnectionHandler<String>> connections=new HashMap<>();
    @Override
    public boolean send(int connectionId, Serializable msg) {
        if(!connections.containsKey(connectionId))
            return false;
        connections.get(connectionId).send((String) msg);
        return true;
    }

    @Override
    public void broadcast(Serializable msg) {

    }

    @Override
    public void disconnect(int connectionId) {
        connections.remove(connectionId);
    }
    public void connect(int id,BlockingConnectionHandler<String> handler){
        connections.put(id,handler);
    }
}
