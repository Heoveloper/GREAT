package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Info {
    private Long memNumber;

    private String memType;

    @NotBlank
    private String memId;

    @NotBlank
    private String memPassword;

    @NotBlank
    private String memPasswordCheck;

    @NotBlank
    private String memName;

    @NotBlank
    private String memNickname;

    @NotBlank
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

    //탈퇴 시 입력한 확인 비밀번호
    private String exitPwc;
}
