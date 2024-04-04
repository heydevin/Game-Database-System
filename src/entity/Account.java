package entity;

public class Account {
    private int UID;
    private String password;
    private String language;
    private String email;

    public Account(int UID, String password, String language, String email){
        this.UID = UID;
        this.password = password;
        this.language = language;
        this.email = email;
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

    public String getEmail() {
        return email;
    }
}
