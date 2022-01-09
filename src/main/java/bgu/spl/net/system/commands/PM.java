package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;

import java.io.Serializable;
import java.util.Date;

public class PM implements Command<String>{
    @Override
    public Serializable execute(int id, String arg, BGSInstance instance, ConnectionsImpl connections) {
        String[] credentials= toString().split("0");
        try {
            Date date = new Date(credentials[2]);
            return instance.PM(id,credentials[1],credentials[0],date,connections).getContent();

        }
        catch (IllegalArgumentException e){
            return "ERROR 6 invalid date";
        }
    }
}
