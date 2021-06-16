# BasicInfoAPI
[README](README.md) | [中文文档](README_cn.md)

## 版本

最近的发行版:0.1,请参阅 Release 页面.

## 摘要

将此API以jar库的形式引入项目。此API提供了获取信息的接口，这些信息包括 服务器的版本 描述（MOTD） 在线玩家列表等能被官方客户端获取并展示在服务器列表的信息。

## 用法

```java
/**
 * 创建一个连接到运行在主机server.address上25565端口的Minecraft服务器的MinecraftServer对象
 * 注意：此方法将会尝试连接到指定的服务器并获取包含基本信息的消息，因此此方法在获取到消息前不会返回，这是一个堵塞的方法
 */
MinecraftServer minecraftServer=new MinecraftServer("server.address",25565);
/**
 * 接下来，调用由接口提供的方法以获取特定的信息
 */
System.out.println("version:name:"+minecraftServer.getVersionName()+" protocol:"+minecraftServer.getVersionProtocol());
System.out.println("defaultDescription:color:"+minecraftServer.getDefaultDescriptionColor()+" text:"+minecraftServer.getDefaultDescriptionText());
/**
 * 获取额外描述的列表，额外描述中包含了颜色和文字
 * 如果你的服务器不含有额外描述（通常来说是这样），你可以简单地调用getDefaultDescriptionColor() 和 getDefaultDescriptionText()方法  
 * 而不需要检查额外描述（extra description）
 */
IServerInfo.ExtraDescr[] extraDescrs=minecraftServer.getExtraDescription();
for (IServerInfo.ExtraDescr extraDescr:extraDescrs){
    System.out.println("extraDescription:color:"+extraDescr.color+" text:"+extraDescr.text);
}
/**
 * 获取基本玩家信息
 */
        System.out.println("players:max:"+minecraftServer.getMaxPlayer()+" online:"+minecraftServer.getOnlinePlayer());
/**
 * 获取在线玩家列表
 */
IServerInfo.Player[] players=minecraftServer.getPlayerList();
for (IServerInfo.Player p:players){
    System.out.println("player:name:"+p.name+" id:"+p.id);
}
/**
 * 获取以Base64编码的服务器图标（favicon）
 * 注意：有些时候某个服务器包含了巨量的Base64编码的favicon，这会引起一些内部错误，请重新调用
 */
System.out.println("favicon:"+minecraftServer.getFavicon());
```
