package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Login {
    @NotBlank
    private String memId;

    //@Size(min = 8, max = 15)
    @NotBlank
    private String memPassword;
}
