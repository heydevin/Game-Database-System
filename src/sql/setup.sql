-- CREATE TABLE Part:

CREATE TABLE UserTable(
    Email VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Birthday DATE
);

CREATE TABLE Account(
    UserID INT PRIMARY KEY,
    Password VARCHAR(50) NOT NULL,
    Language VARCHAR(50) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    FOREIGN KEY (Email) REFERENCES UserTable(Email)
);

CREATE TABLE SavingData(
    DataID CHAR(10),
    UserID INT,
    CreatingDate DATE NOT NULL,
    PRIMARY KEY (DataID, UserID),
    FOREIGN KEY (UserID) REFERENCES Account(UserID)
);


CREATE TABLE Roles(
                      Rname VARCHAR(50) PRIMARY KEY);


CREATE TABLE Weapons(
                        WeaponID INTEGER,
                        wpDamage INTEGER,
                        Price INTEGER,
                        Wname VARCHAR(50),
                        Rname VARCHAR(50),
                        FOREIGN KEY(Rname) REFERENCES Roles(Rname) on DELETE CASCADE);

CREATE TABLE Map(
                    MapID CHAR(10) PRIMARY KEY,
                    MapName VARCHAR(50) NOT NULL
);

CREATE TABLE LockedArea(
                           MapID CHAR(10),
                           MapName VARCHAR(50),
                           FoggyArea CHAR(10),
                           FOREIGN KEY(MapID) REFERENCES Map(MapID));

CREATE TABLE UnlockArea(
                           MapID CHAR(10),
                           MapName VARCHAR(50),
                           CheckPoint CHAR(10),
                           FOREIGN KEY(MapID) REFERENCES Map(MapID));

CREATE TABLE Coordinate(
                           X_Coord CHAR(10),
                           MapID CHAR(10),
                           Y_Coord CHAR(10),
                           PRIMARY KEY(X_Coord,Y_Coord, MapID),
                           FOREIGN KEY(MapID) REFERENCES Map(MapID));

CREATE TABLE LearnSkills_Stats(
                                  Sname VARCHAR(50),
                                  SDamage INTEGER,
                                  Requirement VARCHAR(50),
                                  PRIMARY KEY(Sname));

CREATE TABLE LearnSkills_Info(
                                 skillID CHAR(10) PRIMARY KEY,
                                 Sname VARCHAR(50),
                                 IsLearned CHAR(1),
                                 Rname VARCHAR(50),
                                 FOREIGN KEY(Rname) REFERENCES Roles(Rname) on DELETE CASCADE);

CREATE TABLE BossInfo(
                         Bname VARCHAR(50),
                         Blevel INTEGER,
                         BossDMG INTEGER,
                         BossHP INTEGER,
                         PRIMARY KEY(Bname));

CREATE TABLE DungeonStats(
                             dungeonName VARCHAR(50) PRIMARY KEY,
                             clearStatus CHAR(1));

CREATE TABLE DungeonInfo(
                            dungeonID CHAR(10) PRIMARY KEY,
                            dungeonName VARCHAR(50),
                            Item VARCHAR(50),
                            Bname VARCHAR(50),
                            BossID CHAR(10),
                            MapID CHAR(10),
                            FOREIGN KEY(MapID) REFERENCES Map(MapID));

CREATE TABLE Characters_Stats(
                                 HP INTEGER,
                                 Playtime INT,
                                 charLevel INTEGER PRIMARY KEY);

CREATE TABLE Characters_Info(
                                Cname VARCHAR(50) PRIMARY KEY,
                                charLevel INTEGER,
                                Money INTEGER,
                                Rname VARCHAR(50),
                                MapID CHAR(10),
                                currLoc CHAR(20),
                                FOREIGN KEY(MapID) REFERENCES Map(MapID),
                                FOREIGN KEY(Rname) REFERENCES Roles(Rname);

-- INSERT INTO Part:

INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('Devin@gmail.com','Desheng', TO_DATE('2000-1-10', 'yyyy-mm-dd'));
INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('Xiran@gmail.com','Xiran', TO_DATE('2002-4-11', 'yyyy-mm-dd'));
INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('James@gmail.com','James', TO_DATE('2000-1-03', 'yyyy-mm-dd'));
INSERT INTO UserTable (Email, Name, Birthday)

INSERT INTO Account (UserID, Password, Language, Email)
VALUES (100000001, 'p', 'Chinese', 'Devin@gmail.com');
INSERT INTO Account (UserID, Password, Language, Email)
VALUES (100000002, '123', 'English', 'Xiran@gmail.com');
INSERT INTO Account (UserID, Password, Language, Email)
VALUES (100000003, '123', 'English', 'James@gmail.com');

INSERT INTO Characters_Info (Cname, charLevel, Money, Rname, MapID, currLoc)
VALUES ('Bobby', 15, 100, 'Warrior', 'M03', 'Ocean');
INSERT INTO Characters_Info (Cname, charLevel, Money, Rname, MapID, currLoc)
VALUES ('Austin', 40, 200, 'Assassin', 'M01', 'Town');
INSERT INTO Characters_Info (Cname, charLevel, Money, Rname, MapID, currLoc, DataID, UserID)
VALUES ('Carols', 33, 300, 'Mage', 'M01', 'Town');
INSERT INTO Characters_Info (Cname, charLevel, Money, Rname, MapID, currLoc, DataID, UserID)
VALUES ('Duke', 72, 90, 'Archer', 'M04', 'Desert');
INSERT INTO Characters_Info (Cname, charLevel, Money, Rname, MapID, currLoc, DataID, UserID)
VALUES ('Julia', 18, 80, 'Berserker', 'M05', 'Highland');
INSERT INTO Characters_Info (Cname, charLevel, Money, Rname, MapID, currLoc, DataID, UserID)
VALUES ('Katty', 44, 700, 'Warrior', 'M01', 'Town');
INSERT INTO Characters_Info (Cname, charLevel, Money, Rname, MapID, currLoc, DataID, UserID)
VALUES ('Jones', 35, 220, 'Archer', 'M01', 'Town');


INSERT INTO Weapons (WeaponID, wpDamage, Price, Wname, Rname)
VALUES (1000000001, 100, 150, 'Hammer', 'Warrior' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Wname, Rname)
VALUES (1000000002, 1, 150, 'stick', 'Warrior' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Wname, Rname)
VALUES (1000000002, 20, 200, 'Knife', 'Assassin' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Wname, Rname)
VALUES (1000000002, 51, 250, 'Magic wand', 'Mage' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Wname, Rname)
VALUES (1000000001, 20, 300, 'Bow', 'Archer' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Wname, Rname)
VALUES (1000000002, 20, 400, 'Sword', 'Berserker' );



INSERT INTO Roles (Rname) VALUES ('Warrior');
INSERT INTO Roles (Rname) VALUES ('Assassin');
INSERT INTO Roles (Rname) VALUES ('Mage');
INSERT INTO Roles (Rname) VALUES ('Archer');
INSERT INTO Roles (Rname) VALUES ('Berserker');

INSERT INTO Map(MapID, MapName) VALUES ('M01', 'Town');
INSERT INTO Map(MapID, MapName) VALUES ('M02', 'Forest');
INSERT INTO Map(MapID, MapName) VALUES ('M03', 'Ocean');
INSERT INTO Map(MapID, MapName) VALUES ('M04', 'Desert');
INSERT INTO Map(MapID, MapName) VALUES ('M05', 'Highland');



