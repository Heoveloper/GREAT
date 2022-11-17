package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;

@Data
public class BookmarkForm {
    private Long bookmarkNumber;

    private Long buyerNumber;

    private Long sellerNumber;

    //--------------------------------------------------

    private Member member;
}
