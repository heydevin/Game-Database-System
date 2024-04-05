package entity;

public class Weapons {

    private int WeaponID;
    private int wpDamage;
    private int Price;
    private String Rname;
    private String Wname;

    public Weapons(int WeaponID, int wpDamage, int Price, String Rname, String Wname) {
        this.WeaponID = WeaponID;
        this.wpDamage = wpDamage;
        this.Price = Price;
        this.Rname = Rname;
        this.Wname = Wname;
    }

    public int getWeaponID() {
        return WeaponID;
    }

    public int getwpDamage() {
        return wpDamage;
    }

    public int getPrice() {
        return Price;
    }

    public String getRname() {
        return Rname;
    }

    public String getWname() {
        return Wname;
    }

}