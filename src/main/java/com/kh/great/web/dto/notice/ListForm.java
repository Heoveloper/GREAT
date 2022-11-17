package com.kh.great.web.dto.notice;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ListForm {
    private Long noticeId;

    @Length(min = 1, max = 50)
    private String title;

    private String attachments;

    private String write;

    @DateTimeFormat(pattern = "yyyy.MM.dd.")
    private Long count;

    private LocalDateTime udate;
}