package main.conn;


import com.google.gson.Gson;
import main.api.IServerInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        try {
            response = new Gson().fromJson(new PacketRecv(dataInputStream).popString(), Response.class);
        }catch (EOFException e){//To change protocol.
            socket=new Socket(host,port);
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            dataOutputStream.write(0xFE);
            dataOutputStream.write(0x01);
            dataOutputStream.flush();

            if (dataInputStream.readByte()==-1){
                dataInputStream.readByte();
                dataInputStream.readByte();
                byte[] b=new byte[512];
                dataInputStream.read(b);
                ByteBase bbase=new ByteBase(b);
                byte[] end=new byte[]{0,0};
                if (new String(bbase.pop(end),StandardCharsets.UTF_16BE).equals("§1")){
                    response=new Response();
                    response.version=new Response.version();
                    response.version.protocol=Integer.parseInt
                            (new String(bbase.pop(end),StandardCharsets.UTF_16BE));
                    response.version.name=new String(bbase.pop(end),StandardCharsets.UTF_16BE);
                    response.description=new Response.description();
                    response.description.text=new String(bbase.pop(end),StandardCharsets.UTF_16BE);
                    response.players=new Response.players();
                    response.players.online=Integer.parseInt
                            (new String(bbase.pop(end),StandardCharsets.UTF_16BE));
                    response.players.max=Integer.parseInt
                            (new String(bbase.pop(end),StandardCharsets.UTF_16BE));
                }
            }
        }
    }
    private short len(String l){
        String utf16be=new String(l.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_16BE);
        return (short)utf16be.length();
    }
    private class ByteBase{
        byte[] arr;
        int index=0;
        ByteBase(byte[] byteArr){
            this.arr=byteArr;
        }
        public byte[] pop(byte[] end){
            ArrayList<Byte> byteArrayList=new ArrayList<>();
            byte[] b=new byte[2];
            while(true){
                b=pop(2);
                if (b[0]==end[0]&&b[1]==end[1]){
                    break;
                }
                byteArrayList.add(b[0]);
                byteArrayList.add(b[1]);
            }
            int len=byteArrayList.size();
            byte[] result=new byte[len];
            for (int i=0;i<len;i++){
                result[i]=byteArrayList.get(i);
            }
            return result;
        }
        public byte pop(){
            return arr[index++];
        }
        public byte[] pop(int len){
            byte[] result=new byte[len];
            for (int i=0;i<len;i++){
                result[i]=pop();
            }
            return result;
        }
    }

    public static class Response{
        static class description{
            String text;
            String color;
            description[] extra;
        }
        description description;
        static class players{
            int max;
            int online;
            class player{
                String name;
                String id;
            }
            player[] sample;
        }
        players players;
        static class version{
            String name;
            int protocol;
        }
        version version;
        String favicon;
    }


    @Override
    public String getVersionName() {
        if (response!=null&&response.version!=null) {
            return response.version.name;
        }else {
            return null;
        }
    }

    @Override
    public int getVersionProtocol() {
        if (response!=null&&response.version!=null) {
            return response.version.protocol;
        }else {
            return -1;
        }
    }

    @Override
    public int getMaxPlayer() {
        if (response!=null&&response.players!=null) {
            return response.players.max;
        }else {
            return -1;
        }
    }

    @Override
    public int getOnlinePlayer() {
        if (response!=null&&response.players!=null) {
            return response.players.online;
        }else {
            return -1;
        }
    }

    @Override
    public Player[] getPlayerList() {
        if (response!=null&&response.players!=null&&response.players.sample!=null) {
            int len = response.players.sample.length;
            Player[] players = new Player[len];
            for (int i = 0; i < len; i++) {
                players[i] = new Player();
                players[i].name = response.players.sample[i].name;
                players[i].id = response.players.sample[i].id;
            }
            return players;
        }else {
            return new Player[0];
        }
    }

    @Override
    public String getDefaultDescriptionText() {
        if (response!=null&&response.description!=null) {
            return response.description.text;
        }else {
            return null;
        }
    }
    @Override
    public String getDefaultDescriptionColor(){
        if (response!=null&&response.description!=null) {
            return response.description.color;
        }else {
            return null;
        }
    }
    @Override
    public ExtraDescr[] getExtraDescription(){
        if (response!=null&&response.description!=null&&response.description.extra!=null) {
            ExtraDescr[] extraDescrs = new ExtraDescr[response.description.extra.length];
            for (int i = 0; i < response.description.extra.length; i++) {
                extraDescrs[i] = new ExtraDescr();
                extraDescrs[i].color = response.description.extra[i].color;
                extraDescrs[i].text = response.description.extra[i].text;
            }
            return extraDescrs;
        }else {
            return new ExtraDescr[0];
        }
    }
    @Override
    public String getFavicon() {
        if (response!=null) {
            return response.favicon;
        }else {
            return null;
        }
    }

}
