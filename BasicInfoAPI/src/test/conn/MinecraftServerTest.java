package test.conn;

import main.api.IServerInfo;
import main.conn.MinecraftServer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MinecraftServerTest {
    public static void main(String[] args)throws Exception {
        MinecraftServer minecraftServer=new MinecraftServer("hs7.nide8.com",25597,true);
        System.out.println("available:"+minecraftServer.isAvailable());
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
        System.out.println("favicon:"+minecraftServer.getFaviconBase64());

        if (minecraftServer.getFaviconImage()!=null) {
            FaviconDisplay display = new FaviconDisplay(minecraftServer.getFaviconImage());
            display.setBounds(200, 200, 200, 200);
            display.setVisible(true);
            display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }
    static class FaviconDisplay extends JFrame{
        BufferedImage bufferedImage;
        @Override
        public void paint(Graphics graphics){
            graphics.drawImage(this.bufferedImage,15,40,this);
        }
        public FaviconDisplay(BufferedImage img){
            this.bufferedImage=img;
        }
    }
}
