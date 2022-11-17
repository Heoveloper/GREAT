package com.kh.great.web.dto.deal;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddForm {
    private Long orderNumber;

    private Long buyerNumber;

    private Long sellerNumber;

    private Long pNumber;

    private Long pCount;

    private Long price;

    private String visittime;

    private Long buyType;

    private Long rStatus;

    private Long oStatus;

    private Date orderdate;

    private Long pickupStatus;

    //--------------------------------------------------

    private Product product;

    private Member member;

    private Long ownerNumber;

    private String memStoreName;

    private String pTitle;

    private String pName;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String deadlineTime;

    private String pCategory;

    private Integer totalCount;

    private Integer remainCount;

    private Integer normalPrice;

    private Integer salePrice;

    private Integer discountRate;

    private String paymentOption;

    private String detailInfo;

    private MultipartFile file;

    private List<MultipartFile> files;

    private List<UploadFile> imageFiles;
}