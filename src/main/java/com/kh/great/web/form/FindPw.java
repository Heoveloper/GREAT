package com.kh.great.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindPw {
    @NotBlank
    private String memId;                       //varchar2(30)

    @NotBlank
    private String memEmail;                    //varchar2(30)
}
