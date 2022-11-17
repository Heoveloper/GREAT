--drop table
drop table notice;

--drop sequence
drop sequence notice_notice_id_seq;


--create sequence
create sequence notice_notice_id_seq;   --공지사항번호

--create notice table
create table notice (
notice_id   number(8),                      --공지사항번호
title       varchar2(90),                   --제목
content     clob,                           --내용
write       varchar2(30),                   --작성자(관리자)
attachments varchar2(1),                    --첨부파일유무
count       number(5) default 0,            --조회수
udate       timestamp default systimestamp  --등록일자(수정일자)
);
--primary key
alter table notice add constraint notice_notice_id_pk primary key (notice_id);