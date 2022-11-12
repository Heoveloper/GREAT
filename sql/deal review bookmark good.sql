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
create sequence deal_order_num_seq;         --�ֹ���ȣ
create sequence review_review_num_seq;      --�����ȣ
create sequence bookmark_bookmark_num_seq;  --���ã���ȣ
create sequence good_good_num_seq;          --���ƿ��ȣ

--create deal table
create table deal (
order_number    number(10),             --�ֹ���ȣ    
buyer_number    number(6),              --�����ڹ�ȣ
seller_number   number(6),              --�Ǹ��ڹ�ȣ
p_number        number(30),             --��ǰ��ȣ
p_count         number(3),              --��ǰ����
price           number(6),              --��ǰ����
visittime       date,                   --�湮�����ð�
buy_type        number(1),              --��������
r_status        number(1) default 0,    --�������
o_status        number(1) default 0,    --�ֹ�����
orderdate       date default sysdate,   --�ֹ�����
pickup_status   number(1) default 0     --�Ⱦ�����
);
--primary key
alter table deal add constraint deal_order_number_pk primary key (order_number);
--foreign key
alter table deal add constraint deal_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;    --�����ڹ�ȣ ȸ�����̺� ����
alter table deal add constraint deal_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;  --�Ǹ��ڹ�ȣ ȸ�����̺� ����
alter table deal add constraint deal_p_number_fk foreign key (p_number) references product_info(p_number) on delete cascade;        --��ǰ��ȣ ��ǰ���̺� ����
--check
alter table deal add constraint deal_buy_type_ck check (buy_type = '0' or buy_type = '1');                  --��������(0 or 1)
alter table deal add constraint deal_r_status_ck check (r_status = '0' or r_status = '1');                  --�������(0 or 1)
alter table deal add constraint deal_o_status_ck check (o_status = '0' or o_status = '1');                  --�ֹ�����(0 or 1)
alter table deal add constraint deal_pickup_status_ck check (pickup_status = '0' or pickup_status = '1');   --�Ⱦ�����(0 or 1)

--create review table
create table review (
review_number   number(10),             --�����ȣ
buyer_number    number(6),              --�����ڹ�ȣ(�����ۼ��ڹ�ȣ)
seller_number   number(6),              --�Ǹ��ڹ�ȣ
content         varchar2(150),          --����
write_date      date default sysdate,   --�ۼ���
grade           number(2)               --����
);
--primary key
alter table review add constraint review_review_number_pk primary key (review_number);
--foreign key
alter table review add constraint review_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;    --�����ڹ�ȣ ȸ�����̺� ����
alter table review add constraint review_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;  --�Ǹ��ڹ�ȣ ȸ�����̺� ����

--create bookmark table
create table bookmark (
bookmark_number number(10),     --���ã���ȣ
buyer_number    number(10),     --�����ڹ�ȣ
seller_number   number(10)      --�Ǹ��ڹ�ȣ
);
--primary key
alter table bookmark add constraint bookmark_bookmark_number_pk primary key (bookmark_number);
--foreign key
alter table bookmark add constraint bookmark_buyer_number_fk foreign key (buyer_number) references member(mem_number) on delete cascade;    --�����ڹ�ȣ ȸ�����̺� ����
alter table bookmark add constraint bookmark_seller_number_fk foreign key (seller_number) references member(mem_number) on delete cascade;  --�Ǹ��ڹ�ȣ ȸ�����̺� ����

--create good table
create table good (
good_number number(10),     --���ƿ��ȣ
mem_number  number(6),      --ȸ����ȣ
p_number    number(5)       --��ǰ��ȣ
);
--primary key
alter table good add constraint good_good_id_pk primary key (good_number);
--foreign key
alter table good add constraint good_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;    --ȸ����ȣ ȸ�����̺� ����
alter table good add constraint good_p_number_fk foreign key (p_number) references product_info(p_number) on delete cascade;    --��ǰ��ȣ ��ǰ���̺� ����