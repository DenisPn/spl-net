package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;
import bgu.spl.net.system.responses.Response;

import java.io.Serializable;

public class Logout implements Command<String>{
    @Override
    public Serializable execute(int id,String arg, BGSInstance instance, ConnectionsImpl connections) {
        Response response= instance.logout(id);
        if(response.getType()) { //true=not error
            connections.disconnect(id);
        }
        return response.getContent();

    }
}
