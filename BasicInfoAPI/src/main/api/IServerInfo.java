package main.api;

/**
 * Defines interfaces to get the basic info of a Minecraft server.
 * @author Rock Chin
 */
public interface IServerInfo {
    class Player{
        public String name;
        public String id;
    }
    class ExtraDescr{
        public String text;
        public String color;
    }
    String getVersionName();
    int getVersionProtocol();
    int getMaxPlayer();
    int getOnlinePlayer();
    Player[] getPlayerList();
    String getDefaultDescriptionText();
    String getDefaultDescriptionColor();
    ExtraDescr[] getExtraDescription();
    String getFavicon();
}
