create database gl;
drop database gl;
use gl;
CREATE TABLE `user` (
  `userid` varchar(100) PRIMARY KEY,
  `userpw` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `gender` char(1) NOT NULL,
  `birth` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `userphone` varchar(100) NOT NULL,
  `nickname` varchar(100) UNIQUE,
  `zipcode` varchar(300) NOT NULL,
  `addr` varchar(1000),
  `addrdetail` varchar(1000) NOT NULL,
  `addretc` varchar(300),
  `regdate` datetime default now(),
  `bonus` int DEFAULT 0,
  `type` char(1) default 'O',
  `usercategory` varchar(100) NOT NULL
);
drop table user;
select * from user;
CREATE TABLE `seller` (
  `sellerid` varchar(100) PRIMARY KEY,
  `sellerpw` varchar(100) NOT NULL,
  `sellername` varchar(100) NOT NULL,
  `sellercategory` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `sellerphone` varchar(100) NOT NULL
);
select * from seller;
drop table seller;
CREATE TABLE `org` (
  `orgid` varchar(100) PRIMARY KEY,
  `orgpw` varchar(100) NOT NULL,
  `orgname` varchar(100) NOT NULL,
  `orgcategory` varchar(100) NOT NULL,
  `logo` varchar(300) NOT NULL,
  `orgphone` varchar(100) NOT NULL,
  `ceoname` varchar(100) NOT NULL,
  `orgzipcode` varchar(300) NOT NULL,
  `orgaddr` varchar(1000),
  `orgaddrdetail` varchar(1000) NOT NULL,
  `orgaddretc` varchar(300)
);

select orgname from org where orgid='orgid';
select orgname from org where orgcategory="";
select * from org;

delete from org where orgid='givuluv';
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
  `regdate` datetime DEFAULT now(),
  `sellerid` varchar(100) NOT NULL,
  `isagree` char(1) DEFAULT '-',
   constraint fk_s_register_sellerid foreign key (sellerid) references seller(sellerid)
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
  `sellerid` varchar(100) NOT NULL,
  constraint fk_store_sellerid foreign key (sellerid) references seller(sellerid)
);
insert into store values (0,'상점이름',now(),'010-1234-1234','zipcode','addr','addrdetail','etc',now(),'대표자','seller');
insert into store values (0,'상점이름1',now(),'010-1234-1234','zipcode','addr','addrdetail','etc',(now(),'%Y-%m-%d'),'김사과','s_apple');
insert into store values (0,'상점이름2',now(),'010-1234-1234','zipcode','addr','addrdetail','etc',now(),'반하나','s_banana');
delete from store where s_num=2;
select * from store;
drop table store;
CREATE TABLE `o_register` (
  `regdate` datetime DEFAULT now(),
  `orgid` varchar(100) NOT NULL,
  `isagree` char(1) DEFAULT '-',
  constraint fk_o_register_orgid foreign key (orgid) references org(orgid)
);

CREATE TABLE `d_board` (
  `d_boardnum` int PRIMARY KEY auto_increment,
  `d_title` varchar(100) NOT NULL,
  `d_content` text NOT NULL,
  `target_amount` int NOT NULL,
  `save_money` int DEFAULT 0,
  `d_regdate` datetime DEFAULT now(),
  `d_enddate` date NOT NULL,
  `orgid` varchar(100),
  constraint fk_d_board_orgid foreign key (orgid) references org(orgid)
);
select d_enddate from d_board where d_boardnum=1;
select * from d_board;
update d_board set d_title="수현이의 행복한 짜장면" where d_boardnum=6;
delete from d_board where d_boardnum=4;
drop table d_board;
insert into d_board(d_boardnum,d_title,d_content,target_amount,save_money,d_enddate,orgid) values(2,"흰목물떼새의 사랑을 지켜주세요!","기부해주세요",9000000,0,"2024-09-01","orggg");
		select d.*, o.orgname
		from d_board d join org o on d.orgid = o.orgid
		where o.orgcategory = "장애인"
		order by d.d_regdate desc;
SELECT d.*, o.orgname
FROM d_board d
JOIN org o ON d.orgid = o.orgid
WHERE o.orgcategory = "장애인"
ORDER BY d.d_regdate DESC;
select *
		from d_board
		where orgid in (select orgid from org where orgcategory="장애인")
		order by d_regdate desc;
 CREATE TABLE `s_board`(
   s_boardnum int primary key auto_increment,
    s_title varchar(100) NOT NULL,
    s_content text NOT NULL,
    s_regdate datetime default now(),
    s_num int,
    constraint fk_s_board_s_num foreign key (s_num) references store(s_num)
);       
select * from s_board;
CREATE TABLE `f_board` (
  `f_boardnum` int PRIMARY KEY auto_increment,
  `f_title` varchar(100) NOT NULL,
  `f_content` text NOT NULL,
  `target_amount` int NOT NULL,
  `save_money` int DEFAULT 0,
  `f_regdate` datetime DEFAULT now(),
  `f_enddate` date NOT NULL,
  `orgid` varchar(100),
  constraint fk_f_board_orgid foreign key (orgid) references org(orgid)
);
CREATE TABLE `comment` (
  `commentnum` int PRIMARY KEY auto_increment,
  `commentdetail` text,
  `commentregdate` datetime DEFAULT now(),
  `commentupdatedate` datetime,
  `c_boardnum` int,
  `connectid` varchar(300), -- 작성한 사람(userid or orgid) --  
  `type` char(1), -- 'U' / 'O'
  constraint fk_comment_c_boardnum foreign key (c_boardnum) references c_board(c_boardnum)
);

CREATE TABLE `file` (
  `systemname` varchar(2000),
  `connectionid` varchar(100), /* 게시판 번호 */
  `type` char(1), 
  `boardthumbnail` char(1) /* Y / N */
);
select * from file;
delete from file where connectionid=4;
select systemname from file where connectionid='12';
drop table file;
CREATE TABLE `product` (
  `productnum` int PRIMARY KEY auto_increment,
  `productname` varchar(300),
  `p_amount` int NOT NULL,
  `cost` int NOT NULL,
  `connectid` varchar(100),
  `p_type` char(1)
);
select * from product;
INSERT INTO product (productname, p_amount, cost, connectid, p_type) VALUES ('할미할배1', 500, 5000, '3', 'm');

SELECT p.*
FROM product p
JOIN store s ON p.connectid = s.s_num
JOIN seller se ON s.sellerid = se.sellerid
WHERE se.sellercategory = '음료／건강';
-- CREATE TABLE `category` (
--   `categorynum` int PRIMARY KEY auto_increment,
--   `categoryname` varchar(300) NOT NULL
-- );


-- CREATE TABLE `connect_category` (
--   `categorynum` int auto_increment,
--   `connectid` varchar(100), /* 게시판 번호 */
--   `type` char(1), /* 기부(d), 펀딩(f), 가게(s)  */
--   constraint fk_connect_category_categorynum foreign key (categorynum) references category(categorynum)
-- );

CREATE TABLE `f_detail` (
  `f_detailnum` int PRIMARY KEY auto_increment,
  `productnum` int,
  `userid` varchar(100),
  `f_regdate` datetime DEFAULT now(),
  `type` char(1),
   constraint fk_f_detail_productnum foreign key (productnum) references product(productnum),
   constraint fk_f_detail_userid foreign key (userid) references user(userid)
);

CREATE TABLE `d_detail` (
  `d_detailnum` int PRIMARY KEY auto_increment,
  `d_boardnum` int,
  `userid` varchar(100),
  `d_regdate` datetime DEFAULT now(),
  `type` char(1),
   constraint fk_d_detail_d_boardnum foreign key (d_boardnum) references d_board(d_boardnum),
   constraint fk_d_detail_userid foreign key (userid) references user(userid)
);

CREATE TABLE `delivery` (
  `deliverynum` int PRIMARY KEY auto_increment,
  `f_detailnum` int,
  `start_delivery` date,
  `end_delvery` date,
  `shipmentnum` varchar(300),
  `state` char(1),
  constraint fk_delivery_f_detailnum foreign key (f_detailnum) references f_detail(f_detailnum)
);

CREATE TABLE `review` (
  `reviewnum` int PRIMARY KEY auto_increment,
  `reviewdetail` text,
  `userid` varchar(100),
  `reviewdate` datetime DEFAULT now(),
  `star` int,
   `connectid` varchar(100), -- 게시판번호
  `type` char(1), -- 'd'기부 'f'펀딩 'm'가게
  constraint fk_review_userid foreign key (userid) references user(userid)
);
select * from review;
drop table review;
CREATE TABLE `c_board` (
  `c_boardnum` int PRIMARY KEY auto_increment,
  `c_title` varchar(100) NOT NULL,
  `c_content` text NOT NULL,
  `c_regdate` datetime DEFAULT now(),
  `connectid` varchar(100),
  `type` char(1)
);

CREATE TABLE `like` (
  `likenum` int PRIMARY KEY auto_increment,
  `connectid` int, -- campaignnum / commentnum
  `type` char(1), -- 'C' / 'R'
  `userid` varchar(100),
  constraint fk_like_userid foreign key (userid) references user(userid)
);

CREATE TABLE `qna` (
  `qnanum` int PRIMARY KEY auto_increment,
  `question` varchar(300),
  `answer` varchar(300)
);

CREATE TABLE `follow` (
  `follownum` int PRIMARY KEY auto_increment,
  `orgid` varchar(100),
  `userid` varchar(100),
  constraint fk_follow_orgid foreign key (orgid) references org(orgid),
  constraint fk_follow_userid foreign key (userid) references user(userid)
);

create table d_payment(
	paymentnum int PRIMARY KEY auto_increment,
    userid varchar(100),
    orgid varchar(100),
    d_boardnum int,
    d_cost int,
    paydate datetime DEFAULT now(),
    type char(1), /* 일반결제인지 간편결제인지 */  
    constraint fk_d_payment_userid foreign key (userid) references user(userid),
    constraint fk_d_payment_orgid foreign key (orgid) references org(orgid),
    constraint fk_d_payment_d_boardnum foreign key (d_boardnum) references d_board(d_boardnum)
);
SELECT SUM(d_cost) AS total_cost
FROM d_payment
WHERE DATE(paydate) = CURDATE();
select sum(d_cost) from d_payment;
select count(distinct userid) from d_payment;
UPDATE d_board b
JOIN (SELECT d_boardnum, SUM(d_cost) as total_cost 
FROM d_payment
WHERE d_boardnum = 1
GROUP BY d_boardnum) p 
 ON b.d_boardnum = p.d_boardnum 
SET b.save_money = p.total_cost 
WHERE b.d_boardnum = 1;

select save_money from d_board where d_boardnum=2;

SELECT COUNT(DISTINCT userid) AS user_count
FROM d_payment
WHERE DATE(paydate) = CURDATE();
select * from d_payment;
select sum(d_cost) from d_payment where date(paydate) = curdate();
select * from d_payment where userid='apple' and type='p' order by paymentnum desc limit 1;
select count(distinct userid) from d_payment where d_boardnum=2 and type='r';

/* 펀딩 결제내역 */
create table f_payment(
	paymentnum int PRIMARY KEY auto_increment,
    userid varchar(100),
    orgid varchar(100),
    f_boardnum int,
    f_cost int,
    paydate datetime DEFAULT now(),
    constraint fk_f_payment_userid foreign key (userid) references user(userid),
    constraint fk_f_payment_orgid foreign key (orgid) references org(orgid),
    constraint fk_f_payment_f_boardnum foreign key (f_boardnum) references f_board(f_boardnum)
);