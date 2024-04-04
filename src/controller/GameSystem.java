package controller;

import connecter.LoginConnector;
import database.DatabaseConnectionHandler;
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
        boolean didConnect = dbHandler.login(username, password);
        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.closeLogin();
            System.out.println("Successfully Connected! Switching to Game Page...");
            // switch to account page first.
            GameWindow = new GamePage(this);
            savingDataWindow = new SavingDataPage(this);
            savingDataWindow.close();
            characterWindow = new CharacterPage(this);
            characterWindow.close();
            mapWindow = new MapPage(this);
            mapWindow.close();
            // incomplete
            dbHandler.databaseSetup();
//            this.showUser();
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
            GameWindow.close();
            savingDataWindow.open();  // OPEN!!
            characterWindow.close();
            mapWindow.close();
        } else if (pageIndex == 3) {
            // Index 3 is Character Window
            GameWindow.close();
            savingDataWindow.close();
            characterWindow.open();  // OPEN!!
            mapWindow.close();
        } else if (pageIndex == 4) {
            // Index 4 is Map Window
            GameWindow.close();
            savingDataWindow.close();
            characterWindow.close();
            mapWindow.open();  // OPEN!!
        }
    }

    public void showUser() {
        User[] models = dbHandler.getUserInfo();

        for (int i = 0; i < models.length; i++) {
            User model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getName());
            System.out.printf("%-20.20s", model.getEmail());
            System.out.printf("%-20.20s", model.getBirthday());
            System.out.println();
        }
    }

    public String getUserInfoFromSQL(String item, String userName) {
        User[] models = dbHandler.getUserInfo();
        String info = "need to change";

        for (int i = 0; i < models.length; i++) {
            User model = models[i];
            if(model.getName().equals(userName)){
                if(item == "Email"){
                    info = model.getEmail();
                } else if(item == "Name"){
                    info = model.getName();
                } else {
                    info = model.getBirthday().toString();
                }
            }
        }
        return info;
    }
}
