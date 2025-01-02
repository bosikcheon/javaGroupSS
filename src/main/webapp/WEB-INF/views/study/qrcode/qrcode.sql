show tables;

create table qrcode (
	idx   int not null auto_increment primary key,
	mid		varchar(30) not null,
	name  varchar(30) not null,
	email varchar(50) not null,
	subject varchar(50) not null,
	moveUrl varchar(60) not null,
	movieCinema varchar(20) not null,
	movieName varchar(20) not null,
	movieDate varchar(50) not null,
	movieTime varchar(50) not null,
	movieAdult int not null,
	movieChild int not null,
	qrcodeDate varchar(50) not null,
	qrcodeName varchar(100) not null,
	foreign key(mid) references member(mid)
);

desc qrcode;
drop table qrcode;