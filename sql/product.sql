--drop table
drop table product_info;

--drop sequence
drop sequence product_p_num_seq;


--create sequence
create sequence product_p_num_seq;  --상품번호

--create product info table
create table product_info (
p_number        number(30, 0) not null,
owner_number    number(6, 0) not null,
p_title         varchar2(300 byte) not null,
p_name          varchar2(30 byte) not null,
deadline_time   date not null,
category        varchar2(17 byte) not null,
total_count     number(5, 0) not null,
remain_count    number(5, 0) not null,
normal_price    number(8, 0) not null,
sale_price      number(8, 0) not null,
discount_rate   number(2, 0) not null,
payment_option  varchar2(32 byte) not null,
detail_info     clob,
r_date          date default sysdate not null,
u_date          date default sysdate not null,
p_status        number(1, 0) default 0
);
--primary key
alter table product_info add constraint product_info_p_id_pk primary key (p_number);
--foreign key
alter table product_info add constraint product_info_p_num_fk foreign key (owner_number) references member(mem_number) on delete cascade;   --점주회원번호 회원테이블 참조
--check
alter table product_info add constraint porduct_info_p_status_ck check (p_status = 0 or p_status = 1);  --판매상태(0 or 1)