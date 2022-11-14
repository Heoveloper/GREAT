package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindId {
    @NotBlank
    private String memName;

    @NotBlank
    private String memEmail;
}
