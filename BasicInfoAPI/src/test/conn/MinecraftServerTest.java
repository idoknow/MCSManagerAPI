package test.conn;

import main.api.IServerInfo;
import main.conn.MinecraftServer;

public class MinecraftServerTest {
    public static void main(String[] args)throws Exception {
        MinecraftServer minecraftServer=new MinecraftServer("cn-cd-dx-1.natfrp.cloud",18904);
        System.out.println("version:name:"+minecraftServer.getVersionName()+" protocol:"+minecraftServer.getVersionProtocol());
        System.out.println("defaultDescription:color:"+minecraftServer.getDefaultDescriptionColor()+" text:"+minecraftServer.getDefaultDescriptionText());
        IServerInfo.ExtraDescr[] extraDescrs=minecraftServer.getExtraDescription();
        for (IServerInfo.ExtraDescr extraDescr:extraDescrs){
            System.out.println("extraDescription:color:"+extraDescr.color+" text:"+extraDescr.text);
        }
        System.out.println("players:max:"+minecraftServer.getMaxPlayer()+" online:"+minecraftServer.getOnlinePlayer());
        IServerInfo.Player[] players=minecraftServer.getPlayerList();
        for (IServerInfo.Player p:players){
            System.out.println("player:name:"+p.name+" id:"+p.id);
        }
        System.out.println("favicon:"+minecraftServer.getFavicon());
    }
}
