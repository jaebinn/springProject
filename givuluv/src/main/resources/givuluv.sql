create database gl;
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
select * from org;
select * from file;
          select * from c_board
          where ((type = 'O'
           and connectid in
           
               (select orgid from org where orgcategory='어르신'))
   
          or type = 'M')

          and c_boardnum <= 5
   
          order by c_boardnum desc
          limit 5;
delete from file where connectionid='animal' and type='O';
SET SQL_SAFE_UPDATES = 0;

DELETE FROM file WHERE connectionid='animal' AND type='O';

SET SQL_SAFE_UPDATES = 1;
select * from c_board;
select * from seller;
select * from user;
CREATE TABLE `seller` (
  `sellerid` varchar(100) PRIMARY KEY,
  `sellerpw` varchar(100) NOT NULL,
  `sellername` varchar(100) NOT NULL,
  `sellercategory` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `sellerphone` varchar(100) NOT NULL
);

CREATE TABLE `org` (
  `orgid` varchar(100) PRIMARY KEY,
  `orgpw` varchar(100) NOT NULL,
  `orgname` varchar(100) NOT NULL unique,
  `orgcategory` varchar(100) NOT NULL,
  `logo` varchar(300) NOT NULL,
  `orgphone` varchar(100) NOT NULL,
  `ceoname` varchar(100) NOT NULL,
  `orgzipcode` varchar(300) NOT NULL,
  `orgaddr` varchar(1000),
  `orgaddrdetail` varchar(1000) NOT NULL,
  `orgaddretc` varchar(300),
  `orgunqnum` varchar(20) NOT NULL /* 추가함... */
);

CREATE TABLE `manager` (
  `managerid` varchar(100) PRIMARY KEY,
  `managerpw` varchar(100) NOT NULL,
  `managername` varchar(100) NOT NULL
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

 CREATE TABLE `s_board`(
   s_boardnum int primary key auto_increment,
    s_title varchar(100) NOT NULL,
    s_content text NOT NULL,
    s_regdate datetime default now(),
    s_num int,
    constraint fk_s_board_s_num foreign key (s_num) references store(s_num)
);       

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
select DISTINCT c_title, orgid from c_board join `like` on `like`.userid = 'apple';
CREATE TABLE `file` (
  `systemname` varchar(2000),
  `connectionid` varchar(100), /* 게시판 번호 */
  `type` char(1), 
  `boardthumbnail` char(1) /* Y / N */
);

CREATE TABLE `product` (
  `productnum` int PRIMARY KEY auto_increment,
  `productname` varchar(300),
  `p_amount` int NOT NULL,
  `cost` int NOT NULL,
  `connectid` varchar(100), /* 게시판번호 */
  `p_type` char(1) /*F: 펀딩물품, M: 가게*/
);

-- CREATE TABLE `delivery` (
--   `deliverynum` int PRIMARY KEY auto_increment,
--   `f_detailnum` int,
--   `start_delivery` date,
--   `end_delvery` date,
--   `shipmentnum` varchar(300),
--   `state` char(1),
--   constraint fk_delivery_f_detailnum foreign key (f_detailnum) references f_detail(f_detailnum)
-- );

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

CREATE TABLE `c_board` (
  `c_boardnum` int PRIMARY KEY auto_increment,
  `c_content` text NOT NULL,
  `c_regdate` datetime DEFAULT now(),
  `connectid` varchar(100),
  `type` char(1)
);
select * from c_board;
drop table c_board;
select * from s_board;
select * from f_board;
select * from file;
select b.*, f.* from f_board b join f_payment f on f.f_boardnum=b.f_boardnum where f.userid='apple' order by f.paymentnum desc LIMIT 1;
select b.d_title from d_board b inner join d_payment p on p.d_boardnum = b.d_boardnum where p.userid = 'apple' order by p.paymentnum desc LIMIT 1;
select * from s_board s join `like` l on s.s_boardnum = l.connectid where l.userid="apple" and l.type='s';
select f.systemname from file f join s_board s on f.connectionid = s.s_boardnum where f.connectionid=1 and f.type='M' and f.boardthumbnail='Y';
select * from d_payment;
select * from d_board;
CREATE TABLE `like` (
  `likenum` int PRIMARY KEY auto_increment,
  `connectid` int, -- campaignnum / commentnum 
  `type` char(1), -- 'C' / 'R'
  `userid` varchar(100),
  constraint fk_like_userid foreign key (userid) references user(userid)
);
select * from `like`;
CREATE TABLE `qna` (
  `qnanum` int PRIMARY KEY auto_increment,
  `question` varchar(300),
  `qnaregdate` datetime DEFAULT now(),
  `answer` varchar(300),
  `productnum` int,
  `userid` varchar(100)
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

/* 펀딩 결제내역 */
create table f_payment(
	paymentnum int PRIMARY KEY auto_increment,
    userid varchar(100),
    orgid varchar(100),
    f_boardnum int,
    f_cost int,
    productnum int,
    amount int,
    paydate datetime DEFAULT now(),
    reqetc varchar(300),
    constraint fk_f_payment_userid foreign key (userid) references user(userid),
    constraint fk_f_payment_orgid foreign key (orgid) references org(orgid),
    constraint fk_f_payment_f_boardnum foreign key (f_boardnum) references f_board(f_boardnum),
    constraint fk_f_payment_productnum foreign key (productnum) references product(productnum)
);

create table s_payment(
   paymentnum int PRIMARY KEY auto_increment,
    userid varchar(100),
    sellerid varchar(100),
    s_boardnum int,
    s_cost int,
    productnum int,
    amount int,
    paydate datetime DEFAULT now(),
    reqetc varchar(300),
    constraint fk_s_payment_userid foreign key (userid) references user(userid),
    constraint fk_s_payment_sellerid foreign key (sellerid) references seller(sellerid),
   constraint fk_s_payment_f_boardnum foreign key (s_boardnum) references s_board(s_boardnum),
    constraint fk_s_payment_productnum foreign key (productnum) references product(productnum)
);

create table faq(
	faqnum int primary key auto_increment,
    question varchar(1000) not null,
    answer varchar(1000) not null
);

insert into faq values(1, "기부러브 소개", "1. 기부
추천된 모금함의 상세페이지(내용)까지 조회 및 기부할 수 있는 서비스
2. 펀딩
공익을 추구하는 단체나 개인의 사업을 후원할 수 있는 서비스로 단체 및 개인 누구나 펀딩이 가능함
3. 가게
종료되어 아쉬웠던 펀딩을 가게에서 찾아 구매할 수 있는 서비스
4. 게시글
판매자와 사회단체의 글을 조회 및 소통할 수 있는 서비스");
insert into faq values(2,"기부러브 기부방법","1. 모금함 선택하기
모금함 기부 페이지 진행중 모금함을 선택하세요.
2. 기부하기
선택한 모금함 페이지로 이동한 후 모금함 기부하기 버튼을 클릭하여 기부할 수 있습니다.
이때, 원하는 금액만큼 1회를 기부할지, 매월 일정 금액을 정기적으로 기부할지 정할 수 있습니다.
보유한 보너스로 기부를 하려면, 기부 유형 중 보유금액 기부하기를 선택하면 됩니다.");
insert into faq values(3,"기부 보너스 소개","기부러브에서 기부 보너스는 펀딩, 가게에서 구매한 물품의 가격 10%를 지급하고 기부 보너스를 받을 수 있는 포인트입니다. 
따라서 실제의 기부금과 동일시 취급하며 기부 보너스로도 기부가 가능합니다.");

CREATE TABLE `o_approve` (
    `o_approvenum` int primary key auto_increment,
    `institute_name` VARCHAR(30) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(15) NOT NULL,
    `information` TEXT,
    `regdate` datetime DEFAULT now(),
   `orgid` varchar(100) NOT NULL,
   `isagree` char(1) DEFAULT '-',
   constraint fk_o_approve_orgid foreign key (orgid) references org(orgid)
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
  `information` TEXT,
  `sellerid` varchar(100) NOT NULL,
  `isagree` char(1) DEFAULT '-',
   constraint fk_s_register_sellerid foreign key (sellerid) references seller(sellerid)
);

create table `s_info`(
   `s_infonum` int PRIMARY KEY auto_increment,
   `s_title` varchar(100) not null,
    `s_infocontent` text not null,
    `s_summary` varchar(100) not null,
    `s_num` int,
    constraint fk_s_info_s_num foreign key (s_num) references store(s_num)
);

-- create table `o_info`(
--    `o_infonum` int PRIMARY KEY auto_increment,
--     `o_infocontent` text not null,
--     `orgid` varchar(100),
--     constraint fk_o_info_orgid foreign key (orgid) references org(orgid)
-- );