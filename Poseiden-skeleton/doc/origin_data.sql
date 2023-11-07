DROP TABLE IF EXISTS BidList, Trade, CurvePoint, Rating, RuleName, Users;

CREATE TABLE BidList (
  Id tinyint NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_Quantity DOUBLE,
  ask_Quantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_List_Date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_Name VARCHAR(125),
  creation_Date TIMESTAMP ,
  revision_Name VARCHAR(125),
  revision_Date TIMESTAMP ,
  deal_Name VARCHAR(125),
  deal_Type VARCHAR(125),
  source_List_Id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE Trade (
  Id tinyint NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_Quantity DOUBLE,
  sell_Quantity DOUBLE,
  buy_Price DOUBLE ,
  sell_Price DOUBLE,
  trade_Date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_Name VARCHAR(125),
  creation_Date TIMESTAMP ,
  revision_Name VARCHAR(125),
  revision_Date TIMESTAMP ,
  deal_Name VARCHAR(125),
  deal_Type VARCHAR(125),
  source_List_Id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE CurvePoint (
  Id tinyint NOT NULL AUTO_INCREMENT,
  Curve_Id tinyint,
  as_Of_Date TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creation_Date TIMESTAMP ,

  PRIMARY KEY (Id)
);

CREATE TABLE Rating (
  Id tinyint NOT NULL AUTO_INCREMENT,
  moodys_Rating VARCHAR(125),
  sand_P_Rating VARCHAR(125),
  fitch_Rating VARCHAR(125),
  order_Number tinyint,

  PRIMARY KEY (Id)
);

CREATE TABLE RuleName (
  Id tinyint NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_Str VARCHAR(125),
  sql_Part VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE Users (
  Id tinyint NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),

  PRIMARY KEY (Id)
);

insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2a$10$0.aGcFlatjAfIhmmHkG/i.wkew6w8HBE8WsOTla1ivKDtkNddCiTK", "ADMIN");
insert into Users(fullname, username, password, role) values("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");