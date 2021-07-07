package test.conn;

import java.net.*;

public class ConnectMethodTest {
    static String host="s.maxkim.vip";
    public static void main(String[] args)throws Exception {
        try
        {
            InetAddress ia1=InetAddress.getByName(host);
            System.out.println(ia1.getHostName());
            System.out.println(ia1.getHostAddress());
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        Socket socket=new Socket();
        socket.connect(new InetSocketAddress(getHost(),25565),10000);

    }
    public static String getHost() {
        try {
            return IDN.toASCII(host);
        }
        catch (IllegalArgumentException illegalArgumentException2) {
            return "";
        }
    }
}