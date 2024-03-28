package controller;

import connecter.LoginConnector;
import database.DatabaseConnectionHandler;
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
}
