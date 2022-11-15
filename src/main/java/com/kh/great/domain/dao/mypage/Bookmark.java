package com.kh.great.domain.dao.mypage;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;

@Data
public class Bookmark {
    private Long bookmarkNumber;    //bookmark_number number(10),   --즐겨찾기번호
    private Long buyerNumber;       //buyer_number number(10),      --구매자번호
    private Long sellerNumber;      //seller_number number(10)      --판매자번호

    private Member member;
}
