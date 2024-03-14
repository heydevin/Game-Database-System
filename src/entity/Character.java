package entity;

public class Character {
    private int HP;
    private int level;
    private String characterName;
    private int money;

    public Character(int HP, int level, String name, int money){
        this.HP = HP;
        this.level = level;
        this.characterName = name;
        this.money = money;
    }
}
