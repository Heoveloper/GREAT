package com.kh.great.domain.dao.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;          //notice_id number(8),                  --공지사항번호
    @Length(min=1, max=20)
    private String title;           //title varchar2(90),                   --제목
    private String content;         //content clob,                         --내용
    private String write;           //write varchar2(30),                   --작성자(관리자)
    private String attachments;     //attachments varchar2(1),              --첨부파일유무
    private Long count;             //count number(5) default 0,            --조회수
    @DateTimeFormat(pattern = "yyyy.MM.dd.")
    private LocalDateTime udate;    //udate timestamp default systimestamp  --등록일자(수정일자)
}