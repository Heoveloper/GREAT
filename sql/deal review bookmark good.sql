--drop table
drop table bookmark;
drop table good;
drop table review;
drop table deal;

--drop sequence
drop sequence deal_order_num_seq;
drop sequence review_review_num_seq;
drop sequence good_good_num_seq;
drop sequence bookmark_bookmark_num_seq;


--create sequence
create sequence deal_order_num_seq;         --주문번호
create sequence review_review_num_seq;      --리뷰번호
create sequence bookmark_bookmark_num_seq;  --즐겨찾기번호
create sequence good_good_num_seq;          --좋아요번호

--create deal table
create table deal (
order_number    number(10),             --주문번호    
buyer_number    number(6),              --구매자번호
seller_number   number(6),              --판매자번호
p_number        number(30),             --상품번호
p_count         number(3),              --상품수량
price           number(6),              --상품가격
visittime       date,                   --방문예정시각
buy_type        number(1),              --결제유형
r_status        number(1) default 0,    --리뷰상태
o_status        number(1) default 0,    --주문상태
orderdate       date default sysdate,   --주문일자
pickup_status   number(1) default 0     --픽업상태
);
--primary key
alter table deal add constraint deal_order_number_pk primary key (order_number);
--foreign key
alter table deal add constraint deal_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;    --구매자번호 회원테이블 참조
alter table deal add constraint deal_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;  --판매자번호 회원테이블 참조
alter table deal add constraint deal_p_number_fk foreign key (p_number) references product_info(p_number) on delete cascade;        --상품번호 상품테이블 참조
--check
alter table deal add constraint deal_buy_type_ck check (buy_type = '0' or buy_type = '1');                  --결제유형(0 or 1)
alter table deal add constraint deal_r_status_ck check (r_status = '0' or r_status = '1');                  --리뷰상태(0 or 1)
alter table deal add constraint deal_o_status_ck check (o_status = '0' or o_status = '1');                  --주문상태(0 or 1)
alter table deal add constraint deal_pickup_status_ck check (pickup_status = '0' or pickup_status = '1');   --픽업상태(0 or 1)

--create review table
create table review (
review_number   number(10),             --리뷰번호
buyer_number    number(6),              --구매자번호(리뷰작성자번호)
seller_number   number(6),              --판매자번호
content         varchar2(150),          --내용
write_date      date default sysdate,   --작성일
grade           number(2)               --평점
);
--primary key
alter table review add constraint review_review_number_pk primary key (review_number);
--foreign key
alter table review add constraint review_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;    --구매자번호 회원테이블 참조
alter table review add constraint review_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;  --판매자번호 회원테이블 참조

--create bookmark table
create table bookmark (
bookmark_number number(10),     --즐겨찾기번호
buyer_number    number(10),     --구매자번호
seller_number   number(10)      --판매자번호
);
--primary key
alter table bookmark add constraint bookmark_bookmark_number_pk primary key (bookmark_number);
--foreign key
alter table bookmark add constraint bookmark_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;    --구매자번호 회원테이블 참조
alter table bookmark add constraint bookmark_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;  --판매자번호 회원테이블 참조

--create good table
create table good (
good_number number(10),     --좋아요번호
mem_number  number(6),      --회원번호
p_number    number(5)       --상품번호
);
--primary key
alter table good add constraint good_good_id_pk primary key (good_number);
--foreign key
alter table good add constraint good_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;    --회원번호 회원테이블 참조
alter table good add constraint good_p_number_fk foreign key (p_number) references product_info(p_number) on delete cascade;    --상품번호 상품테이블 참조