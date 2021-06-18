package test.conn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LegacyPingTest {
    public static void main(String[] args)throws Exception {
        sendLegacyPing("localhost",25565);
    }
    public static void sendLegacyPing(String host,int port)throws Exception{

        System.out.println("ChangeProtocol");
        Socket socket=new Socket(host,port);
        DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
//        dataOutputStream.write(0xFE);
//        dataOutputStream.write(0x01);
//        dataOutputStream.write(0xFA);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x0B);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x4D);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x43);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x7C);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x50);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x69);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x6E);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x67);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x48);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x6F);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x73);
//        dataOutputStream.write(0x00);
//        dataOutputStream.write(0x74);//MC|PingHost over
//
//        dataOutputStream.write(7+len(host));
//        dataOutputStream.write(0x4A);
//        dataOutputStream.write(len(host));
//        String utf16beHost=new String(host.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_16BE);
//        dataOutputStream.write(utf16beHost.getBytes());
//        dataOutputStream.write(port);
//        dataOutputStream.flush();
        dataOutputStream.write(0xFE);
        dataOutputStream.write(0x01);
        dataOutputStream.flush();

        if (dataInputStream.readByte()==-1){
            dataInputStream.readByte();
            dataInputStream.readByte();
            int len=0;
            byte[] b=new byte[1024];
            len=dataInputStream.read(b);
            String s=new String(b,StandardCharsets.UTF_16BE);
        }
    }
    private static short len(String l){
        String utf16be=new String(l.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_16BE);
        return (short)utf16be.length();
    }

}
