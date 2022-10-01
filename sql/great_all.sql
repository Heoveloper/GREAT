--drop table
drop table withdrawal_member;
drop table deal;
drop table good;
drop table review;
drop table bookmark;
drop table profile;
drop table product_info;
drop table comments;
drop table article;
drop table uploadfile;
drop table member;
drop table notice;
drop table uploadfile;

--drop sequence
drop sequence mem_num;
drop sequence deal_order_number_seq;
drop sequence notice_notice_id_seq;
drop sequence article_article_num_seq;
drop sequence comments_comment_num_seq;
drop sequence uploadfile_uploadfile_id_seq;
drop sequence good_good_number_seq;
drop sequence review_review_number_seq;
drop sequence profile_profile_number_seq;
drop sequence PRODUCT_P_NUMBER_SEQ;
drop sequence bookmark_bookmark_number_seq;

--===========================================회원================================================================
--탈퇴회원 테이블
create table withdrawal_member (
mem_number number(9),
mem_type varchar2(15),
mem_id varchar2(30),
mem_password varchar2(18),
mem_name varchar2(18),
mem_nickname varchar2(18),
mem_email varchar2(30),
mem_businessnumber varchar2(10),
mem_store_name varchar2(45),
mem_store_phonenumber varchar2(15),
mem_store_location varchar2(150),
mem_store_latitude number(15, 9),
mem_store_longitude number(15, 9),
mem_store_introduce varchar2(150),
mem_store_sns varchar2(150),
mem_regtime date,
mem_lock_expiration date,
mem_admin varchar2(3)
);
--primary key
alter table withdrawal_member add constraint withdrawal_member_mem_number_pk primary key (mem_number);
--unique
alter table withdrawal_member add constraint withdrawal_member_mem_id_un unique (mem_id);
alter table withdrawal_member add constraint withdrawal_member_mem_nickname_un unique (mem_nickname);
alter table withdrawal_member add constraint withdrawal_member_mem_email_un unique (mem_email);
alter table withdrawal_member add constraint withdrawal_member_mem_businessnumber_un unique (mem_businessnumber);
alter table withdrawal_member add constraint withdrawal_member_mem_store_location_un unique (mem_store_location);
alter table withdrawal_member add constraint withdrawal_member_mem_store_latitude_un unique (mem_store_latitude);
alter table withdrawal_member add constraint withdrawal_member_mem_store_longitude_un unique (mem_store_longitude);
--not null
alter table withdrawal_member modify mem_number constraint withdrawal_member_mem_number_nn not null;
alter table withdrawal_member modify mem_type constraint withdrawal_member_mem_type_nn not null;
alter table withdrawal_member modify mem_id constraint withdrawal_member_mem_id_nn not null;
alter table withdrawal_member modify mem_password constraint withdrawal_member_mem_password_nn not null;
alter table withdrawal_member modify mem_name constraint withdrawal_member_mem_name_nn not null;
alter table withdrawal_member modify mem_nickname constraint withdrawal_member_mem_nickname_nn not null;
alter table withdrawal_member modify mem_email constraint withdrawal_member_mem_email_nn not null;
alter table withdrawal_member modify mem_regtime constraint withdrawal_member_mem_regtime_nn not null;

--회원번호 시퀀스
create sequence mem_num
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
    
--회원 테이블
create table member (
mem_number number(9),
mem_type varchar2(15),
mem_id varchar2(30),
mem_password varchar2(18),
mem_name varchar2(18),
mem_nickname varchar2(18),
mem_email varchar2(30),
mem_businessnumber varchar2(10),
mem_store_name varchar2(45),
mem_store_phonenumber varchar2(15),
mem_store_location varchar2(150),
mem_store_latitude number(15, 9),
mem_store_longitude number(15, 9),
mem_store_introduce varchar2(150),
mem_store_sns varchar2(150),
mem_regtime date,
mem_lock_expiration date,
mem_admin varchar2(3)
);
--primary key
alter table member add constraint member_mem_number_pk primary key (mem_number);
--unique
alter table member add constraint member_mem_id_un unique (mem_id);
alter table member add constraint member_mem_nickname_un unique (mem_nickname);
alter table member add constraint member_mem_email_un unique (mem_email);
alter table member add constraint member_mem_businessnumber_un unique (mem_businessnumber);
alter table member add constraint member_mem_store_location_un unique (mem_store_location);
alter table member add constraint member_mem_store_latitude_un unique (mem_store_latitude);
alter table member add constraint member_mem_store_longitude_un unique (mem_store_longitude);
--not null
alter table member modify mem_number constraint member_mem_number_nn not null;
alter table member modify mem_type constraint member_mem_type_nn not null;
alter table member modify mem_id constraint member_mem_id_nn not null;
alter table member modify mem_password constraint member_mem_password_nn not null;
alter table member modify mem_name constraint member_mem_name_nn not null;
alter table member modify mem_nickname constraint member_mem_nickname_nn not null;
alter table member modify mem_email constraint member_mem_email_nn not null;
alter table member modify mem_regtime constraint member_mem_regtime_nn not null;
--default
alter table member modify mem_number number(9) default mem_num.nextval;
alter table member modify mem_regtime date default sysdate;
alter table member modify mem_admin varchar2(3) default 'n';

--===========================================상품================================================================
--상품 테이블 생성
create table product_info(
     P_NUMBER NUMBER(30, 0) not null
,    OWNER_NUMBER NUMBER(6, 0) not null
,    P_TITLE VARCHAR2(300 BYTE) not null
,    P_NAME VARCHAR2(30 BYTE) not null
,    DEADLINE_TIME DATE not null
,    CATEGORY VARCHAR2(17 BYTE) not null
,    TOTAL_COUNT NUMBER(5, 0) not null
,    REMAIN_COUNT NUMBER(5, 0) not null
,    NORMAL_PRICE NUMBER(8, 0) not null
,    SALE_PRICE NUMBER(8, 0) not null
,    DISCOUNT_RATE NUMBER(2, 0) not null
,    PAYMENT_OPTION VARCHAR2(32 BYTE) not null
,    DETAIL_INFO clob
,    R_DATE DATE default sysdate not null
,    U_DATE DATE default sysdate not null
,    P_STATUS NUMBER(1, 0) default 0
);

--기본키 설정
ALTER TABLE PRODUCT_INFO ADD CONSTRAINT product_info_p_id_pk PRIMARY key(p_NUMBER);
 --외래키 설정
 alter table PRODUCT_INFO ADD CONSTRAINT product_info_p_num_fk FOREIGN key(OWNER_NUMBER) REFERENCES member(mem_number) on delete cascade;


-- 상품번호 시퀀스 생성
create sequence PRODUCT_P_NUMBER_SEQ;

-- 판매상태(0,1만 선택 가능)
ALTER TABLE PRODUCT_INFO ADD CONSTRAINT porduct_info_p_status_ck CHECK (P_STATUS =0 OR P_STATUS =1);

--===========================================거래/리뷰================================================================
create table deal ( --거래테이블 생성
order_number number(10),  --주문번호
buyer_number number (6), --구매자번호
seller_number number (6),--핀매자번호
p_number number (30),   --판매번호
p_count number (3),      --상품수량
price number (6),        --가격
visittime date,          --방문예정시간
buy_type number(1),     --결제유형
r_status number(1) default 0,      --리뷰상태
o_status number(1) default 0,      --주문상태
orderdate date default sysdate,          --주문일자
pickup_status number (1) default 0 --픽업상태
);

--거래번호 시퀀스 생성
create sequence deal_order_number_seq;

--기본키설정
alter table deal add constraint deal_order_number_pk primary key (order_number);
--외래키설정
alter table deal add constraint deal_buyer_number_fk
    foreign key (buyer_number) references member (mem_number) on delete cascade;  --구매자 회원테이블 참조
alter table deal add constraint deal_seller_number_fk
    foreign key (seller_number) references member (mem_number)on delete cascade; --판매자 회원 테이블 참조
alter table deal add constraint deal_p_number_fk
    foreign key (p_number) references product_info (p_number) on delete cascade; --상품 번호 상품 테이블 참조
    
-- 결제유형
ALTER TABLE deal ADD CONSTRAINT deal_buy_type_ck CHECK (buy_type ='0' OR buy_type ='1');
-- 리뷰상태(0,1만 선택 가능)
ALTER TABLE deal ADD CONSTRAINT deal_r_status_ck CHECK (r_status ='0' OR r_status ='1'); 
-- 주문상태
ALTER TABLE deal ADD CONSTRAINT deal_o_status_ck CHECK (o_status ='0' OR o_status ='1'); 
-- 픽업상태
ALTER TABLE deal ADD CONSTRAINT deal_pickup_status_ck CHECK (pickup_status ='0' OR pickup_status ='1'); 
  
    
create table profile (  --프로필 테이블
profile_number number(5),  --프로필 번호
mem_number number (6),-- 유저 번호
p_number number(5)--상품 번호
);

--프로필번호 시퀀스 생성
create sequence  profile_profile_number_seq;

--기본키설정
alter table profile add constraint profile_profile_number_pk primary key (profile_number);--프로필 번호 pk
--외래키설정
alter table profile add constraint profile_mem_number_fk
    foreign key (mem_number) references member (mem_number) on delete cascade;-- 유저번호 회원테이블 fk 참조
alter table profile add constraint profile_p_number_fk
    foreign key (p_number) references product_info (p_number) on delete cascade; --판매글 번호 판매테이블 fk 참조


create table review (   --리뷰테이블
review_number number(10),  --리뷰 번호
buyer_number number (6),-- 작성자 번호
seller_number number(6),-- 판매자 번호
content varchar2(150),--본문 내용
write_date date default sysdate, --작성일
grade number(2), -- 평점
profile_number number (10) --프로필 번호
);     

--리뷰번호 시퀀스 생성
create sequence  review_review_number_seq;

--기본키설정
alter table review add constraint review_review_number_pk primary key (review_number);--리뷰 번호 pk
--외래키설정
alter table review add constraint review_buyer_number_fk
    foreign key (buyer_number) references member (mem_number) on delete cascade;-- 작성자번호 회원테이블 fk 참조
alter table review add constraint review_seller_number_fk   
    foreign key (seller_number) references member (mem_number) on delete cascade;--판매자번호 회원테이블 fk 참조
alter table review add constraint review_profile_number_fk
    foreign key (profile_number) references profile (profile_number)on delete cascade; --프로필번호 프로필테이블 fk참조
  
create table good ( --좋아요 테이블
good_number number(10),  --좋아요 번호
mem_number number (6),-- 회원 번호
p_number number(5)-- 상품 번호
);    

--좋아요번호 시퀀스 생성
create sequence  good_good_number_seq;

--기본키설정
alter table good add constraint good_good_id_pk primary key (good_number);--좋아요 번호 pk
--외래키설정
alter table good add constraint good_mem_number_fk
    foreign key (mem_number) references member (mem_number) on delete cascade;-- 회원번호 회원테이블 fk 참조
alter table good add constraint good_p_number_fk
    foreign key (p_number) references product_info (p_number) on delete cascade;-- 상품번호 상품테이블 fk 참조  
    
create table bookmark ( --즐겨찾기 테이블
bookmark_number number(10),  --즐겨찾기 번호
mem_number number (10),-- 회원 번호
profile_number number(10)-- 프로필 번호
);   

--즐겨찾기번호 시퀀스 생성
create sequence  bookmark_bookmark_number_seq;

--기본키설정
alter table bookmark add constraint bookmark_bookmark_number_pk primary key (bookmark_number);--좋아요 번호 pk
--외래키설정
alter table bookmark add constraint bookmark_mem_number_fk
    foreign key (mem_number) references member (mem_number) on delete cascade;-- 회원번호 회원테이블 fk 참조    
alter table bookmark add constraint bookmark_profile_number_fk
    foreign key (profile_number) references profile (profile_number) on delete cascade;-- 프로필번호 프로필테이블 fk 참조    
--===========================================게시판================================================================

--커뮤니티 테이블 생성
create table article (
  article_num           number(6),        -- 게시글 번호
  mem_number            number(6),        -- 회원 번호
  article_category      varchar2(10),     -- 게시글 카테고리
  article_title         varchar2(90),     -- 게시글 제목
  article_contents      clob,   -- 게시글 내용
  attachment            varchar2(1),      -- 첨부파일 유무
  create_date           date,             -- 게시글 작성일
  views                 number(5)         -- 조회수
);
--primary key
alter table article add constraint article_article_num_pk primary key(article_num);
--foreign key
alter table article add constraint article_mem_number_fk foreign key(mem_number) references member(mem_number) on delete cascade;
--default
alter table article modify create_date date default sysdate;
alter table article modify views number(5) default 0;
--not null
alter table article modify article_category constraint article_article_category_nn not null;
alter table article modify article_title constraint article_article_title_nn not null;
alter table article modify article_contents constraint article_article_contents_nn not null;
alter table article modify attachment constraint article_attachment_nn not null;

--게시글 번호 시퀀스 생성
create sequence article_article_num_seq
increment by 1
    start with 1
    minvalue 1
    maxvalue 999999
    nocycle
    nocache
    noorder;

--댓글 테이블 생성
create table comments (
  article_num          number(6),       -- 게시글 번호
  comment_group        number(6),       -- 댓글 그룹
  comment_num          number(6),       -- 댓글 번호
  p_comment_num        number(6),       -- 부모 댓글 번호
  mem_number           number(6),       -- 회원 번호
  comment_contents     varchar2(300),   -- 댓글 내용
  create_date          date,            -- 댓글 생성일
  comment_indent       number(3)        -- 대댓글 들여쓰기
);

--primary key
alter table comments add constraint comments_comment_num_pk primary key(comment_num);
--foreign key
alter table comments add constraint comments_article_num_fk foreign key(article_num) references article(article_num) on delete cascade;
alter table comments add constraint comments_mem_number_fk foreign key(mem_number) references member(mem_number) on delete cascade;
alter table comments add constraint comments_p_comment_num_fk foreign key(p_comment_num) references comments(comment_num) on delete set null;
--default
alter table comments modify create_date date default sysdate;
alter table comments modify comment_indent number default 0;
--not null
alter table comments modify comment_contents constraint comments_comment_contents_nn not null;

--댓글 번호 시퀀스 생성
create sequence comments_comment_num_seq
increment by 1
    start with 1
    minvalue 1
    maxvalue 999999
    nocycle
    nocache
    noorder;

--===========================================공지사항================================================================
create table notice(
    notice_id    number(8),
    title          varchar2(150),
    content     Varchar2(1500),
    write         varchar2(30),
    count        number(5) default 0,
    udate       timestamp default systimestamp
);
--기본키생성
alter table notice add Constraint notice_notice_id_pk primary key (notice_id);

--제약조건 not null
alter table notice modify title constraint notice_title_nn not null;
alter table notice modify content constraint notice_content_nn not null;
alter table notice modify write constraint notice_write_nn not null;

--시퀀스

create sequence notice_notice_id_seq;

--======================첨부파일==============================================================================
create table uploadfile(
    uploadfile_id   number(10),     --파일아이디
    code            varchar2(11),   --분류코드
    rid             varchar2(10),     --참조번호(게시글번호등)
    store_filename  varchar2(100),   --서버보관파일명
    upload_filename varchar2(100),   --업로드파일명(유저가 업로드한파일명)
    fsize           varchar2(45),   --업로드파일크기(단위byte)
    ftype           varchar2(100),   --파일유형(mimetype)
    cdate           timestamp default systimestamp, --등록일시
    udate           timestamp default systimestamp  --수정일시
);
--기본키
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key(uploadfile_id);

--외래키
alter table uploadfile add constraint uploadfile_uploadfile_id_fk
    foreign key(code) references code(code_id);

--제약조건
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;

--시퀀스
create sequence uploadfile_uploadfile_id_seq;