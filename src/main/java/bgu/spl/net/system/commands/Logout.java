package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;

import java.io.Serializable;

public class Logout implements Command<String>{
    @Override
    public Serializable execute(int id,String arg, BGSInstance instance, ConnectionsImpl connections) {
        return instance.logout(id).getContent();
    }
}
