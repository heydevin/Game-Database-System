package controller;

import connecter.LoginConnector;
import database.DatabaseConnectionHandler;
import ui.LoginPage;

public class GameSystem implements LoginConnector {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginPage loginWindow = null;

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
            loginWindow.dispose();
            System.out.println("connected");
        } else {
            loginWindow.handleLoginFailed();
        }
    }
}
