show tables;

create table crime (
  idx  int not null auto_increment,
  year int not null,			/* 범죄 발생년도 */
  police varchar(20) not null, /* 경찰서명 */
  murder  int,							/* 살인 건수 */
  robbery int,							/* 강도 건수 */
  theft   int,							/* 절도 건수 */
  violence int,							/* 폭력 건수 */  
  primary key (idx)
);

desc crime;

select * from crime order by year, police;

select year,sum(murder) as totMurder,sum(robbery) as totRobbery,sum(theft) as totTheft,sum(violence) as totViolence,
		avg(murder) as avgMurder,avg(robbery) as avgRobbery,avg(theft) as avgTheft,avg(violence) as avgViolence
		from crime where year=2023 and police like '서울%';