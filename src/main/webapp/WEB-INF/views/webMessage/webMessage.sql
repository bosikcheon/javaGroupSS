show tables;

create table webMessage (
	idx		int not null auto_increment,	/* 메세지 고유번호 */
	title	varchar(100) not null,				/* 메세지 제목 */
	content text not null,							/* 메세지 내용 */
	sendId	varchar(30) not null,				/* 보내는 사람 아이디 */
	sendSw	char(1)			not null default 's',	/* 보낸메세지(s), 휴지통(g), 휴지통삭제(x) 표시 */
	sendDate datetime		default now(),	/* 메세지 보낸날짜 */
	receiveId	varchar(30) not null,			/* 받는 사람 아이디 */
	receiveSw	char(1)			not null default 'n',	/* 받은메세지(n), 읽은메세지(r), 휴지통(g), 휴지통삭제(x) 표시 */
	receiveDate datetime	default now(),	/* 메세지 받은날짜/읽은날짜 */
	primary key(idx),
	foreign key(sendId) references member(mid),
	foreign key(receiveId) references member(mid)
);
desc webMessage;

insert into webMessage values (default,'안녕! 말숙아~~','이번주 주말에 뭐하니?','hkd1234',default,default,'kms1234','r',default);
insert into webMessage values (default,'안녕하세요 관리자님','메세지가 지연되는것 같습니다.','hkd1234',default,default,'admin','r',default);
insert into webMessage values (default,'안녕! 길동아~~','이번주는 연말이라서 바빠~~','kms1234',default,default,'hkd1234','r',default);
insert into webMessage values (default,'말숙아~ 메리크리스마스','크리스마스에는 뭐하니?','hkd1234',default,default,'kms1234',default,default);
insert into webMessage values (default,'글쎄~~','크리스마스는 가족과 함께~~','kms1234',default,default,'hkd1234',default,default);
insert into webMessage values (default,'홍길동님~~','서비스가 지연되어 죄송합니다.','admin',default,default,'hkd1234',default,default);
insert into webMessage values (default,'김말숙님 안녕하세요','불편사항 있으면 연락주세요.','admin',default,default,'kms1234',default,default);
