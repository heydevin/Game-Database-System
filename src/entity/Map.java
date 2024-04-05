package entity;

public class Map {
    private String MapID;
    private String MapName;

    public Map(String MapID, String MapName){
        this.MapID = MapID;
        this.MapName = MapName;
    }

    public String getMapID() {
        return MapID;
    }

    public String getMapName() {
        return MapName;
    }
}
