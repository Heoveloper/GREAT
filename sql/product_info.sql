drop table product_info;
--상품 테이블 생성
create table product_info(
     P_NUMBER NUMBER(30, 0) not null
,    OWNER_NUMBER NUMBER(6, 0) not null
,    P_TITLE VARCHAR2(300 BYTE) not null
,    P_NAME VARCHAR2(30 BYTE) not null
,    DEADLINE_TIME DATE not null
,    CATEGORY VARCHAR2(17 BYTE) not null
,    TOTAL_COUNT NUMBER(5, 0) not null
,    REMAIN_COUNT NUMBER(5, 0) not null
,    NORMAL_PRICE NUMBER(8, 0) not null
,    SALE_PRICE NUMBER(8, 0) not null
,    DISCOUNT_RATE NUMBER(2, 0) not null
,    PAYMENT_OPTION VARCHAR2(32 BYTE) not null
,    DETAIL_INFO VARCHAR2(4000 BYTE) not null
,    R_DATE DATE default sysdate not null
,    U_DATE DATE default sysdate not null
,    P_STATUS NUMBER(1, 0) default 0
);

--기본키 설정
ALTER TABLE PRODUCT_INFO ADD CONSTRAINT product_info_p_id_pk PRIMARY key(p_NUMBER);
 --외래키 설정
 alter table PRODUCT_INFO ADD CONSTRAINT product_info_p_num_fk FOREIGN key(OWNER_NUMBER) REFERENCES member(mem_number) on delete cascade;

drop sequence PRODUCT_P_NUMBER_SEQ;
-- 상품번호 시퀀스 생성
create sequence PRODUCT_P_NUMBER_SEQ;

-- 판매상태(0,1만 선택 가능)
ALTER TABLE PRODUCT_INFO ADD CONSTRAINT porduct_info_p_status_ck CHECK (P_STATUS =0 OR P_STATUS =1);
