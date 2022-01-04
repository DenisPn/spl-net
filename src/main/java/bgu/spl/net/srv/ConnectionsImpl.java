package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.Connections;

public class ConnectionsImpl<T> implements Connections<T> {
    @Override
    public boolean send(int connectionId, T msg) {
        return false;
    }

    @Override
    public void broadcast(T msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }
}
