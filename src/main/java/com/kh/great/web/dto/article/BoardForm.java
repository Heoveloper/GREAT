package com.kh.great.web.dto.article;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class BoardForm {
    private Long articleNum;

    private Long memNumber;

    private String articleCategory;

    @Length(min = 1, max = 50)
    private String articleTitle;

    @NotBlank
    private String articleContents;

    private String attachment;

    @DateTimeFormat(pattern = "yyyy.MM.dd.")
    private LocalDateTime createDate;

    private Long views;

    private Long comments;

    //--------------------------------------------------

    private Member member;
}