package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;

import java.io.Serializable;

public class Post implements Command<String> {
    @Override
    public Serializable execute(int id, String arg, BGSInstance instance, ConnectionsImpl connections) {
        return instance.post(id,arg.substring(0,arg.length()-1),connections).getContent(); //remove 0 bit from string
    }
}
