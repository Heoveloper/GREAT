--drop table
drop table product_info;

--drop sequence
drop sequence product_p_num_seq;


--create sequence
create sequence product_p_num_seq;  --상품번호

--create product info table
create table product_info (
p_number        number(30) not null,            --상품번호
owner_number    number(6) not null,             --판매자번호
p_title         varchar2(300) not null,         --제목
p_name          varchar2(30) not null,          --상품명
deadline_time   date not null,                  --마감일자
category        varchar2(17) not null,          --업종카테고리
total_count     number(5) not null,             --총수량
remain_count    number(5) not null,             --잔여수량
normal_price    number(8) not null,             --정상가
sale_price      number(8) not null,             --할인가
discount_rate   number(2) not null,             --할인율
payment_option  varchar2(32) not null,          --결제방식
detail_info     clob,                           --상품설명
r_date          date default sysdate not null,  --등록일자
u_date          date default sysdate not null,  --수정일자
p_status        number(1) default 0             --판매상태
);
--primary key
alter table product_info add constraint product_info_p_id_pk primary key (p_number);
--foreign key
alter table product_info add constraint product_info_p_num_fk foreign key (owner_number) references member(mem_number) on delete cascade;   --점주회원번호 회원테이블 참조
--check
alter table product_info add constraint porduct_info_p_status_ck check (p_status = 0 or p_status = 1);  --판매상태(0 or 1)