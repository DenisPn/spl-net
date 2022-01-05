package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.system.BGSInstance;
import bgu.spl.net.system.BidiMessagingProtocolImpl;
import bgu.spl.net.system.ConnectionsImpl;
import bgu.spl.net.system.MessageEncoderDecoder;

public class BGSMain {

    public static void main(String[] args){
    //    ConnectionsImpl connections=new ConnectionsImpl();
        BGSInstance bgs=new BGSInstance();

            Server.threadPerClient(
                    7777, //port
                    () -> new BidiMessagingProtocolImpl(bgs),
                    MessageEncoderDecoder::new //message encoder decoder factory
            ).serve();
    }
}
