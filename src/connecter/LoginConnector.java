package connecter;

public interface LoginConnector {
    void login(String username, String password);
    void switchPage(int pageIndex);

    String getUserInfoFromSQL(String item, String userName);

}
