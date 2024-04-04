package database;

import entity.Account;
import entity.User;
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
            String query = "SELECT * FROM user_table_1";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User model = new User(rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("birthday"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new User[result.size()]);
    }

    private void dropBranchTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ps.execute("DROP TABLE user_table_1");
                break;
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void insertUserModel(User model) {
        try {
            String query = "INSERT INTO user_table_1 VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, model.getEmail());
            ps.setString(2, model.getName());
            ps.setDate(3, model.getBirthday());
//            if (model.getPhoneNumber() == 0) {
//                ps.setNull(5, java.sql.Types.INTEGER);
//            } else {
//                ps.setInt(5, model.getPhoneNumber());
//            }
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void databaseSetup() {
        dropBranchTableIfExists();

        try {
            String query = "CREATE TABLE user_table_1 (email varchar2(50) PRIMARY KEY, name varchar2(50) NOT NULL, birthday Date)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating User Table");
        }

        try {
            String query = "CREATE TABLE Account(\n" +
                    "    UID INT PRIMARY KEY,\n" +
                    "    Password VARCHAR(50) NOT NULL,\n" +
                    "    Language VARCHAR(50) NOT NULL,\n" +
                    "    Email VARCHAR(50) NOT NULL,\n" +
                    "    FOREIGN KEY (Email) REFERENCES User(Email)\n" +
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
                    "    UID INT,\n" +
                    "    CreatingDate DATE NOT NULL,\n" +
                    "    PRIMARY KEY (DataID, UID),\n" +
                    "    FOREIGN KEY (UID) REFERENCES Account(UID)\n" +
                    ")";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating SavingData Table");
        }

        try {
            String query = "CREATE TABLE Store (\n" +
                    "                       DataID CHAR(10),\n" +
                    "                       UID INT,\n" +
                    "                       Cname VARCHAR(50),\n" +
                    "                       Playtime INT,\n" +
                    "                       PRIMARY KEY (DataID, UID, Cname),\n" +
                    "                       FOREIGN KEY (DataID, UID) REFERENCES SavingData(DataID, UID),\n" +
                    "                       FOREIGN KEY (Cname) REFERENCES Characters_Info(Cname)\n" +
                    ")";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Store Table");
        }

        try {
            String query = "CREATE TABLE Characters_Stats(\n" +
                    "                                 HP INTEGER,\n" +
                    "                                 Level INTEGER PRIMARY KEY)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Characters_Stats Table");
        }

        try {
            String query = "CREATE TABLE Characters_Info(\n" +
                    "                                Level INTEGER,\n" +
                    "                                Money INTEGER,\n" +
                    "                                Cname VARCHAR(50) PRIMARY KEY,\n" +
                    "                                Rname VARCHAR(50),\n" +
                    "                                MapID CHAR(10),\n" +
                    "                                currLoc CHAR(20),\n" +
                    "                                FOREIGN KEY(MapID) REFERENCES Map(MapID),\n" +
                    "                                FOREIGN KEY(Rname) REFERENCES Roles(Rname))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Characters_Info Table");
        }

        try {
            String query = "CREATE TABLE Characters_Stats(\n" +
                    "                                 HP INTEGER,\n" +
                    "                                 Level INTEGER PRIMARY KEY)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Characters_Stats Table");
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
            String query = "CREATE TABLE Roles(\n" +
                    "                      Rname VARCHAR(50) PRIMARY KEY ,\n" +
                    "                      Cname VARCHAR(50),\n" +
                    "                      FOREIGN KEY(Cname) REFERENCES Characters_Info(Cname) on DELETE CASCADE)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println("Error in Creating Roles Table");
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
                    "                           PRIMARY KEY(MapID))";
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

//        User UserModel1 = new User("Desheng", "Devin@gmail.com", Date.valueOf("2000-1-10"));
//        User UserModel2 = new User("Xiran", "Xiran@gmail.com", Date.valueOf("2000-1-01"));
//        User UserModel3 = new User("James", "James@gmail.com", Date.valueOf("2000-1-03"));
//
//        insertUserModel(UserModel1);
//        insertUserModel(UserModel2);
//        insertUserModel(UserModel3);

    }
}
