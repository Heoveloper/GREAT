package com.kh.great.web.form;

import lombok.Data;

@Data
public class InfoChk {
    private Long memNumber;                      //varchar2(9)
    private String memType;                     //varchar2(15)
    private String memId;                       //varchar2(30)
    private String memPassword;                 //varchar2(18)
}
