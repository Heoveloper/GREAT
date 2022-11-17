--drop table
drop table member;

--drop sequence
drop sequence member_mem_num_seq;


--create sequence
create sequence member_mem_num_seq  --ȸ����ȣ
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
    
--create member table
create table member (
mem_number number(9),               --ȸ����ȣ    
mem_type varchar2(15),              --ȸ������
mem_id varchar2(30),                --���̵�
mem_password varchar2(18),          --��й�ȣ
mem_name varchar2(18),              --�̸�
mem_nickname varchar2(18),          --�г���
mem_email varchar2(30),             --�̸���
mem_businessnumber varchar2(10),    --����ڹ�ȣ
mem_store_name varchar2(45),        --���Ը�
mem_store_phonenumber varchar2(15), --���Կ���ó
mem_store_location varchar2(150),   --�����ּ�
mem_store_latitude number(15, 9),   --��������
mem_store_longitude number(15, 9),  --���԰浵
mem_store_introduce varchar2(150),  --���ԼҰ�
mem_store_sns varchar2(150),        --����SNS
mem_regtime date,                   --��������
mem_lock_expiration date,           --�����Ⱓ
mem_admin varchar2(3)               --�����ڿ���
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