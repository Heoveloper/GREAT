package com.kh.great.web.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {
    private Long memNumber;         //회원번호

    private String memType;         //회원유형

    private String memId;           //아이디

    private String memNickname;     //닉네임

    private String memStoreName;    //가게명

    private String memAdmin;        //관리자여부
}
