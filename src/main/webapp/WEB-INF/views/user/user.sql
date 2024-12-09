show tables;

create table user (
  idx  int  not null auto_increment, /* 고유번호 */
  mid  varchar(30) not null,		/* 아이디 */
  pwd  varchar(30) not null,		/* 비밀번호 */
  name varchar(30) not null,		/* 성명 */
  age  int default 20,					/* 나이 */
  gender varchar(2) default '여자', /* 성별 */
  address varchar(10),					/* 주소 */
  primary key (idx),
  unique key (mid)
);

desc user;

insert into user (_,_,_) values (_,_,_);
insert into user values (default, 'admin', '1234', '관리자', default, default, '서울');

select * from user;

select * from user order by idx desc;