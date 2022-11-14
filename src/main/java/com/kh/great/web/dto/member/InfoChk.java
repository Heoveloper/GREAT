package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InfoChk {
    @NotBlank
    private String memPassword;
}