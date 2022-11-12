--drop table
drop table deal;
drop table good;
drop table review;
drop table bookmark;
drop table comments;
drop table article;
drop table uploadfile;
drop table notice;
drop table product_info;
drop table member;

--drop sequence
drop sequence member_mem_num_seq;
drop sequence deal_order_num_seq;
drop sequence notice_notice_id_seq;
drop sequence article_article_num_seq;
drop sequence comments_comment_num_seq;
drop sequence uploadfile_uploadfile_id_seq;
drop sequence good_good_num_seq;
drop sequence review_review_num_seq;
drop sequence product_p_num_seq;
drop sequence bookmark_bookmark_num_seq;

------------------------------ member ------------------------------
--create table: member
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
alter table member modify mem_number number(9) default member_mem_num_seq.nextval;
alter table member modify mem_regtime date default sysdate;
alter table member modify mem_admin varchar2(3) default 'n';

--create sequence: member number
create sequence member_mem_num_seq
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
    
------------------------------ product ------------------------------
--create table: product
create table product_info (
p_number number(30) not null,
owner_number number(6) not null,
p_title varchar2(300) not null,
p_name varchar2(30) not null,
deadline_time date not null,
category varchar2(17) not null,
total_count number(5) not null,
remain_count number(5) not null,
normal_price number(8) not null,
sale_price number(8) not null,
discount_rate number(2) not null,
payment_option varchar2(32) not null,
detail_info clob,
r_date date default sysdate not null,
u_date date default sysdate not null,
p_status number(1) default 0
);
--primary key
alter table product_info add constraint product_info_p_id_pk primary key (p_number);
--foreign key
alter table product_info add constraint product_info_p_num_fk foreign key (owner_number) references member(mem_number) on delete cascade;
--check
alter table product_info add constraint porduct_info_p_status_ck check (p_status = 0 or p_status = 1);  --sales status(0 or 1)

--create sequence: product number
create sequence product_p_num_seq;

------------------------------ deal ------------------------------
--create table: deal
create table deal (
order_number number(10),
buyer_number number(6),
seller_number number(6),
p_number number(30),
p_count number(3),
price number(6),
visittime date,
buy_type number(1),
r_status number(1) default 0,
o_status number(1) default 0,
orderdate date default sysdate,
pickup_status number(1) default 0
);
--primary key
alter table deal add constraint deal_order_number_pk primary key (order_number);
--foreign key
alter table deal add constraint deal_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;
alter table deal add constraint deal_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;
alter table deal add constraint deal_p_number_fk foreign key (p_number) references product_info(p_number) on delete cascade;
--check
alter table deal add constraint deal_buy_type_ck check (buy_type = '0' or buy_type = '1');                  --payment type(0 or 1)
alter table deal add constraint deal_r_status_ck check (r_status = '0' or r_status = '1');                  --review status(0 or 1)
alter table deal add constraint deal_o_status_ck check (o_status = '0' or o_status = '1');                  --order status(0 or 1)
alter table deal add constraint deal_pickup_status_ck check (pickup_status = '0' or pickup_status = '1');   --pickup status(0 or 1)

--create sequence: order number
create sequence deal_order_num_seq;

------------------------------ review ------------------------------
--create table: review
create table review (
review_number number(10),
buyer_number number(6),
seller_number number(6),
content varchar2(150),
write_date date default sysdate,
grade number(2)
);
--primary key
alter table review add constraint review_review_number_pk primary key (review_number);
--foreign key
alter table review add constraint review_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;
alter table review add constraint review_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;

--create sequence: review number
create sequence  review_review_num_seq;

------------------------------ bookmark ------------------------------
--create table: bookmark
create table bookmark (
bookmark_number number(10),
buyer_number number(10),
seller_number number(10)
);
--primary key
alter table bookmark add constraint bookmark_bookmark_number_pk primary key (bookmark_number);
--foreign key
alter table bookmark add constraint bookmark_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;
alter table bookmark add constraint bookmark_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;

--create sequence: bookmark number
create sequence  bookmark_bookmark_num_seq;

------------------------------ good ------------------------------
--create table: good
create table good (
good_number number(10),
mem_number number(6),
p_number number(5)
);
--primary key
alter table good add constraint good_good_id_pk primary key (good_number);
--foreign key
alter table good add constraint good_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;
alter table good add constraint good_p_number_fk foreign key (p_number) references product_info(p_number) on delete cascade; 

--create sequence: good number
create sequence  good_good_num_seq;

------------------------------ article ------------------------------
--create table: article
create table article (
article_num number(6),
mem_number number(6),
article_category varchar2(30),
article_title varchar2(90),
article_contents clob,
attachment varchar2(1),
create_date date,
views number(5),
comments number(5)
);
--primary key
alter table article add constraint article_article_num_pk primary key (article_num);
--foreign key
alter table article add constraint article_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;
--not null
alter table article modify article_category constraint article_article_category_nn not null;
alter table article modify article_title constraint article_article_title_nn not null;
alter table article modify article_contents constraint article_article_contents_nn not null;
alter table article modify attachment constraint article_attachment_nn not null;
--default
alter table article modify create_date date default sysdate;
alter table article modify views number(5) default 0;
alter table article modify comments number(5) default 0;

--create sequence: article number
create sequence article_article_num_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 999999
    nocycle
    nocache
    noorder;
    
------------------------------ comments ------------------------------    
--create table: comments
create table comments (
article_num number(6),
comment_group number(6),
comment_num number(6),
p_comment_num number(6),
step number(3),
comment_order number(3),
p_comment_nickname varchar2(18),
mem_number number(6),
comment_contents clob,
create_date date,
reply varchar2(1)
);
--primary key
alter table comments add constraint comments_comment_num_pk primary key (comment_num);
--foreign key
alter table comments add constraint comments_article_num_fk foreign key (article_num) references article(article_num) on delete cascade;
alter table comments add constraint comments_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;
alter table comments add constraint comments_p_comment_num_fk foreign key (p_comment_num) references comments(comment_num) on delete set null;
--not null
alter table comments modify comment_contents constraint comments_comment_contents_nn not null;
--default
alter table comments modify create_date date default sysdate;

--create sequence: comment number
create sequence comments_comment_num_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 999999
    nocycle
    nocache
    noorder;
    
------------------------------ uploadfile ------------------------------
--create table: uploadfile
create table uploadfile(
uploadfile_id number(10),
code varchar2(11),
rid varchar2(10),
store_filename varchar2(100),
upload_filename varchar2(100),
fsize varchar2(45),
ftype varchar2(100),
cdate timestamp default systimestamp,
udate timestamp default systimestamp
);
--primary key
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key (uploadfile_id);
--not null
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;

--create sequence: uploadfile id
create sequence uploadfile_uploadfile_id_seq;    
    
------------------------------ notice ------------------------------
--create table: notice
create table notice (
notice_id number(8),
title varchar2(150),
content clob,
write varchar2(30),
attachments varchar2(1),
count number(5) default 0,
udate timestamp default systimestamp
);
--primary key
alter table notice add constraint notice_notice_id_pk primary key (notice_id);

--create sequence: notice id
create sequence notice_notice_id_seq;