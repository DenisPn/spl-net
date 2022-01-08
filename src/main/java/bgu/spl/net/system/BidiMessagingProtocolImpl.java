package bgu.spl.net.system;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.system.commands.Command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Serializable> {
    private final BGSInstance bgs;
    private int id;
    private ConnectionsImpl connections;
    private final HashMap<Short, Supplier<Command>> commandFactory;


    public BidiMessagingProtocolImpl(BGSInstance bgs, HashMap<Short, Supplier<Command>> factory) {
        commandFactory=factory;
        this.bgs = bgs;
    }
    @Override
    public void start(int connectionId, Connections<Serializable> connections) {
        id=connectionId;
        this.connections= (ConnectionsImpl) connections;
    }

    @Override
    public void process(Serializable message) {
        Short opCode=Short.valueOf(message.toString().substring(0,2));
        Command command=commandFactory.get(opCode).get();
        command.execute(id,message.toString().substring(3,message.toString().length()-1), bgs,connections);
    }

    @Override
    public boolean shouldTerminate() {
        return connections.connected(id);
    }
}
