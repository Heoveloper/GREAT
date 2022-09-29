--drop
drop table withdrawal_member;
drop table member;
drop sequence mem_num;


----탈퇴회원 테이블
--create table withdrawal_member (
--mem_number number(9),
--mem_type varchar2(15),
--mem_id varchar2(30),
--mem_password varchar2(18),
--mem_name varchar2(18),
--mem_nickname varchar2(18),
--mem_email varchar2(30),
--mem_businessnumber varchar2(10),
--mem_store_name varchar2(45),
--mem_store_phonenumber varchar2(15),
--mem_store_location varchar2(150),
--mem_store_latitude number(15, 9),
--mem_store_longitude number(15, 9),
--mem_store_introduce varchar2(150),
--mem_store_sns varchar2(150),
--mem_regtime date,
--mem_lock_expiration date,
--mem_admin varchar2(3)
--);
----primary key
--alter table withdrawal_member add constraint withdrawal_member_mem_number_pk primary key (mem_number);
----unique
--alter table withdrawal_member add constraint withdrawal_member_mem_id_un unique (mem_id);
--alter table withdrawal_member add constraint withdrawal_member_mem_nickname_un unique (mem_nickname);
--alter table withdrawal_member add constraint withdrawal_member_mem_email_un unique (mem_email);
--alter table withdrawal_member add constraint withdrawal_member_mem_businessnumber_un unique (mem_businessnumber);
--alter table withdrawal_member add constraint withdrawal_member_mem_store_location_un unique (mem_store_location);
--alter table withdrawal_member add constraint withdrawal_member_mem_store_latitude_un unique (mem_store_latitude);
--alter table withdrawal_member add constraint withdrawal_member_mem_store_longitude_un unique (mem_store_longitude);
----not null
--alter table withdrawal_member modify mem_number constraint withdrawal_member_mem_number_nn not null;
--alter table withdrawal_member modify mem_type constraint withdrawal_member_mem_type_nn not null;
--alter table withdrawal_member modify mem_id constraint withdrawal_member_mem_id_nn not null;
--alter table withdrawal_member modify mem_password constraint withdrawal_member_mem_password_nn not null;
--alter table withdrawal_member modify mem_name constraint withdrawal_member_mem_name_nn not null;
--alter table withdrawal_member modify mem_nickname constraint withdrawal_member_mem_nickname_nn not null;
--alter table withdrawal_member modify mem_email constraint withdrawal_member_mem_email_nn not null;
--alter table withdrawal_member modify mem_regtime constraint withdrawal_member_mem_regtime_nn not null;


--회원번호 시퀀스
--create sequence mem_num
--    increment by 1
--    start with 1
--    minvalue 1
--    nomaxvalue
--    nocycle
--    nocache;
create sequence mem_num;

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
--alter table member modify mem_type constraint member_mem_type_nn not null;
--alter table member modify mem_id constraint member_mem_id_nn not null;
alter table member modify mem_password constraint member_mem_password_nn not null;
alter table member modify mem_name constraint member_mem_name_nn not null;
alter table member modify mem_nickname constraint member_mem_nickname_nn not null;
alter table member modify mem_email constraint member_mem_email_nn not null;
alter table member modify mem_regtime constraint member_mem_regtime_nn not null;
--default
--alter table member modify mem_number number(9) default mem_num.nextval;
alter table member modify mem_regtime date default sysdate;
alter table member modify mem_admin varchar2(3) default 'n';


--고객회원 회원가입 쿼리
--insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email)
--values ('customer', 'customer123', '@pwcust123', '김고객', '별명1', 'test1@test.com');
--insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email)
--values ('customer', 'customer456', '@pwcust456', '이고객', '별명2', 'test2@test.com');
--insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email)
--values ('customer', 'customer789', '@pwcust789', '박고객', '별명3', 'test3@test.com');
--점주회원 회원가입 쿼리
--insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email,
--mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_introduce, mem_store_sns)
--values ('owner', 'owner123', '@pwown123', '김점주', '별칭1', 'tester1@test.com',
--'1112233333', '싸다구돼지국밥', '0521112222', '울산광역시 중구 달동', null, null);
--insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email,
--mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_introduce, mem_store_sns)
--values ('owner', 'owner456', '@pwown456', '이점주', '별칭2', 'tester2@test.com',
--'4445566666', '그레이트스시', '0523334444', '울산광역시 동구 일산동', '마감 직전이어도 신선하게 나갑니다', 'https://www.instagram.com/greatsushi/');
--insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email,
--mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_introduce, mem_store_sns)
--values ('owner', 'owner789', '@pwown789', '박점주', '별칭3', 'tester3@test.com',
--'7778899999', '삼팀커피', '0525556666', '울산광역시 남구 신정동', '아인슈페너 맛집입니다', 'https://www.instagram.com/3teamcoffee/');
----[회원가입 확인]
--select * from member;
----아이디 중복확인 쿼리
--select count(*) "아이디 중복확인: 1이면 중복, 0이면 중복X"
--  from member
-- where mem_id = 'customer123'; --회원가입시 입력받은 아이디
----닉네임 중복확인 쿼리
--select count(*) "닉네임 중복확인: 1이면 중복, 0이면 중복X"
--  from member
-- where mem_nickname = '별칭1004'; --회원가입시 입력받은 닉네임
--
----로그인 쿼리 (회원 조회)
----아이디와 패스워드를 입력받아 필요한 회원정보 조회 (select 필요한 회원정보)
--select *
--  from member
-- where mem_id = 'owner123' --로그인시 입력받은 아이디
--   and mem_password = '@pwown123'; --로그인시 입력받은 패스워드
----로그인 쿼리 2
----'입력받은 패스워드'와 쿼리로 출력한 '입력받은 아이디와 매칭되는 패스워드'가 같을 시 로그인 성공
--select mem_password "입력아이디와 매칭되는 패스워드"
--  from member
-- where mem_id = 'owner123'; --로그인시 입력받은 아이디
----아이디 찾기 쿼리
--select mem_id "찾은 아이디", mem_regtime "찾은 아이디의 가입일자"
--  from member
-- where mem_name = '김고객'  --아이디찾기시 입력받은 이름
--   and mem_email = 'test1@test.com'; --아이디찾기시 입력받은 이메일
----비밀번호 찾기 쿼리
----'입력받은 이메일'과 쿼리로 출력한 '입력받은 아이디와 매칭되는 이메일'이 같을 시 인증번호 발송
----입력한 인증번호가 발송한 인증번호와 일치하면 비밀번호 재설정 화면으로 이동
--select mem_email "입력아이디와 매칭되는 이메일"
--  from member
-- where mem_id = 'owner123'; --입력받은 아이디
----비밀번호 재설정 쿼리
--update member
--   set mem_password = '@pwown123123' --입력받은 패스워드
-- where mem_id = 'owner123'; --비밀번호 찾기 화면에서 입력받은 아이디
-- 
----회원정보 조회·수정·탈퇴 시도시 인증 쿼리
----'입력받은 패스워드'와 쿼리로 출력한 '로그인된 아이디와 매칭되는 패스워드'가 같을 시 조회·수정·탈퇴 가능
--select mem_password "로그인된 아이디와 매칭되는 패스워드"
--  from member
-- where mem_id = 'owner123'; --로그인된 아이디(로그인시 입력받은 아이디)
----회원정보 수정 쿼리
--update member
--   set mem_password = '@pwown456456',
--       mem_store_introduce = '신선도가 첫째인 그레이트 스시입니다' --입력받은 수정사항들
-- where mem_id = 'owner456'; --로그인된 아이디
----[수정까지 커밋]
--commit;
----회원탈퇴 쿼리
--insert into withdrawal_member --회원 테이블에서 탈퇴회원 테이블로 데이터 이동
--     select *
--       from member
--      where mem_id = 'owner789'; --로그인된 아이디
--delete --회원정보 삭제
--  from member
-- where mem_id = 'owner789'; --로그인된 아이디
----[탈퇴는 롤백]
--rollback;
-- 
----고객회원 조회
--select * from member where mem_type = 'customer';
----점주회원 조회
--select * from member where mem_type = 'owner';
----탈퇴회원 테이블 조회
--select * from withdrawal_member;
--회원 테이블 조회
select * from member;