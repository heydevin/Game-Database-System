package entity;

public class CharacterInfo {
    private int level;
    private int money;
    private String characterName;
    private String roleName;
    private String mapID;
    private String currLoc;

    public CharacterInfo(int level, int money, String cname, String rname, String currLoc){
        this.level = level;
        this.money = money;
        this.characterName = cname;
        this.roleName = rname;
        this.mapID = getMapIDFromLocation(currLoc);
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

    public String getMapIDFromLocation(String currLoc) {
        if(currLoc == "Town"){
            return "M01";
        } else if(currLoc == "Forest"){
            return "M02";
        } else if(currLoc == "Ocean"){
            return "M03";
        } else if(currLoc == "Desert"){
            return "M04";
        } else if(currLoc == "Highland"){
            return "M05";
        }
        return null;
    }

    public String getCurrLoc() {
        return currLoc;
    }
}