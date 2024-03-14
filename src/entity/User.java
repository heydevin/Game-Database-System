package entity;

public class User {
    private String name;
    private String email;
    private String birthday;

    public User(String name, String email, String birthday){
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

    public String getBirthday() {
        return birthday;
    }
}
