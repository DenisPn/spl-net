package bgu.spl.net.system;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.BlockingConnectionHandler;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.io.Serializable;
import java.util.HashMap;

public class ConnectionsImpl<T> implements Connections<T> {
    private final HashMap<Integer, ConnectionHandler<T>> connections=new HashMap<>();
    @Override
    public boolean send(int connectionId, T msg) {
        if(!connections.containsKey(connectionId))
            return false;
        connections.get(connectionId).send(msg);
        return true;
    }

    @Override
    public void broadcast(T msg) {

    }

    @Override
    public void disconnect(int connectionId) {
        connections.remove(connectionId);
    }
    public void connect(int id,BlockingConnectionHandler<T> handler){
        connections.put(id,handler);
    }
    public boolean connected(int id){
        return connections.containsKey(id);
    }
}
