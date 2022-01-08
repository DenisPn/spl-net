package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;
import bgu.spl.net.system.responses.Response;

import java.io.Serializable;

public class Register implements Command<String>{
    @Override
    public Serializable execute(int id,String arg, BGSInstance instance, ConnectionsImpl connections) {
        String[] credentials= toString().split("0");
        return instance.register(credentials[0],credentials[1],credentials[2]).getContent();

    }
}
