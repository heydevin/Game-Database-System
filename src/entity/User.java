package entity;

import java.sql.Date;

public class User {
    private String name;
    private String email;
    private Date birthday;

    public User(String name, String email, Date birthday){
        this.name = name;
        this.email = email;
        this.birthday = birthday;
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
}
