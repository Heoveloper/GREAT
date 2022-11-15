package com.kh.great.domain.dao.mypage;

import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Review {
    private Long reviewNumber;          //review_number number(10),         --리뷰번호
    private Long buyerNumber;           //buyer_number number(6),           --구매자번호(리뷰작성자번호)
    private Long sellerNumber;          //seller_number number(6),          --판매자번호
    private String content;             //content varchar2(150),            --내용
    private LocalDateTime writeDate;    //write_date date default sysdate,  --작성일
    private Long grade;                 //grade number(2)                   --평점

    private Long profileNumber;
    private Member member;
    private Product product;
    private Deal deal;
}
