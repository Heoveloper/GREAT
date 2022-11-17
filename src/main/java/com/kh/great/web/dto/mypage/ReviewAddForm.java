package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewAddForm {
    private Long reviewNumber;

    private Long buyerNumber;

    private Long sellerNumber;

    private String content;

    private LocalDateTime writeDate;

    private Long grade;

    //--------------------------------------------------

    private Long profileNumber;

    private Member member;
}