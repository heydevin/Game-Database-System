package entity;

public class CharacterInfo {
    private int level;
    private int money;
    private String characterName;
    private String roleName;
    private String mapID;
    private String currLoc;

    public CharacterInfo(int level, int money, String cname, String rname, String mapID, String currLoc){
        this.level = level;
        this.money = money;
        this.characterName = cname;
        this.roleName = rname;
        this.mapID = mapID;
        this.currLoc = currLoc;
    }

    public int getLevel() {
        return level;
    }

    public int getMoney() {
        return money;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getMapID() {
        return mapID;
    }

    public String getCurrLoc() {
        return currLoc;
    }
}