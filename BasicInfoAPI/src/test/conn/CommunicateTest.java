package test.conn;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CommunicateTest {
    static String host="192.168.1.3";
    static int port=25565;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    public static void main(String[] args)throws Exception {
        Socket socket=new Socket(host,port);
        System.out.println("established.");
        dataOutputStream=new DataOutputStream(socket.getOutputStream());
        dataInputStream=new DataInputStream(socket.getInputStream());
        System.out.println("streamObjectCreated.");
        writeVarInt(18);
        writeVarInt(0x00);
        writeVarInt(754);
        writeString("192.168.1.3");
        writeVarInt(25567);
        writeVarInt(1);
//        dataOutputStream.write(1);
//        dataOutputStream.flush();
        System.out.println("wroteHandshake.");
        writeVarInt(0);
        System.out.println("recv:"+readString());
    }
    public static void writeVarInt(int value)throws Exception {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            dataOutputStream.write(temp);
            dataOutputStream.flush();
        } while (value != 0);
    }

    static void writeString(String s)throws Exception{
        byte[] b=s.getBytes();
        writeVarInt(b.length);
        dataOutputStream.write(b);
        dataOutputStream.flush();
    }
    static void writePacket(){}
    public static String readString()throws Exception{
        int len=readVarInt();
        byte[] b=new byte[len];
        dataInputStream.read(b,0,len);
        return new String(b);
    }
    public static int readVarInt()throws Exception {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = dataInputStream.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }
}
