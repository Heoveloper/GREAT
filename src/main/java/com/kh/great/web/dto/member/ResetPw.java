package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPw {
    @NotBlank
    private String memId;

    @NotBlank
    private String memPassword;

    @NotBlank
    private String memPasswordCheck;
}