package connecter;

public interface LoginConnector {
    void login(String username, String password);
    void switchPage(int pageIndex);
}
