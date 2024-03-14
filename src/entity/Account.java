package entity;

public class Account {
    private int UID;
    private String password;
    private String language;

    public Account(int UID, String password, String language){
        this.UID = UID;
        this.password = password;
        this.language = language;
    }

    public int getUID() {
        return UID;
    }

    public String getPassword() {
        return password;
    }

    public String getLanguage() {
        return language;
    }
}
