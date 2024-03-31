CREATE TABLE userOwnsAccount (
    Uid integer PRIMARY KEY,
    Name varchar2(50),
    Birthday Date,
    Email varchar2(50),
    Password integer
);

INSERT INTO userOwnsAccount VALUES (12345678, "Devin", Date.valueOf("2010-10-10"), "Devin@gmail.com", 8888);