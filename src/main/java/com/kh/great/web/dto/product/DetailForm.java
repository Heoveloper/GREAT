package com.kh.great.web.dto.product;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class DetailForm {
    private Long pNumber;

    private Long ownerNumber;

    private String memStoreName;

    private String pTitle;

    private String pName;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String deadlineTime;

    private String category;

    //private Integer totalCount;

    private Integer remainCount;

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

    private List<UploadFile> imageFiles;
}