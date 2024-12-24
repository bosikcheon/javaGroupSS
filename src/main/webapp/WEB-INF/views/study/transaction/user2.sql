show tables;

desc user;

create table user2 (
  mid  varchar(4) not null,
  job  varchar(10)
);

drop table user2;
delete from user2;

desc user2;

insert into user2 values ('otom', '학생');

select * from user2;

/* ------------------- */

desc user;
insert into user (mid, pwd, name) values ('otom1234', '1234', '오톰');
delete from user where mid = 'otom1234';
select * from user order by idx desc;

/* ------------------- */
insert into user (mid, pwd, name) values ('ptom1234', '1234', '피톰');
insert into user2 values ('ptom1234', '학생');

select * from user order by idx desc;
select * from user2;
