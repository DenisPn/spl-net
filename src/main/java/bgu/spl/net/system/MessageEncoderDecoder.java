package bgu.spl.net.system;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoder implements bgu.spl.net.api.MessageEncoderDecoder<Serializable> {
  //  private final ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
    private byte[] objectBytes = null;
    private int len = 0;
    @Override
    public Serializable decodeNextByte(byte nextByte) {
        if(nextByte==';')
            return popString();
        else {
            pushByte(nextByte);
            return null;
        }
    }

    @Override
    public byte[] encode(Serializable message) {
        return (message+";").getBytes();
    }
    private Serializable popString(){
        String res=new String(objectBytes,0,len, StandardCharsets.UTF_8);
        len=0; objectBytes=null;
        return res;
    }
    private void pushByte(byte nextByte) {
        if (len >= objectBytes.length)
            objectBytes = Arrays.copyOf(objectBytes, len*2);
        objectBytes[len] = nextByte;
        len++;
    }

}
