-- create table part:
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
    FOREIGN KEY (Email) REFERENCES User(Email)
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
                        WeaponID CHAR(10),
                        wpDamage INTEGER,
                        Price INTEGER,
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
                                 charLevel INTEGER PRIMARY KEY);

CREATE TABLE Characters_Info(
                                charLevel INTEGER,
                                Money INTEGER,
                                Cname VARCHAR(50) PRIMARY KEY,
                                Rname VARCHAR(50),
                                MapID CHAR(10),
                                currLoc CHAR(20),
                                FOREIGN KEY(MapID) REFERENCES Map(MapID),
                                FOREIGN KEY(Rname) REFERENCES Roles(Rname));

CREATE TABLE Store (
                       DataID CHAR(10),
                       UserID INT,
                       Cname VARCHAR(50),
                       Playtime INT,
                       PRIMARY KEY (DataID, UserID, Cname),
                       FOREIGN KEY (DataID, UserID) REFERENCES SavingData(DataID, UserID),
                       FOREIGN KEY (Cname) REFERENCES Characters_Info(Cname));

-- insert into part:

INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('test1@gmail.com','Test User 1', TO_DATE('01-01-2001', 'dd-mm-yyyy'));
INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('test2@gmail.com','Test User 2', TO_DATE('02-02-2002', 'dd-mm-yyyy'));
INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('test3@gmail.com','Test User 3', TO_DATE('03-03-2003', 'dd-mm-yyyy'));
INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('test4@gmail.com','Test User 4', TO_DATE('04-04-2004', 'dd-mm-yyyy'));
INSERT INTO UserTable (Email, Name, Birthday)
VALUES ('test5@gmail.com','Test User 5', TO_DATE('05-05-2005', 'dd-mm-yyyy'));

INSERT INTO Account (UserID, Password, Language, Email)
VALUES (1, 'password1', 'English', 'test1@gmail.com');
INSERT INTO Account (UserID, Password, Language, Email)
VALUES (2, 'password2', 'English', 'test2@gmail.com');
INSERT INTO Account (UserID, Password, Language, Email)
VALUES (3, 'password3', 'English', 'test3@gmail.com');
INSERT INTO Account (UserID, Password, Language, Email)
VALUES (4, 'password4', 'English', 'test4@gmail.com');
INSERT INTO Account (UserID, Password, Language, Email)
VALUES (5, 'password5', 'English', 'test5@gmail.com');

INSERT INTO SavingData (DataID, UserID, CreatingDate)
VALUES (1, 1, TO_DATE('01-JAN-2022', 'DD-MON-YYYY'));
INSERT INTO SavingData (DataID, UserID, CreatingDate)
VALUES (2, 2, TO_DATE('02-FEB-2022', 'DD-MON-YYYY'));
INSERT INTO SavingData (DataID, UserID, CreatingDate)
VALUES (3, 3, TO_DATE('03-MAR-2022', 'DD-MON-YYYY'));
INSERT INTO SavingData (DataID, UserID, CreatingDate)
VALUES (4, 4, TO_DATE('04-APR-2022', 'DD-MON-YYYY'));
INSERT INTO SavingData (DataID, UserID, CreatingDate)
VALUES (5, 5, TO_DATE('05-MAY-2022', 'DD-MON-YYYY'));

INSERT INTO Store (DataID, UserID, Cname, Playtime)
VALUES (1, 1, 'Character1', 5);
INSERT INTO Store (DataID, UserID, Cname, Playtime)
VALUES (2, 2, 'Character2', 10);
INSERT INTO Store (DataID, UserID, Cname, Playtime)
VALUES (3, 3, 'Character3', 15);
INSERT INTO Store (DataID, UserID, Cname, Playtime)
VALUES (4, 4, 'Character4', 20);
INSERT INTO Store (DataID, UserID, Cname, Playtime)
VALUES (5, 5, 'Character5', 25);

INSERT INTO Characters_Stats (HP, charLevel) VALUES (1, 100);
INSERT INTO Characters_Stats (HP, charLevel) VALUES (2, 150);
INSERT INTO Characters_Stats (HP, charLevel) VALUES (3, 200);
INSERT INTO Characters_Stats (HP, charLevel) VALUES (4, 250);
INSERT INTO Characters_Stats (HP, charLevel) VALUES (5, 300);

INSERT INTO Characters_Info (charLevel, Money, Cname, Rname, MapID, currLoc)
VALUES (1, 100, 'character1', 'role1', 'M000000001', 'x000000001y000000001');
INSERT INTO Characters_Info (charLevel, Money, Cname, Rname, MapID, currLoc)
VALUES (2, 200, 'character2', 'role2', 'M000000002', 'x000000002y000000002');
INSERT INTO Characters_Info (charLevel, Money, Cname, Rname, MapID, currLoc)
VALUES (3, 300, 'character3', 'role3', 'M000000003', 'x000000003y000000003');
INSERT INTO Characters_Info (charLevel, Money, Cname, Rname, MapID, currLoc)
VALUES (4, 400, 'character4', 'role4', 'M000000004', 'x000000004y000000004');
INSERT INTO Characters_Info (charLevel, Money, Cname, Rname, MapID, currLoc)
VALUES (5, 5100, 'character5', 'role5', 'M000000005', 'x000000005y000000005');

INSERT INTO Weapons (WeaponID, wpDamage, Price, Rname)
VALUES ('W000000001', 10, 100, 'role1' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Rname)
VALUES ('W000000001', 10, 100, 'role1' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Rname)
VALUES ('W000000001', 10, 100, 'role1' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Rname)
VALUES ('W000000001', 10, 100, 'role1' );
INSERT INTO Weapons (WeaponID, wpDamage, Price, Rname)
VALUES ('W000000001', 10, 100, 'role1' );

INSERT INTO Roles (Rname) VALUES ('role1');
INSERT INTO Roles (Rname) VALUES ('role2');
INSERT INTO Roles (Rname) VALUES ('role3');
INSERT INTO Roles (Rname) VALUES ('role4');
INSERT INTO Roles (Rname) VALUES ('role5');

INSERT INTO Map(MapID, MapName) VALUES ('Map0000001', 'Map1');
INSERT INTO Map(MapID, MapName) VALUES ('Map0000002', 'Map2');
INSERT INTO Map(MapID, MapName) VALUES ('Map0000003', 'Map3');
INSERT INTO Map(MapID, MapName) VALUES ('Map0000004', 'Map4');
INSERT INTO Map(MapID, MapName) VALUES ('Map0000005', 'Map5');

INSERT INTO LockedArea(MapID, MapName, FoggyArea)
VALUES ('Map0000001', 'Map1', 'Foggy00001');
INSERT INTO LockedArea(MapID, MapName, FoggyArea)
VALUES ('Map0000002', 'Map2', 'Foggy00002');
INSERT INTO LockedArea(MapID, MapName, FoggyArea)
VALUES ('Map0000003', 'Map3', 'Foggy00003');
INSERT INTO LockedArea(MapID, MapName, FoggyArea)
VALUES ('Map0000004', 'Map4', 'Foggy00004');
INSERT INTO LockedArea(MapID, MapName, FoggyArea)
VALUES ('Map0000005', 'Map5', 'Foggy00005');

INSERT INTO UnlockArea(MapID, MapName, CheckPoint)
VALUES ('Map0000001', 'map1', 'check00001');
INSERT INTO UnlockArea(MapID, MapName, CheckPoint)
VALUES ('Map0000002', 'map2', 'check00002');
INSERT INTO UnlockArea(MapID, MapName, CheckPoint)
VALUES ('Map0000003', 'map3', 'check00003');
INSERT INTO UnlockArea(MapID, MapName, CheckPoint)
VALUES ('Map0000004', 'map4', 'check00004');
INSERT INTO UnlockArea(MapID, MapName, CheckPoint)
VALUES ('Map0000005', 'map5', 'check00005');

INSERT INTO Coordinate (X_Coord, Y_Coord, MapID)
VALUES ('X000000001', 'Y000000001', 'Map0000001');
INSERT INTO Coordinate (X_Coord, Y_Coord, MapID)
VALUES ('X000000002', 'Y000000002', 'Map0000002');
INSERT INTO Coordinate (X_Coord, Y_Coord, MapID)
VALUES ('X000000003', 'Y000000003', 'Map0000003');
INSERT INTO Coordinate (X_Coord, Y_Coord, MapID)
VALUES ('X000000004', 'Y000000004', 'Map0000004');
INSERT INTO Coordinate (X_Coord, Y_Coord, MapID)
VALUES ('X000000005', 'Y000000005', 'Map0000005');

INSERT INTO LearnSkills_Stats(Sname, SDamage, Requirement)
VALUES ('invisibility', 0, 'level 1');
INSERT INTO LearnSkills_Stats(Sname, SDamage, Requirement)
VALUES ('fireball', 100, 'level 2');
INSERT INTO LearnSkills_Stats(Sname, SDamage, Requirement)
VALUES ('iceball', 100, 'level 3');
INSERT INTO LearnSkills_Stats(Sname, SDamage, Requirement)
VALUES ('fly', 0, 'level 4');
INSERT INTO LearnSkills_Stats(Sname, SDamage, Requirement)
VALUES ('heal', -50, 'level 5');

INSERT INTO LearnSkills_Info(skillID, Sname, IsLearned, Rname)
VALUES ('SK00000001','invisibility', 'F', 'knight');
INSERT INTO LearnSkills_Info(skillID, Sname, IsLearned, Rname)
VALUES ('SK00000002','fireball', 'T', 'apprentice');
INSERT INTO LearnSkills_Info(skillID, Sname, IsLearned, Rname)
VALUES ('SK00000003','iceball', 'F', 'wizard');
INSERT INTO LearnSkills_Info(skillID, Sname, IsLearned, Rname)
VALUES ('SK00000004','fly', 'T', 'assassin');
INSERT INTO LearnSkills_Info(skillID, Sname, IsLearned, Rname)
VALUES ('SK00000005','heal', 'F', 'healer');

INSERT INTO BossInfo (Bname, Blevel, BossDMG, BossHP)
VALUES ('BOSS1', 1, 100, 100);
INSERT INTO BossInfo (Bname, Blevel, BossDMG, BossHP)
VALUES ('BOSS2', 2, 200, 200);
INSERT INTO BossInfo (Bname, Blevel, BossDMG, BossHP)
VALUES ('BOSS3', 3, 300, 300);
INSERT INTO BossInfo (Bname, Blevel, BossDMG, BossHP)
VALUES ('BOSS4', 4, 400, 400);
INSERT INTO BossInfo (Bname, Blevel, BossDMG, BossHP)
VALUES ('BOSS5', 5, 500, 500);

INSERT INTO DungeonStats (dungeonName, clearStatus)
VALUES ('Dungeon1', 'T');
INSERT INTO DungeonStats (dungeonName, clearStatus)
VALUES ('Dungeon2', 'T');
INSERT INTO DungeonStats (dungeonName, clearStatus)
VALUES ('Dungeon3', 'F');
INSERT INTO DungeonStats (dungeonName, clearStatus)
VALUES ('Dungeon4', 'F');
INSERT INTO DungeonStats (dungeonName, clearStatus)
VALUES ('Dungeon5', 'T');

INSERT INTO DungeonInfo (dungeonID, dungeonName, Item, Bname, BossID, MapID)
VALUES ('D000000001', 'Dungeon1', 'Item1', 'BOSS1', 'B000000001', 'MAP0000001');
INSERT INTO DungeonInfo (dungeonID, dungeonName, Item, Bname, BossID, MapID)
VALUES ('D000000002', 'Dungeon2', 'Item2', 'BOSS2', 'B000000002', 'MAP0000002');
INSERT INTO DungeonInfo (dungeonID, dungeonName, Item, Bname, BossID, MapID)
VALUES ('D000000003', 'Dungeon3', 'Item3', 'BOSS3', 'B000000003', 'MAP0000003');
INSERT INTO DungeonInfo (dungeonID, dungeonName, Item, Bname, BossID, MapID)
VALUES ('D000000004', 'Dungeon4', 'Item4', 'BOSS4', 'B000000004', 'MAP0000004');
INSERT INTO DungeonInfo (dungeonID, dungeonName, Item, Bname, BossID, MapID)
VALUES ('D000000005', 'Dungeon5', 'Item5', 'BOSS5', 'B000000005', 'MAP0000005');