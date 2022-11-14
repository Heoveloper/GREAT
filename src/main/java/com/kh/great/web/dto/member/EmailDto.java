package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailDto {
    @NotEmpty(message = "이메일을 입력해주세요.")
    public String email;

    @NotEmpty
    public String code;
}
