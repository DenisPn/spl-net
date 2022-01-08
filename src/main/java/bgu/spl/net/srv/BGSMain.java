package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.BidiMessagingProtocolImpl;
import bgu.spl.net.system.ConnectionsImpl;
import bgu.spl.net.system.MessageEncoderDecoder;
import bgu.spl.net.system.commands.*;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.function.Supplier;

public class BGSMain {

    public static void main(String[] args){
    //    ConnectionsImpl connections=new ConnectionsImpl();
        BGSInstance bgs=new BGSInstance();
        HashMap<Short, Supplier<Command>> factory=new HashMap();
        factory.put((short) 1, Register::new);
        factory.put((short) 2, Login::new);
        factory.put((short) 3, Logout::new);
        factory.put((short) 4, Follow::new);
        factory.put((short) 5, Post::new);
        factory.put((short) 6, PM::new);
        factory.put((short) 7, LogStat::new);
        factory.put((short) 8, Stat::new);
        factory.put((short) 9, Notification::new);
        factory.put((short) 12, Block::new);
            Server.threadPerClient(
                    7777, //port
                    () -> new BidiMessagingProtocolImpl(bgs,factory),
                    MessageEncoderDecoder::new //message encoder decoder factory
            ).serve();
    }
}
