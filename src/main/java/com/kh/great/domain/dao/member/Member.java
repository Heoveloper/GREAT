package com.kh.great.domain.dao.member;

import com.kh.great.domain.dao.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Member {
    private Long memNumber;                     //varchar2(9)
    private String memType;                     //varchar2(15)
    private String memId;                       //varchar2(30)
    private String memPassword;                 //varchar2(18)
    private String memName;                     //varchar2(18)
    private String memNickname;                 //varchar2(18)
    private String memEmail;                    //varchar2(30)
    private String memBusinessnumber;           //varchar2(10)
    private String memStoreName;                //varchar2(45)
    private String memStorePhonenumber;         //varchar2(15)
    private String memStoreLocation;            //varchar2(150)
    private String memStoreLatitude;            //varchar2(15, 9)
    private String memStoreLongitude;           //varchar2(15, 9)
    private String memStoreIntroduce;           //varchar2(150)
    private String memStoreSns;                 //varchar2(150)
    private LocalDateTime memRegtime;           //date
    private LocalDateTime memLockExpiration;    //date
    private String memAdmin;                    //varchar2(3)
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
