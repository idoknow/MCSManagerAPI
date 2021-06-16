package main.api;

/**
 * Defines interfaces to get the basic info of a Minecraft server.
 * @author Rock Chin
 */
public interface IServerInfo {
    class Player{
        String name;
        String id;
    }
    String getVersionName();
    int getVersionProtocol();
    int getMaxPlayer();
    int getOnlinePlayer();
    Player[] getPlayerList();
    String getServerDescription();
    String getFavicon();
    String getJSONRawData();
}
