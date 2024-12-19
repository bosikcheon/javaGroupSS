show tables;

/*
  회원제: 준회원은 자료실의 목록검만 가능(업로드/다운로드 불가)
        정회원은 자료실 목록검색과 다운로드만 가능, 업로드 불가
*/ 

create table pds2 (
  idx  int not null auto_increment,		/* 자료실 고유번호 */
  mid  varchar(30) not null,					/* 올린이 아이디 */
  nickName 	varchar(30)  not null,		/* 올린이 닉네임 */
  fName			varchar(200) not null,		/* 업로드시 원본 파일명 */
  fSName		varchar(300) not null,		/* 실제 서버에 저장되는 파일명 */
  fSize			int not null,							/* 업로드파일의 총 사이즈 */
  part			varchar(20)	 not null, 		/* 파일 분류(학습/여행/음식/기타) */
  title			varchar(100) not null, 		/* 업로드파일의 간단한 제목 */
  content   text,											/* 업로드파일의 상세 설명 */
  openSw		varchar(3) default '공개',	/* 파일 공개여부(공개/비공개) */
  hostIp		varchar(40)  not null,		/* 업로드한 클라이언트 IP */
  pwd				varchar(100) not null,		/* 파일 삭제시에 비밀번호가 같을경우만 삭제처리한다. */
  fDate			datetime	default now(),	/* 업로드 시킨 날짜 */
  downNum		int default 0,						/* 파일 다운 횟수 */
  primary key(idx),
  foreign key(mid) references member(mid)
);
desc pds;

/* 리뷰 테이블 */
create table review(
  idx  int not null auto_increment,	/* 리뷰 고유번호 */
  part varchar(20) not null,				/* 분야(게시판:board, 자료실:pds, 포토갤러리:photo....) */
  partIdx int not null,							/* 해당 분야의 고유번호 */
  mid  varchar(30) not null,				/* 리뷰 올린이 아이디 */
  nickName varchar(30) not null,		/* 리뷰 올린이 닉네임 */
  content  text,										/* 리뷰 내용 */
  star int not null default 0,			/* 별점 부여 점수 */
  reviewDate datetime default now(),/* 리뷰 작성일 */
  primary key(idx),
  foreign key(mid) references member(mid)
);
desc review;
select * from review;

/* 리뷰 댓글 달기 */
create table reviewReply(
  replyIdx		int not null auto_increment,	/* 댓글의 고유번호 */
  reviewIdx 	int not null,									/* 원본글(부모:리뷰)의 고유번호 */
  replyMid  varchar(30) not null,						/* 리뷰댓글 올린이 아이디 */
  replyNickName varchar(30) not null,				/* 리뷰댓글 올린이 닉네임 */
  replyContent  text,												/* 댓글 내용 */
  replyDate datetime default now(),					/* 댓글 작성일 */
  primary key(replyIdx),
  foreign key(reviewIdx) references review(idx),
  foreign key(replyMid) references member(mid)
);
desc reviewReply;
select * from reviewReply;

select * from review where part = 'pds' and partIdx = 11 order by idx desc;

select * from (select * from review where part='pds' and partIdx=11) as v left join 
         reviewReply r on v.partIdx=11 and v.idx=r.reviewIdx order by v.idx desc, r.replyIdx desc;
