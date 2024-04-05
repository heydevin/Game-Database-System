package database;

import entity.*;
import util.PrintablePreparedStatement;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public User[] getUserInfo() {
        ArrayList<User> result = new ArrayList<User>();

        try {
            String query = "SELECT * FROM USERTABLE";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User model = new User(rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getDate("Birthday"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new User[result.size()]);
    }

    public Account[] getAccountInfo() {
        ArrayList<Account> result = new ArrayList<Account>();
        try {
            String query = "SELECT * FROM ACCOUNT";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Account model = new Account(rs.getInt("UserID"),
                        rs.getString("Password"),
                        rs.getString("Language"),
                        rs.getString("Email"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Account[result.size()]);
    }

    private void dropBranchTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            try {ps.execute("DROP TABLE UserTable cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE Account cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE SavingData cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE Roles cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE UserTable cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE Weapons cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE Map cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE LockedArea cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE UnlockArea cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE Coordinate cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE LearnSkills_Stats cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE LearnSkills_Info cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE BossInfo cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE DungeonStats cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE DungeonInfo cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE Characters_Stats cascade constraints purge");}
            catch (SQLException e){ }
            try {ps.execute("DROP TABLE Characters_Info cascade constraints purge");}
            catch (SQLException e){ }
            ps.close();
            System.out.println("Drop finished");
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void insertUserModel(User model) {
        try {
            String query = "INSERT INTO USERTABLE VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, model.getEmail());
            ps.setString(2, model.getName());
            ps.setDate(3, model.getBirthday());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertRolesModel(Role role) {
        try {
            String query = "INSERT INTO ROLES VALUES (?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, role.getRoleName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertMapModel(Map map) {
        try {
            String query = "INSERT INTO MAP VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, map.getMapID());
            ps.setString(2, map.getMapName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertCharacterInfoModel(CharacterInfo characterInfo) {
        try {
            String query = "INSERT INTO Characters_Info VALUES (?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, characterInfo.getCharacterName());
            ps.setInt(2, characterInfo.getLevel());
            ps.setInt(3, characterInfo.getMoney());
            ps.setString(4, characterInfo.getRoleName());
            ps.setString(5, characterInfo.getMapID());
            ps.setString(6, characterInfo.getCurrLoc());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertAccountModel(Account model) {
        try {
            String query = "INSERT INTO ACCOUNT VALUES (?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getUID());
            ps.setString(2, model.getPassword());
            ps.setString(3, model.getLanguage());
            ps.setString(4, model.getEmail());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void initializeRoles(){
        insertRolesModel(new Role("Warrior"));
        insertRolesModel(new Role("Assassin"));
        insertRolesModel(new Role("Mage"));
        insertRolesModel(new Role("Archer"));
        insertRolesModel(new Role("Berserker"));
    }

    public void initializeMaps(){
        insertMapModel(new Map("M01", "Town"));
        insertMapModel(new Map("M02", "Forest"));
        insertMapModel(new Map("M03", "Ocean"));
        insertMapModel(new Map("M04", "Desert"));
        insertMapModel(new Map("M05", "Highland"));
    }

    public void initializeUsers() {
        User UserModel1 = new User("Desheng", "Devin@gmail.com", Date.valueOf("2000-1-10"));
        User UserModel2 = new User("Xiran", "Xiran@gmail.com", Date.valueOf("2000-1-01"));
        User UserModel3 = new User("James", "James@gmail.com", Date.valueOf("2000-1-03"));

        insertUserModel(UserModel1);
        insertUserModel(UserModel2);
        insertUserModel(UserModel3);

        Account AccountModel1 = new Account(100000001, "p","Chinese", UserModel1.getEmail());

        insertAccountModel(AccountModel1);
    }
    public void databaseSetup() {
        dropBranchTableIfExists();

        try {
            String query = "CREATE TABLE UserTable (\n" +
                    "    Email VARCHAR(50) PRIMARY KEY,\n" +
                    "    Name VARCHAR(50) NOT NULL,\n" +
                    "    Birthday DATE\n" +
                    ")";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating UserTable Table");
        }

        try {
            String query = "CREATE TABLE Account(\n" +
                    "    UserID INT PRIMARY KEY,\n" +
                    "    Password VARCHAR(50) NOT NULL,\n" +
                    "    Language VARCHAR(50) NOT NULL,\n" +
                    "    Email VARCHAR(50) NOT NULL,\n" +
                    "    FOREIGN KEY (Email) REFERENCES UserTable(Email)\n" +
                    ")";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Account Table");
        }

        try {
            String query = "CREATE TABLE SavingData(\n" +
                    "    DataID CHAR(10),\n" +
                    "    UserID INT,\n" +
                    "    CreatingDate DATE NOT NULL,\n" +
                    "    PRIMARY KEY (DataID, UserID),\n" +
                    "    FOREIGN KEY (UserID) REFERENCES Account(UserID)\n" +
                    ")";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating SavingData Table");
        }

        try {
            String query = "CREATE TABLE Roles(\n" +
                    "                      Rname VARCHAR(50) PRIMARY KEY)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Roles Table");
        }

        try {
            String query = "CREATE TABLE Weapons(\n" +
                    "                        WeaponID CHAR(10),\n" +
                    "                        wpDamage INTEGER,\n" +
                    "                        Price INTEGER,\n" +
                    "                        Rname VARCHAR(50),\n" +
                    "                        FOREIGN KEY(Rname) REFERENCES Roles(Rname) on DELETE CASCADE)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Weapons Table");
        }

        try {
            String query = "CREATE TABLE Map(\n" +
                    "                    MapID CHAR(10) PRIMARY KEY,\n" +
                    "                    MapName VARCHAR(50) NOT NULL\n" +
                    ")";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Map Table");
        }

        try {
            String query = "CREATE TABLE LockedArea(\n" +
                    "                           MapID CHAR(10),\n" +
                    "                           MapName VARCHAR(50),\n" +
                    "                           FoggyArea CHAR(10),\n" +
                    "                           FOREIGN KEY(MapID) REFERENCES Map(MapID))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating LockedArea Table");
        }

        try {
            String query = "CREATE TABLE UnlockArea(\n" +
                    "                           MapID CHAR(10),\n" +
                    "                           MapName VARCHAR(50),\n" +
                    "                           CheckPoint CHAR(10),\n" +
                    "                           FOREIGN KEY(MapID) REFERENCES Map(MapID))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating UnlockArea Table");
        }

        try {
            String query = "CREATE TABLE Coordinate(\n" +
                    "                           X_Coord CHAR(10),\n" +
                    "                           MapID CHAR(10),\n" +
                    "                           Y_Coord CHAR(10),\n" +
                    "                           PRIMARY KEY(X_Coord,Y_Coord, MapID),\n" +
                    "                           FOREIGN KEY(MapID) REFERENCES Map(MapID))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Coordinate Table");
        }

        try {
            String query = "CREATE TABLE LearnSkills_Stats(\n" +
                    "                                  Sname VARCHAR(50),\n" +
                    "                                  SDamage INTEGER,\n" +
                    "                                  Requirement VARCHAR(50),\n" +
                    "                                  PRIMARY KEY(Sname))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating LearnSkills_Stats Table");
        }

        try {
            String query = "CREATE TABLE LearnSkills_Info(\n" +
                    "                                 skillID CHAR(10) PRIMARY KEY,\n" +
                    "                                 Sname VARCHAR(50),\n" +
                    "                                 IsLearned CHAR(1),\n" +
                    "                                 Rname VARCHAR(50),\n" +
                    "                                 FOREIGN KEY(Rname) REFERENCES Roles(Rname) on DELETE CASCADE)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating LearnSkills_Info Table");
        }

        try {
            String query = "CREATE TABLE BossInfo(\n" +
                    "                         Bname VARCHAR(50),\n" +
                    "                         Blevel INTEGER,\n" +
                    "                         BossDMG INTEGER,\n" +
                    "                         BossHP INTEGER,\n" +
                    "                         PRIMARY KEY(Bname))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating BossInfo Table");
        }

        try {
            String query = "CREATE TABLE DungeonStats(\n" +
                    "                             dungeonName VARCHAR(50) PRIMARY KEY,\n" +
                    "                             clearStatus CHAR(1))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating DungeonStats Table");
        }

        try {
            String query = "CREATE TABLE DungeonInfo(\n" +
                    "                            dungeonID CHAR(10) PRIMARY KEY,\n" +
                    "                            dungeonName VARCHAR(50),\n" +
                    "                            Item VARCHAR(50),\n" +
                    "                            Bname VARCHAR(50),\n" +
                    "                            BossID CHAR(10),\n" +
                    "                            MapID CHAR(10),\n" +
                    "                            FOREIGN KEY(MapID) REFERENCES Map(MapID))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating DungeonInfo Table");
        }

        try {
            String query = "CREATE TABLE Characters_Stats(\n" +
                    "                                 HP INTEGER,\n" +
                    "                                 Playtime INT,\n" +
                    "                                 charLevel INTEGER PRIMARY KEY)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Characters_Stats Table");
        }

        try {
            String query = "CREATE TABLE Characters_Info(\n" +
                    "                                Cname VARCHAR(50) PRIMARY KEY,\n" +
                    "                                charLevel INTEGER,\n" +
                    "                                Money INTEGER,\n" +
                    "                                Rname VARCHAR(50),\n" +
                    "                                MapID CHAR(10),\n" +
                    "                                currLoc CHAR(20),\n" +
                    "                                FOREIGN KEY(MapID) REFERENCES Map(MapID),\n" +
                    "                                FOREIGN KEY(Rname) REFERENCES Roles(Rname))";
//            String query = "CREATE TABLE Characters_Info(\n" +
//                    "                                Cname VARCHAR(50) PRIMARY KEY,\n" +
//                    "                                charLevel INTEGER,\n" +
//                    "                                Money INTEGER,\n" +
//                    "                                Rname VARCHAR(50),\n" +
//                    "                                MapID CHAR(10),\n" +
//                    "                                currLoc CHAR(20),\n" +
//                    "                                DataID CHAR(10),\n" +
//                    "                                UserID INT,\n" +
//                    "                                FOREIGN KEY(MapID) REFERENCES Map(MapID),\n" +
//                    "                                FOREIGN KEY(Rname) REFERENCES Roles(Rname),\n" +
//                    "                                FOREIGN KEY (DataID, UserID) REFERENCES SavingData(DataID, UserID))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Characters_Info Table");
        }
    }
}
