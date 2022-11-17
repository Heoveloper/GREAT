package com.kh.great.web.dto.notice;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DetailForm {
    private Long noticeId;

    @Length(min = 1, max = 50)
    private String title;

    private String content;

    private String write;

    private String attachments;

    @DateTimeFormat(pattern = "yyyy.MM.dd.HH.mm")
    private LocalDateTime udate;
}