package com.kh.great.web.api.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindId {
    @NotBlank
    private String memName;                       //varchar2(30)

    @NotBlank
    private String memEmail;                     //varchar2(18)
}
