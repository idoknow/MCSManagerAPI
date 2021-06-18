# BasicInfoAPI
[README](README.md) | [中文文档](README_cn.md)

## Version

Please check release page.

## Abstract

Import this API as a jar lib.Provide interfaces to get information,such as server's
version,description(MOTD),player online list and so on that official client can get.

## Usage

```java
/**
 * Create a MinecraftServer object connected with the minecraft server running on port 25565 on host server.address.
 * Caution:this method will try to connect to the determined server and get message that includes basic info,so this method
 * will not return until receive the message.
 */
MinecraftServer minecraftServer=new MinecraftServer("server.address",25565);
/**
 * Call provided methods from interface to get specific info.
 */
System.out.println("version:name:"+minecraftServer.getVersionName()+" protocol:"+minecraftServer.getVersionProtocol());
System.out.println("defaultDescription:color:"+minecraftServer.getDefaultDescriptionColor()+" text:"+minecraftServer.getDefaultDescriptionText());
/**
 * Get the list of extra description includes text and color.
 * If your server does not have extra description(as usual),you can simply use getDefaultDescriptionColor() and getDefaultDescriptionText().
 */
IServerInfo.ExtraDescr[] extraDescrs=minecraftServer.getExtraDescription();
for (IServerInfo.ExtraDescr extraDescr:extraDescrs){
    System.out.println("extraDescription:color:"+extraDescr.color+" text:"+extraDescr.text);
}
System.out.println("players:max:"+minecraftServer.getMaxPlayer()+" online:"+minecraftServer.getOnlinePlayer());
/**
 * Get online player list.
 */
IServerInfo.Player[] players=minecraftServer.getPlayerList();
for (IServerInfo.Player p:players){
    System.out.println("player:name:"+p.name+" id:"+p.id);
}
/**
 * Get server's icon(favicon) described in a Base64 way.
 * Caution:sometime the packet with a very big Base64 data from server will cause a internal exception,you should try again.
 */
System.out.println("favicon:"+minecraftServer.getFavicon());
```
