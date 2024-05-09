CREATE TABLE `user` (
  `userid` varchar(100) PRIMARY KEY,
  `userpw` varchar(100) NOT NULL,
  `username` varchar(255) NOT NULL,
  `gender` char(1) NOT NULL,
  `birth` date NOT NULL,
  `email` varchar(100) NOT NULL,
  `userphone` varchar(100) NOT NULL,
  `nickname` varchar(100) UNIQUE,
  `zipcode` varchar(300) NOT NULL,
  `addr` varchar(1000),
  `addrdetail` varchar(1000) NOT NULL,
  `addretc` varchar(300),
  `regdate` date,
  `bonus` int DEFAULT 0,
  `type` char(1) NOT NULL
  
);

CREATE TABLE `seller` (
  `sellerid` varchar(100) PRIMARY KEY,
  `sellerpw` varchar(100) NOT NULL,
  `sellername` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `sellerphone` varchar(100) NOT NULL
);

CREATE TABLE `org` (
  `orgid` varchar(100) PRIMARY KEY,
  `orgpw` varchar(100) NOT NULL,
  `orgname` varchar(100) NOT NULL,
  `logo` varchar(300) NOT NULL,
  `orgphone` varchar(100) NOT NULL,
  `ceoname` varchar(100) NOT NULL,
  `orgzipcode` varchar(300) NOT NULL,
  `orgaddr` varchar(1000),
  `orgaddrdetail` varchar(1000) NOT NULL,
  `orgaddretc` varchar(300)
);

CREATE TABLE `manager` (
  `managerid` varchar(100) PRIMARY KEY,
  `managerpw` varchar(100) NOT NULL,
  `managername` varchar(100) NOT NULL
);

CREATE TABLE `s_register` (
  `s_name` varchar(100) PRIMARY KEY,
  `s_regnum` varchar(100) NOT NULL,
  `s_phone` varchar(100) NOT NULL,
  `s_zipcode` varchar(300) NOT NULL,
  `s_addr` varchar(1000),
  `s_addrdetail` varchar(1000) NOT NULL,
  `s_addretc` varchar(300),
  `s_opendate` date,
  `s_leader` varchar(100) NOT NULL,
  `regdate` date DEFAULT 'now()',
  `sellerid` varchar(100) NOT NULL,
  `isagree` char(1) DEFAULT '-'
);

CREATE TABLE `store` (
  `s_num` int PRIMARY KEY auto_increment, 
  `s_name` varchar(100),
  `s_regnum` varchar(100) NOT NULL, 
  `s_phone` varchar(100) NOT NULL,
  `s_zipcode` varchar(300) NOT NULL,
  `s_addr` varchar(1000),
  `s_addrdetail` varchar(1000) NOT NULL,
  `s_addretc` varchar(300),
  `s_opendate` date,
  `s_leader` varchar(100) NOT NULL,
  `sellerid` varchar(100) NOT NULL
);

CREATE TABLE `o_register` (
  `regdate` date DEFAULT 'now()',
  `orgid` varchar(100) NOT NULL,
  `isagree` char(1) DEFAULT '-'
);

CREATE TABLE `d_board` (
  `d_boardnum` int PRIMARY KEY auto_increment,
  `d_title` varchar(100) NOT NULL,
  `d_content` text NOT NULL,
  `target_amount` int NOT NULL,
  `save_money` int DEFAULT 0,
  `d_regdate` date DEFAULT 'now()',
  `d_enddate` date NOT NULL,
  `orgid` varchar(100)
);

CREATE TABLE `f_board` (
  `f_boardnum` int PRIMARY KEY auto_increment,
  `f_title` varchar(100) NOT NULL,
  `f_content` text NOT NULL,
  `target_amount` int NOT NULL,
  `save_money` int DEFAULT 0,
  `f_regdate` date DEFAULT 'now()',
  `f_enddate` date NOT NULL,
  `orgid` varchar(100)
);

CREATE TABLE `file` (
  `systemname` varchar(2000),
  `connectionid` varchar(100),
  `type` char(1)
);

CREATE TABLE `product` (
  `productnum` int PRIMARY KEY auto_increment,
  `productname` varchar(300),
  `p_amount` int NOT NULL,
  `cost` int NOT NULL,
  `connectid` varchar(100),
  `p_type` char(1)
);

CREATE TABLE `category` (
  `categorynum` int PRIMARY KEY auto_increment,
  `categoryname` varchar(300) NOT NULL
);

CREATE TABLE `connect_category` (
  `categorynum` int auto_increment,
  `connectid` varchar(100),
  `type` char(1)
);

CREATE TABLE `f_detail` (
  `f_detailnum` int PRIMARY KEY auto_increment,
  `productnum` int,
  `userid` varchar(100),
  `f_regdate` date DEFAULT 'now()',
  `type` char(1)
);

CREATE TABLE `d_detail` (
  `d_detailnum` int PRIMARY KEY auto_increment,
  `d_boardid` int,
  `userid` varchar(100),
  `d_regdate` date DEFAULT 'now()',
  `type` char(1)
);

CREATE TABLE `delivery` (
  `deliverynum` int PRIMARY KEY auto_increment,
  `f_detailnum` int,
  `start_delivery` date,
  `end_delvery` date,
  `shipmentnum` varchar(300),
  `state` char(1)
);

CREATE TABLE `reivew` (
  `reviewnum` int PRIMARY KEY auto_increment,
  `reviewdetail` text,
  `userid` varchar(100),
  `reviewdate` date DEFAULT 'now()',
  `type` char(1)
);

CREATE TABLE `c_board` (
  `c_boardnum` int PRIMARY KEY auto_increment,
  `c_title` varchar(100) NOT NULL,
  `c_content` text NOT NULL,
  `c_regdate` date DEFAULT 'now()',
  `connectid` varchar(100),
  `type` char(1)
);

CREATE TABLE `like` (
  `likenum` int PRIMARY KEY auto_increment,
  `c_boardnum` int,
  `userid` varchar(100)
);

CREATE TABLE `qna` (
  `qnanum` int PRIMARY KEY auto_increment,
  `question` varchar(300),
  `answer` varchar(300)
);

CREATE TABLE `follow` (
  `follownum` int PRIMARY KEY auto_increment,
  `orgid` varchar(100),
  `userid` varchar(100)
);

ALTER TABLE `s_register` ADD FOREIGN KEY (`sellerid`) REFERENCES `seller` (`sellerid`);

ALTER TABLE `store` ADD FOREIGN KEY (`sellerid`) REFERENCES `seller` (`sellerid`);

ALTER TABLE `org` ADD FOREIGN KEY (`orgid`) REFERENCES `o_register` (`orgid`);

ALTER TABLE `org` ADD FOREIGN KEY (`orgid`) REFERENCES `d_board` (`orgid`);

ALTER TABLE `org` ADD FOREIGN KEY (`orgid`) REFERENCES `f_board` (`orgid`);

ALTER TABLE `category` ADD FOREIGN KEY (`categorynum`) REFERENCES `connect_category` (`categorynum`);

ALTER TABLE `product` ADD FOREIGN KEY (`productnum`) REFERENCES `f_detail` (`productnum`);

ALTER TABLE `user` ADD FOREIGN KEY (`userid`) REFERENCES `f_detail` (`userid`);

ALTER TABLE `d_board` ADD FOREIGN KEY (`d_boardid`) REFERENCES `d_detail` (`d_boardid`);

ALTER TABLE `user` ADD FOREIGN KEY (`userid`) REFERENCES `d_detail` (`userid`);

ALTER TABLE `f_detail` ADD FOREIGN KEY (`f_detailnum`) REFERENCES `delivery` (`f_detailnum`);

ALTER TABLE `user` ADD FOREIGN KEY (`userid`) REFERENCES `reivew` (`userid`);

ALTER TABLE `c_board` ADD FOREIGN KEY (`c_boardnum`) REFERENCES `like` (`c_boardnum`);

ALTER TABLE `user` ADD FOREIGN KEY (`userid`) REFERENCES `like` (`userid`);

ALTER TABLE `org` ADD FOREIGN KEY (`orgid`) REFERENCES `follow` (`orgid`);

ALTER TABLE `user` ADD FOREIGN KEY (`userid`) REFERENCES `follow` (`userid`);
