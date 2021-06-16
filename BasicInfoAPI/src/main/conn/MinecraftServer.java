package main.conn;


import com.google.gson.Gson;
import main.api.IServerInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * A MinecraftServer instance provides methods to create connection to server and communicate with peer.
 * This is the basic instance and any info-getting function is base on this class.
 *
 * @author Rock Chin
 */
public class MinecraftServer extends Thread implements IServerInfo {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String host;
    private int port=25565;
    private Response response=null;
    public MinecraftServer(String host,int port)throws Exception {
        socket=new Socket(host,port);
        this.host=host;
        this.port=port;
        dataInputStream=new DataInputStream(socket.getInputStream());
        dataOutputStream=new DataOutputStream(socket.getOutputStream());

        new PacketSend(0).addVarInt(-1)
                .addString(host)
                .addShort(port)
                .addVarInt(1).write(dataOutputStream);
        new PacketSend(0).write(dataOutputStream);
        response=new Gson().fromJson(new PacketRecv(dataInputStream).popString(),Response.class);
    }


    public class Response{
        class description{
            String text;
            String color;
            description[] extra;
        }
        description description;
        class players{
            int max;
            int online;
            class player{
                String name;
                String id;
            }
            player[] sample;
        }
        players players;
        class version{
            String name;
            int protocol;
        }
        version version;
        String favicon;
    }


    @Override
    public String getVersionName() {
        return response.version.name;
    }

    @Override
    public int getVersionProtocol() {
        return response.version.protocol;
    }

    @Override
    public int getMaxPlayer() {
        return response.players.max;
    }

    @Override
    public int getOnlinePlayer() {
        return response.players.online;
    }

    @Override
    public Player[] getPlayerList() {
        int len=response.players.sample.length;
        Player[] players=new Player[len];
        for (int i=0;i<len;i++){
            players[i]=new Player();
            players[i].name=response.players.sample[i].name;
            players[i].id=response.players.sample[i].id;
        }
        return players;
    }

    @Override
    public String getDefaultDescriptionText() {
        return response.description.text;
    }
    @Override
    public String getDefaultDescriptionColor(){
        return response.description.color;
    }
    @Override
    public ExtraDescr[] getExtraDescription(){
        ExtraDescr[] extraDescrs=new ExtraDescr[response.description.extra.length];
        for (int i=0;i<response.description.extra.length;i++){
            extraDescrs[i]=new ExtraDescr();
            extraDescrs[i].color=response.description.extra[i].color;
            extraDescrs[i].text=response.description.extra[i].text;
        }
        return extraDescrs;
    }
    @Override
    public String getFavicon() {
        return response.favicon;
    }

}
