package com.kh.great.web.dto.deal;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditReq {
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

    private Member member;

    private Product product;
}