package com.kh.great.web.dto.article;

import lombok.Data;

@Data
public class ArticleSearchForm {
    private String searchCategory;

    private String searchKeyword;
}