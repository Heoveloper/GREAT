package com.kh.great.domain.dao.member;

import com.kh.great.domain.dao.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Member {
    private Long memNumber;                     //mem_number number(9),                 --회원번호
    private String memType;                     //mem_type varchar2(15),                --회원유형
    private String memId;                       //mem_id varchar2(30),                  --아이디
    private String memPassword;                 //mem_password varchar2(18),            --비밀번호
    private String memName;                     //mem_name varchar2(18),                --이름
    private String memNickname;                 //mem_nickname varchar2(18),            --닉네임
    private String memEmail;                    //mem_email varchar2(30),               --이메일
    private String memBusinessnumber;           //mem_businessnumber varchar2(10),      --사업자번호
    private String memStoreName;                //mem_store_name varchar2(45),          --가게명
    private String memStorePhonenumber;         //mem_store_phonenumber varchar2(15),   --가게연락처
    private String memStoreLocation;            //mem_store_location varchar2(150),     --가게주소
    private String memStoreLatitude;            //mem_store_latitude number(15, 9),     --가게위도
    private String memStoreLongitude;           //mem_store_longitude number(15, 9),    --가게경도
    private String memStoreIntroduce;           //mem_store_introduce varchar2(150),    --가게소개
    private String memStoreSns;                 //mem_store_sns varchar2(150),          --가게SNS
    private LocalDateTime memRegtime;           //mem_regtime date,                     --가입일자
    private LocalDateTime memLockExpiration;    //mem_lock_expiration date,             --정지기간
    private String memAdmin;                    //mem_admin varchar2(3)                 --관리자여부

    private Product product;

    public Member(String memType, String memId, String memPassword, String memName, String memNickname, String memEmail, String memBusinessnumber, String memStoreName, String memStroePhonenumber, String memStoreLocation, String memStoreLatitude, String memStoreLongitude, String memStoreIntroduce, String memStoreSns, LocalDateTime memRegtime, LocalDateTime memLockExpiration, String memAdmin) {
        this.memType = memType;
        this.memId = memId;
        this.memPassword = memPassword;
        this.memName = memName;
        this.memNickname = memNickname;
        this.memEmail = memEmail;
        this.memBusinessnumber = memBusinessnumber;
        this.memStoreName = memStoreName;
        this.memStorePhonenumber = memStroePhonenumber;
        this.memStoreLocation = memStoreLocation;
        this.memStoreLatitude = memStoreLatitude;
        this.memStoreLongitude = memStoreLongitude;
        this.memStoreIntroduce = memStoreIntroduce;
        this.memStoreSns = memStoreSns;
        this.memRegtime = memRegtime;
        this.memLockExpiration = memLockExpiration;
        this.memAdmin = memAdmin;
    }
}
