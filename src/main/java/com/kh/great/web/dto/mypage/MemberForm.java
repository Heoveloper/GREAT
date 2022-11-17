package com.kh.great.web.dto.mypage;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberForm {
    private Long memNumber;

    private String memType;

    private String memId;

    private String memPassword;

    private String memName;

    private String memNickname;

    private String memEmail;

    private String memBusinessnumber;

    private String memStoreName;

    private String memStorePhonenumber;

    private String memStoreLocation;

    private String memStoreLatitude;

    private String memStoreLongitude;

    private String memStoreIntroduce;

    private String memStoreSns;

    private LocalDateTime memRegtime;

    private LocalDateTime memLockExpiration;

    private String memAdmin;
}