show tables;

create table visit (
  visitDate datetime not null default now(),
  visitCount int not null default 1
);

desc visit;
drop table visit;
delete from visit;

insert into visit values ('2024-12-27', 15);
insert into visit values ('2024-12-26', 4);
insert into visit values ('2024-12-25', 5);
insert into visit values ('2024-12-23', 12);
insert into visit values ('2024-12-22', 10);
insert into visit values ('2024-12-20', 5);
insert into visit values ('2024-12-17', 8);
insert into visit values ('2024-12-15', 18);
insert into visit values ('2024-12-13', 20);

select * from visit order by visitDate desc;
select * from visit order by visitDate desc limit 7;
select substring(visitDate, 1, 10) as visitDate, visitCount from visit order by visitDate desc limit 7;
