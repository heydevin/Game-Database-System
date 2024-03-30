package entity;

import java.sql.Date;

public class UserAccountModel {
    private String name;
    private String email;
    private Date birthday;
    private int UID;
    private int password;

    public UserAccountModel(String name, Date birthday, String email, int password, int UID){
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.UID = UID;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getUID() {
        return UID;
    }

    public int getPassword() {
        return password;
    }

//    public String getLanguage() {
//        return language;
//    }
}
