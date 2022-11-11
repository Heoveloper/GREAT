--drop table
drop table notice;

--drop sequence
drop sequence notice_notice_id_seq;


--create sequence
create sequence notice_notice_id_seq;   --공지사항번호

--create notice table
create table notice (
notice_id   number(8),
title       varchar2(150),
content     clob,
write       varchar2(30),
attachments varchar2(1),
count       number(5) default 0,
udate       timestamp default systimestamp
);
--primary key
alter table notice add constraint notice_notice_id_pk primary key (notice_id);