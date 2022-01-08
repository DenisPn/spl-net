package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class Login implements Command<String>{
    @Override
    public Serializable execute(int id, String arg, BGSInstance instance, ConnectionsImpl connections) {
        String[] credentials= toString().split("0");
        return instance.login(id,credentials[0],credentials[1],arg.getBytes(StandardCharsets.UTF_8)[arg.length()-1],connections).getContent();
    }
}
