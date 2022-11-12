package com.kh.great.domain.dao.product;

import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long pNumber;           //p_number number(30) not null,         --상품번호
    private Long ownerNumber;       //owner_number number(6) not null,      --판매자번호
    private String pTitle;          //p_title varchar2(300) not null,       --제목
    private String pName;           //p_name varchar2(30) not null,         --상품명
    @NotBlank
    private String deadlineTime;    //deadline_time date not null,          --마감일자
    private String category;        //category varchar2(17) not null,       --업종카테고리
    private Integer totalCount;     //total_count number(5) not null,       --총수량
    private Integer remainCount;    //remain_count number(5) not null,      --잔여수량
    private Integer normalPrice;    //normal_price number(8) not null,      --정상가
    private Integer salePrice;      //sale_price number(8) not null,        --할인가
    private Integer discountRate;   //discount_rate number(2) not null,     --할인율
    private String paymentOption;   //payment_option varchar2(32) not null, --결제방식
    private String detailInfo;      //detail_info clob,                     --상품설명
    @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime rDate;    //r_date date default sysdate not null, --등록일자
    private LocalDateTime uDate;    //u_date date default sysdate not null, --수정일자
    private Integer pStatus;        //p_status number(1) default 0          --판매상태

    private Member member;
    private Deal deal;
    private List<MultipartFile> files;
    private List<UploadFile> imageFiles;
}