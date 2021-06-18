package test.conn;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FakeServerTest {
    static DataInputStream dataInputStream;
    public static void main(String[] args)throws Exception {
        ServerSocket socket=new ServerSocket(25567);
        while (true){
            Socket client=socket.accept();
            System.out.println("newConn.");
            try{
                dataInputStream=new DataInputStream(client.getInputStream());
                int len=0;
                byte[] recv=new byte[2048];
//                System.out.println("Length(varInt):"+readVarInt());
//                System.out.println("PacketID(varInt):"+readVarInt());
//                System.out.println("ProtocolVersion(varInt):"+readVarInt());
//                System.out.println("ServerAddress(String):"+readString());
//                System.out.println("ServerPort(UnsignedShort):"+readUnsignedShort());
//                System.out.println("NextState(varIntEnum):"+readVarInt());
                while((len=dataInputStream.read(recv))!=-1){
                    if (len==0)
                        continue;
//                    System.out.println("recv:"+new String(recv, StandardCharsets.UTF_8));
                    for (int i=0;i<len;i++){
                        System.out.print(recv[i]+" ");
                    }
                    System.out.println();
                    recv=new byte[1024];
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
    public static String readString()throws Exception{
        int len=readVarInt();
        byte[] b=new byte[len];
        dataInputStream.read(b,0,len);
        return new String(b);
    }
    public static int readUnsignedShort()throws Exception{
        return dataInputStream.readUnsignedShort();
    }

}
