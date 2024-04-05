package connecter;

import entity.Account;
import entity.CharacterInfo;
import entity.User;

import javax.swing.table.DefaultTableModel;

public interface LoginConnector {
    void login(String username, String password);
    void switchPage(int pageIndex);
    User getUserFromSQL(String name);
    Account getAccountFromSQL(String email);
    void insertAccountIntoSQL(Account account);
    void insertCharacterIntoSQL(CharacterInfo character);
    DefaultTableModel groupByQuery();
    void deleteCharacterInfoFromSQL(String cName);
    void updateCharacterMoney(int newLevel,String cName);

    String[] getAffordableWeapons(String characterName);

    void updateCharacterLevel(int newLevel, String charName);
}
