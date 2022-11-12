package com.kh.great.domain.dao.deal;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deal {
    private Long orderNumber;   //order_number number(10),          --주문번호
    private Long buyerNumber;   //buyer_number number(6),           --구매자번호
    private Long sellerNumber;  //seller_number number(6),          --판매자번호
    private Long pNumber;       //p_number number(30),              --상품번호
    private Long pCount;        //p_count number(3),                --상품수량
    private Long price;         //price number(6),                  --상품가격
    private String visittime;   //visittime date,                   --방문예정시각
    private Long buyType;       //buy_type number(1) default 0,     --결제유형
    private Long rStatus;       //r_status number(1) default 0,     --리뷰상태
    private Long oStatus;       //o_status number(1) default 0,     --주문상태
    private Date orderdate;     //orderdate date default sysdate,   --주문일자
    private Long pickupStatus;  //pickup_status number(1) default 0 --픽업상태

    private Member member;
    private Product product;
}