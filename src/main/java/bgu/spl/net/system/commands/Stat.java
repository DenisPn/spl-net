package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;

import java.io.Serializable;

public class Stat implements Command<String>{

    public Serializable execute(int id, String arg, BGSInstance instance, ConnectionsImpl connections) {
        String[] users=toString().split("\\s | \\|+"); //split by space or '|'
        return instance.STAT(id,users).getContent();
    }
}
