package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Join {
    private Long memNumber;

    private String memType;

    @NotBlank(message = "필수 입력항목입니다.")
    private String memId;

    //@Size(min = 8, max = 15)
    @NotBlank(message = "필수 입력항목입니다.")
    private String memPassword;

    @NotBlank(message = "필수 입력항목입니다.")
    private String memPasswordCheck;

    @NotBlank(message = "필수 입력항목입니다.")
    private String memName;

    @NotBlank(message = "필수 입력항목입니다.")
    private String memNickname;

    //@Email(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}", message = "이메일 형식이 아닙니다.")
    @Email(regexp = ".+@.+\\..+", message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "필수 입력항목입니다.")
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
