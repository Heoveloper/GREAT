--drop table
drop table uploadfile;
drop table comments;
drop table article;

--drop sequence
drop sequence article_article_num_seq;
drop sequence comments_comment_num_seq;
drop sequence uploadfile_uploadfile_id_seq;


--create sequence
create sequence article_article_num_seq;        --게시글번호
create sequence comments_comment_num_seq;       --댓글번호
create sequence uploadfile_uploadfile_id_seq;   --업로드파일번호

--create article table
create table article (
article_num         number(6),      --게시글번호
mem_number          number(6),      --작성자번호
article_category    varchar2(30),   --카테고리
article_title       varchar2(90),   --제목
article_contents    clob,           --내용
attachment          varchar2(1),    --첨부파일유무
create_date         date,           --등록일자
views               number(5),      --조회수
comments            number(5)       --댓글수
);
--primary key
alter table article add constraint article_article_num_pk primary key (article_num);
--foreign key
alter table article add constraint article_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;  --작성자번호 회원테이블 참조
--not null
alter table article modify article_category constraint article_article_category_nn not null;
alter table article modify article_title constraint article_article_title_nn not null;
alter table article modify article_contents constraint article_article_contents_nn not null;
alter table article modify attachment constraint article_attachment_nn not null;
--default
alter table article modify create_date date default sysdate;
alter table article modify views number(5) default 0;
alter table article modify comments number(5) default 0;

--create comments table
create table comments (
article_num         number(6),      --게시글번호
comment_group       number(6),      --그룹  
comment_num         number(6),      --댓글번호
p_comment_num       number(6),      --부모댓글번호
step                number(3),      --단계
comment_order       number(3),      --순서    
p_comment_nickname  varchar2(18),   --부모댓글닉네임
mem_number          number(6),      --작성자번호
comment_contents    clob,           --내용
create_date         date,           --등록일자
reply               varchar2(1)     --대댓글
);
--primary key
alter table comments add constraint comments_comment_num_pk primary key (comment_num);
--foreign key
alter table comments add constraint comments_article_num_fk foreign key (article_num) references article(article_num) on delete cascade;        --게시글번호 게시글테이블 참조
alter table comments add constraint comments_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;            --작성자번호 회원테이블 참조
alter table comments add constraint comments_p_comment_num_fk foreign key (p_comment_num) references comments(comment_num) on delete set null;  --부모댓글번호 댓글테이블 참조
--not null
alter table comments modify comment_contents constraint comments_comment_contents_nn not null;
--default
alter table comments modify create_date date default sysdate;

--create uploadfile table
create table uploadfile(
uploadfile_id   number(10),                         --파일아이디
code            varchar2(11),                       --분류코드
rid             varchar2(10),                       --참조번호
store_filename  varchar2(100),                      --저장파일명(서버에 보관되는 파일명)
upload_filename varchar2(100),                      --업로드파일명(유저가 업로드한 파일명)
fsize           varchar2(45),                       --파일크기
ftype           varchar2(100),                      --파일유형
cdate           timestamp default systimestamp,     --등록일자
udate           timestamp default systimestamp      --수정일자
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