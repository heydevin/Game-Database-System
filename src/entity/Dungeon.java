package entity;

public class Dungeon {
    private int DungeonID;
    private String DungeonName;
    private String Item;
    private boolean isClear;

    public Dungeon(int DungeonID, String DungeonName, String Item, boolean isClear){
        this.DungeonID = DungeonID;
        this.DungeonName = DungeonName;
        this.Item = Item;
        this.isClear = isClear;
    }
}
