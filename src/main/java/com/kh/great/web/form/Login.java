package com.kh.great.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Login {
    @NotBlank
    private String memId;

    @NotBlank
//    @Size(min = 8, max = 15)
    private String memPassword;
}
