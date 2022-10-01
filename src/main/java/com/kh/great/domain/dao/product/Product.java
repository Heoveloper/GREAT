package com.kh.great.domain.dao.product;

import com.kh.great.domain.Deal;
import com.kh.great.domain.common.file.UploadFile;
import com.kh.great.domain.dao.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long pNumber;           //상품번호    P_NUMBER	NUMBER(6,0)
    private Long ownerNumber;       //점주고객번호    OWNER_NUMBER	NUMBER(6,0)
    private String pTitle;          //상품 제목    P_TITLE	VARCHAR2(90 BYTE)
    private String pName;           //상품명    P_NAME	VARCHAR2(60 BYTE)
    @NotBlank
    private String deadlineTime;    //마감일자    DEADLINE_TIME	DATE
    private String category;      //업종카테고리    CATEGORY	VARCHAR2(17 BYTE)
    private Integer totalCount;     //총수량    TOTAL_COUNT	NUMBER(5,0)
    private Integer remainCount;    //남은 수량    REMAIN_COUNT	NUMBER(5,0)
    private Integer normalPrice;    //정상가    NORMAL_PRICE	NUMBER(8,0)
    private Integer salePrice;      //할인가    SALE_PRICE	NUMBER(8,0)
    private Integer discountRate;   //할인율    DISCOUNT_RATE	NUMBER(2,0)
    private String paymentOption;  //결제방식    PAYMENT_OPTION	VARCHAR2 (32 BYTE)
    private String detailInfo;     //상품설명    DETAIL_INFO	VARCHAR2 (4000 BYTE)

    private LocalDateTime rDate;    //등록일    R_DATE DATE DEFAULT SYSDATE
    private LocalDateTime uDate;    //수정일    U_DATE	DATE DEFAULT SYSDATE
    private Integer pStatus;        //판매상태    P_STATUS	NUMBER(1,0)

    private Member member;
    private Deal deal;

    private List<UploadFile> imageFiles;
}