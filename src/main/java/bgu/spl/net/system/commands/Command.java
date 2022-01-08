package bgu.spl.net.system.commands;

import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.ConnectionsImpl;

import java.io.Serializable;

public interface Command<T> extends Serializable {

    Serializable execute(int id, T arg, BGSInstance instance, ConnectionsImpl connections);
}
