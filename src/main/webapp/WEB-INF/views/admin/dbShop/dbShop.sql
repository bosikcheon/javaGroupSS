show tables;

/* 대분류(main) */
create table categoryMain (
  categoryMainCode  char(1)  not null,		/* 대분류코드(A,B,C,...) => 영문 대문자 1자(중복불허) */
  categoryMainName  varchar(20) not null, /* 대분류명(회사명 => 삼성/현대/LG...(중복불허) */
  primary key(categoryMainCode),
  unique key(categoryMainName)
);
desc categoryMain;

insert into categoryMain values ('A','삼성');
insert into categoryMain values ('B','LG');
insert into categoryMain values ('C','현대');

select * from categoryMain;


/* 중분류(middle) */
create table categoryMiddle (
	categoryMainCode  char(1)  not null,			/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode  char(2)  not null,		/* 중분류코드(01,02,03,...) => 문자형 숫자 2자(중복불허) */
  categoryMiddleName  varchar(20) not null, /* 중분류명(제품분류명 => 전자제품/생활가전/차종/의류/신발류...(중복불허) */
  primary key(categoryMiddleCode),
  foreign key(categoryMainCode) references categoryMain(categoryMainCode)
);
desc categoryMiddle;

insert into categoryMiddle values ('B', '01', '전자제품');
insert into categoryMiddle values ('A', '02', '전자제품');
insert into categoryMiddle values ('C', '03', '자동차');

select * from categoryMiddle;


/* 소분류(sub) */
create table categorySub (
	categoryMainCode  char(1)  not null,			/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode  char(2)  not null,		/* 중분류코드를 외래키로 지정 */
  categorySubCode  char(3)  not null,				/* 소분류코드(001,002,003,...) => 문자형 숫자 3자(중복불허) */
  categorySubName  varchar(20) not null, 		/* 소분류명(상품구분 => 중분류가 전자제품이라면? 냉장고/에어컨/오디오/TV */
  primary key(categorySubCode),
  foreign key(categoryMainCode) references categoryMain(categoryMainCode),
  foreign key(categoryMiddleCode) references categoryMiddle(categoryMiddleCode)
);
desc categorySub;

insert into categorySub values ('B','01','001','냉장고');
insert into categorySub values ('B','01','002','TV');
insert into categorySub values ('B','01','003','선풍기');
insert into categorySub values ('A','02','004','냉장고');
insert into categorySub values ('A','02','005','세탁기');
insert into categorySub values ('A','02','006','핸드폰');

select * from categorySub;



/* 상품테이블(세분류) */
create table dbProduct (
  idx  int not null, 				/* 상품 고유번호 */
	categoryMainCode  char(1)  not null,			/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode  char(2)  not null,		/* 중분류코드를 외래키로 지정 */
  categorySubCode  char(3)  not null,				/* 소분류코드를 외래키로 지정 */
  productCode   varchar(20) not null,				/* 상품고유코드(대분류코드+중분류코드+소분류코드+상품고유번호) 예 : A 01 002 5 */
  productName   varchar(50) not null,				/* 상품명(상품모델명) */
  detail				varchar(100) not null,			/* 상품의 간단설명(초기화면 메인창에 출력할 간단한 설명) */
  mainPrice     int not null,								/* 상품의 기본가격 */
  fSName				varchar(200) not null,			/* 상품의 기본사진(1장이상 처리시는 '/'로 구분한다.) */
  content				text not null,							/* 상품의 상세설명 - ckeditor를 이용 */
  primary key(idx),
  unique key(productCode,productName),
  foreign key(categoryMainCode) references categoryMain(categoryMainCode),
  foreign key(categoryMiddleCode) references categoryMiddle(categoryMiddleCode),
  foreign key(categorySubCode) references categorySub(categorySubCode)
);
desc dbProduct;

/* 옵션 테이블 */
create table dbOption (
  idx    int not null auto_increment, /* 옵션 고유번호 */
  productIdx int not null,						/* product테이블(상품)의 고유번호 - 외래키로 지정 */
  optionName varchar(50) not null, 		/* 옵션 이름 */
  optionPrice int not null default 0, /* 옵션 가격 */
  primary key(idx),
  foreign key(productIdx) references dbProduct(idx)
);

desc dbOption;
select * from dbOption where productIdx = 1 order by optionName;

/* 상품 상세설명시 초기화면에 출력될 이미지 리스트.... */


drop table dbOption;
drop table dbProduct;
drop table categorySub;
drop table categoryMiddle;
drop table categoryMain;


/* ==============상품 주문시에 사용되는 테이블(장바구니/주문/배송/이벤트/쿠폰/QR코드)===================== */

/* 장바구니 테이블 */
create table dbCart (
  idx   int not null auto_increment,
  cartDate datetime default now() not null,	/* 장바구니에 상품을 담은 날짜 */
  mid		varchar(20) not null,						/* 장바구니를 사용한 사용자의 아이디 - 로그인한 회원 아이디 */
  productIdx 	int not null,							/* 장바구니에 담은 상품의 고유번호 */
  productName varchar(50) not null,			/* 장바구니에 담은 상품명 */
  mainPrice		int not null,							/* 메인상품의 기본 가격 */
  thumbImg		varchar(100) not null,		/* 서버에 저장된 상품의 메인 이미지 */
  optionIdx		varchar(50)  not null,		/* 옵션의 고유번호 리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  optionName	varchar(100)  not null,		/* 옵션명 리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  optionPrice	varchar(100)  not null,		/* 옵션가격 리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  optionNum		varchar(50)  not null,		/* 옵션수량 리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  totalPrice	int not null,							/* 구매한 모든 항목(상품+옵션)에 따른 총 가격 */  
  primary key(idx),
  foreign key(mid) references member(mid) on update cascade on delete cascade
);
desc dbCart;

/* 주문 테이블 */
create table dbOrder (
  idx   int not null auto_increment,
  orderIdx  varchar(15) not null,				/* 주문 고유번호(새로 만들어준다) */
  mid		varchar(30) not null,						/* 주문한 사용자의 아이디 - 로그인한 회원 아이디 */
  productIdx 	int not null,							/* 주문한 상품의 고유번호 */
  orderDate   datetime default now() not null, /* 실제 주문한 날짜 */
  productName varchar(50) not null,			/* 주문한 상품명 */
  mainPrice		int not null,							/* 메인상품의 기본 가격 */
  thumbImg		varchar(100) not null,		/* 서버에 저장된 상품의 메인 이미지 */
  optionName	varchar(100)  not null,		/* 옵션명 리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  optionPrice	varchar(100)  not null,		/* 옵션가격 리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  optionNum		varchar(50)  not null,		/* 옵션수량 리스트(여러개가 될수 있기에 문자열 배열로 처리한다.) */
  totalPrice	int not null,							/* 구매한 모든 항목(상품+옵션)에 따른 총 가격 */  
  primary key(idx),
  foreign key(mid) references member(mid) on update cascade on delete cascade,
  foreign key(productIdx) references dbProduct(idx) on update cascade on delete restrict
);
desc dbOrder;

/* 배송테이블 */
create table dbBaesong (
  idx     int not null auto_increment,
  oIdx    int not null,								/* 주문테이블의 고유번호를 외래키로 지정함 */
  orderIdx    varchar(15) not null,   /* 주문 고유번호 */
  orderTotalPrice int     not null,   /* 주문한 모든 상품의 총 가격 */
  mid         varchar(30) not null,   /* 회원 아이디 */
  name				varchar(30) not null,   /* 배송지 받는사람 이름 */
  address     varchar(100) not null,  /* 배송지 (우편번호)주소 */
  tel					varchar(15),						/* 받는사람 전화번호 */
  message     varchar(100),						/* 배송시 요청사항 */
  payment			varchar(10)  not null,	/* 결재도구 */
  payMethod   varchar(50)  not null,  /* 결재도구에 따른 방법(카드번호) */
  orderStatus varchar(10)  not null default '결제완료', /* 주문순서(결제완료->배송준비중->배송중->배송완료->구매완료) */
  primary key(idx),
  foreign key(oIdx) references dbOrder(idx) on update restrict on delete restrict
  /* foreign key(oIdx) references dbOrder(idx) on update cascade on delete cascade */
);
desc dbBaesong;




select product.*, main.categoryMainName, middle.categoryMiddleName, (select categorySubName from categorySub where categorySubCode=product.categorySubCode) as categorySubName
  	from dbProduct product, categoryMain main, categoryMiddle middle, categorySub sub
  	where productName='LGTV_86QNED80KRA' order by idx limit 1;
  	