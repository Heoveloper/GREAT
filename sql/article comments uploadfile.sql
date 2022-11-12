--drop table
drop table uploadfile;
drop table comments;
drop table article;

--drop sequence
drop sequence article_article_num_seq;
drop sequence comments_comment_num_seq;
drop sequence uploadfile_uploadfile_id_seq;


--create sequence
create sequence article_article_num_seq;        --�Խñ۹�ȣ
create sequence comments_comment_num_seq;       --��۹�ȣ
create sequence uploadfile_uploadfile_id_seq;   --���ε����Ϲ�ȣ

--create article table
create table article (
article_num         number(6),      --�Խñ۹�ȣ
mem_number          number(6),      --�ۼ��ڹ�ȣ
article_category    varchar2(30),   --ī�װ�
article_title       varchar2(90),   --����
article_contents    clob,           --����
attachment          varchar2(1),    --÷����������
create_date         date,           --�ۼ���
views               number(5),      --��ȸ��
comments            number(5)       --��ۼ�
);
--primary key
alter table article add constraint article_article_num_pk primary key (article_num);
--foreign key
alter table article add constraint article_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;  --�ۼ��ڹ�ȣ ȸ�����̺� ����
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
article_num         number(6),      --�Խñ۹�ȣ
comment_group       number(6),      --�׷�  
comment_num         number(6),      --��۹�ȣ
p_comment_num       number(6),      --�θ��۹�ȣ
step                number(3),      --�ܰ�
comment_order       number(3),      --����    
p_comment_nickname  varchar2(18),   --�θ��۴г���
mem_number          number(6),      --�ۼ��ڹ�ȣ
comment_contents    clob,           --����
create_date         date,           --�ۼ���
reply               varchar2(1)     --����
);
--primary key
alter table comments add constraint comments_comment_num_pk primary key (comment_num);
--foreign key
alter table comments add constraint comments_article_num_fk foreign key (article_num) references article(article_num) on delete cascade;        --�Խñ۹�ȣ �Խñ����̺� ����
alter table comments add constraint comments_mem_number_fk foreign key (mem_number) references member(mem_number) on delete cascade;            --�ۼ��ڹ�ȣ ȸ�����̺� ����
alter table comments add constraint comments_p_comment_num_fk foreign key (p_comment_num) references comments(comment_num) on delete set null;  --�θ��۹�ȣ ������̺� ����
--not null
alter table comments modify comment_contents constraint comments_comment_contents_nn not null;
--default
alter table comments modify create_date date default sysdate;

--create uploadfile table
create table uploadfile(
uploadfile_id   number(10),                         --���Ͼ��̵�
code            varchar2(11),                       --�з��ڵ�
rid             varchar2(10),                       --������ȣ
store_filename  varchar2(100),                      --�������ϸ�(������ �����Ǵ� ���ϸ�)
upload_filename varchar2(100),                      --���ε����ϸ�(������ ���ε��� ���ϸ�)
fsize           varchar2(45),                       --����ũ��
ftype           varchar2(100),                      --��������
cdate           timestamp default systimestamp,     --�������
udate           timestamp default systimestamp      --��������
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