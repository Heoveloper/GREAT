package com.kh.great.web.dto.article;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleAddForm {
    private Long memNumber;

    private String articleCategory;

    @Length(min = 1, max = 50)
    private String articleTitle;

    @NotBlank
    private String articleContents;

    private String attachment;
}