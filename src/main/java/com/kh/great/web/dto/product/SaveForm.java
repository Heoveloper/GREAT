package com.kh.great.web.dto.product;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SaveForm {
    private Long pNumber;

    private Long ownerNumber;

    private String pTitle;

    private String pName;

    private String deadlineTime;

    private String category;

    private Integer totalCount;

    //private Integer remainCount;

    private Integer normalPrice;

    private Integer salePrice;

    private Integer discountRate;

    private String paymentOption;

    private String detailInfo;

    //private LocalDateTime rDate;

    //private LocalDateTime uDate;

    //private Integer pStatus;

    //--------------------------------------------------

    private Member member;

    private List<MultipartFile> files;  //파일 첨부 - 여러건
}