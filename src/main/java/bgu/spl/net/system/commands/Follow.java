package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;

import java.io.Serializable;

public class Follow implements Command<String>{

    public Serializable execute(int id, String arg, BGSInstance instance, ConnectionsImpl connections) {
        return null;
    }
}
