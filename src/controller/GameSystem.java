package controller;

import connecter.LoginConnector;
import database.DatabaseConnectionHandler;
import entity.Account;
import entity.User;
import ui.*;

public class GameSystem implements LoginConnector {
    private DatabaseConnectionHandler dbHandler;
    private LoginPage loginWindow;
    private CharacterPage characterWindow;
    private MapPage mapWindow;
    private SavingDataPage savingDataWindow;
    private GamePage GameWindow;


    public GameSystem() {
        dbHandler = new DatabaseConnectionHandler();
    }

    public static void main(String args[]) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.start();
    }

    private void start() {
        loginWindow = new LoginPage(this);
    }

    public void login(String username, String password) {
        if (dbHandler.login(username, password)) {
            loginWindow.closeLogin();
            System.out.println("Successfully Connected! Switching to Game Page...");

            GameWindow = new GamePage(this);
            // incomplete
            dbHandler.databaseSetup();
            dbHandler.initializeUsers();
        } else {
            loginWindow.handleLoginFailed();
        }
    }

    public void switchPage(int pageIndex){
        if(pageIndex == 1){
            // Index 1 is Game Window
            GameWindow.open();  // OPEN!!
            savingDataWindow.close();
            characterWindow.close();
            mapWindow.close();
        } else if (pageIndex == 2) {
            // Index 2 is Saving Data Window
            if(savingDataWindow == null){
                savingDataWindow = new SavingDataPage(this);
            }
            GameWindow.close();
            savingDataWindow.open();  // OPEN!!
            characterWindow.close();
            mapWindow.close();
        } else if (pageIndex == 3) {
            // Index 3 is Character Window
            if(characterWindow == null){
                characterWindow = new CharacterPage(this);
            }
            GameWindow.close();
            savingDataWindow.close();
            characterWindow.open();  // OPEN!!
            mapWindow.close();
        } else if (pageIndex == 4) {
            // Index 4 is Map Window
            if(mapWindow == null){
                mapWindow = new MapPage(this);
            }
            GameWindow.close();
            savingDataWindow.close();
            characterWindow.close();
            mapWindow.open();  // OPEN!!
        }
    }

    public User getUserFromSQL(String name) {
        User[] models = dbHandler.getUserInfo();
        User one = null;
        for (int i = 0; i < models.length; i++) {
            User model = models[i];
            if(model.getName().equals(name)){
                one = model;
            }
        }
        return one;
    }

    public Account getAccountFromSQL(String email) {
        Account[] models = dbHandler.getAccountInfo();
        Account one = null;
        for (int i = 0; i < models.length; i++) {
            Account model = models[i];
            if(model.getEmail().equals(email)){
                one = model;
            }
        }
        return one;
    }

    public void insertAccountIntoSQL(Account account) {
        dbHandler.insertAccountModel(account);
    }
}
