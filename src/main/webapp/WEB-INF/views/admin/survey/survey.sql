show tables;

-- 설문지에 참여하는 사람들의 고유정보수집을 위한 필드설계
create table surveyBasic (
  idx   int not null auto_increment primary key,	/* 투표자 분석을 위한 고유번호 */
  gender 	varchar(2) 	not null,		/* 성별 */
  age			varchar(15) not null,		/* 연령대 */
  address varchar(15) not null		/* 거주 지역 */
);
desc surveyBasic;

/* 설문지 주제에 따른 큰 제목 설정 */
create table survey (
  idx   int not null auto_increment primary key,	/* 설문지(큰 제목) 고유번호 */
  title varchar(50) not null,									/* 설문지 큰 제목 */
  sDate	datetime default now() not null,			/* 설문 작성일 */
  startDate datetime default now() not null,								/* 설문 시작날짜 */
  endDate datetime default now() not null,									/* 설문 종료날짜 */
  showSw  int not null												/* 설문지 초기화면에 보이기(1)/가리기(0) */
);
desc survey;
insert into survey values (default,'겨울이면 생각나는 것?',default,default,default,1);
select * from survey;

/* 설문지 문항의 각종 답변지 유형을 설정하는 테이블(답변지 : '콤보(라디오)형/리스트(체크박스)형/텍스트(서술)형' */
create table surveyQuestion (
  idx   int not null auto_increment primary key,	/* 설문의 답변유형 고유번호 */
  sIdx  int not null,							/* 설문지의 고유번호 */
  qContent varchar(50) not null,	/* 설문 문항의 개별 질의 문구 */
  answerSw	int not null,					/* 답변유형(텍스트(서술형) 0, 체크박스형 1, 콤보형 2) */
  foreign key(sIdx) references survey(idx) on delete restrict
);
desc surveyQuestion;
insert into surveyQuestion values (default,1,'겨울에 좋아하는 음식?',1);
insert into surveyQuestion values (default,1,'겨울에 가고 싶은곳?',2);
insert into surveyQuestion values (default,1,'겨울을 즐기기 위한 나만의 비법을 써주세요',0);
select * from surveyQuestion;

-- 설문문항지에 각 항목에 따른 답변항목리스트 테이블
create table surveyQuestionContent (
  idx   int not null auto_increment primary key,	/* 각 문항별 고유번호 */
  sIdx  int not null,							/* 설문지의 고유번호 */
  qIdx  int not null,							/* 설문 각 소제목의 고유번호 */
  aContent varchar(50),						/* 소항목에 따른 질문 내용 */
  foreign key(sIdx) references survey(idx) on delete restrict,
  foreign key(qIdx) references surveyQuestion(idx) on delete restrict
);
desc surveyQuestionContent;
insert into surveyQuestionContent values (default,1,1,'아이스크림');
insert into surveyQuestionContent values (default,1,1,'오뎅국물');
insert into surveyQuestionContent values (default,1,1,'국밥');
insert into surveyQuestionContent values (default,1,1,'붕어빵');
insert into surveyQuestionContent values (default,1,1,'귤');
insert into surveyQuestionContent values (default,1,2,'겨울바다');
insert into surveyQuestionContent values (default,1,2,'한라산');
insert into surveyQuestionContent values (default,1,2,'찜질방');
insert into surveyQuestionContent values (default,1,2,'스키장');
insert into surveyQuestionContent values (default,1,2,'얼음축제장');
insert into surveyQuestionContent values (default,1,3,'냉수마찰');
select * from surveyQuestionContent;

/* 설문 문항지에 따른 각 항목별 답변 내용 */
create table surveyRealAnswer (
	idx   int not null auto_increment primary key,	/* 설문문항지 답변 고유번호 */
  bIdx  int not null,							/* 설문에 참여하는 사람들의 기본정보를 담고있는 고유번호 */
  sIdx  int not null,							/* 설문지의 고유번호 */
  qIdx  int not null,							/* 설문 각 소제목(질문지)의 고유번호 */
  aIdx  int not null,							/* 설문 소제목의 답변문항의 답변한 번호(콤보형/리스트형일때는 각 문항별 번호) */
  detailAnswer varchar(50) not null,	/* 설문문항지의 답변문항이 서술형(0)일때의 답변내역 */
  foreign key(bIdx) references surveyBasic(idx) on delete restrict,
  foreign key(sIdx) references survey(idx) on delete restrict,
  foreign key(qIdx) references surveyQuestion(idx) on delete restrict
);


drop table surveyRealAnswer;
drop table surveyQuestionContent;
drop table surveyQuestion;
drop table survey;
drop table surveyBasic;

select * from surveyQuestion where sIdx = 1;
select *,(select count(*) from serveyRealAnswer where aIdx=a.idx) from surveyQuestionContent a where sIdx = 1;