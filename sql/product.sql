--drop table
drop table product_info;

--drop sequence
drop sequence product_p_num_seq;


--create sequence
create sequence product_p_num_seq;  --��ǰ��ȣ

--create product info table
create table product_info (
p_number        number(30) not null,            --��ǰ��ȣ
owner_number    number(6) not null,             --�Ǹ��ڹ�ȣ
p_title         varchar2(300) not null,         --����
p_name          varchar2(30) not null,          --��ǰ��
deadline_time   date not null,                  --��������
category        varchar2(17) not null,          --����ī�װ�
total_count     number(5) not null,             --�Ѽ���
remain_count    number(5) not null,             --�ܿ�����
normal_price    number(8) not null,             --����
sale_price      number(8) not null,             --���ΰ�
discount_rate   number(2) not null,             --������
payment_option  varchar2(32) not null,          --�������
detail_info     clob,                           --��ǰ����
r_date          date default sysdate not null,  --�������
u_date          date default sysdate not null,  --��������
p_status        number(1) default 0             --�ǸŻ���
);
--primary key
alter table product_info add constraint product_info_p_id_pk primary key (p_number);
--foreign key
alter table product_info add constraint product_info_p_num_fk foreign key (owner_number) references member(mem_number) on delete cascade;   --����ȸ����ȣ ȸ�����̺� ����
--check
alter table product_info add constraint porduct_info_p_status_ck check (p_status = 0 or p_status = 1);  --�ǸŻ���(0 or 1)