package connecter;

import entity.Account;
import entity.CharacterInfo;
import entity.User;

public interface LoginConnector {
    void login(String username, String password);
    void switchPage(int pageIndex);

    User getUserFromSQL(String name);
    Account getAccountFromSQL(String email);
    void insertAccountIntoSQL(Account account);
    void insertCharacterIntoSQL(CharacterInfo character);

    void groupByQuery();
}
