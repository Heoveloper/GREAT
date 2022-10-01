package com.kh.great.domain.dao.product;

import java.util.List;

public interface ProductDAO {
    //상품등록
    Long save(Product product);

    //상품조회
    Product findByProductNum(Long pNum);

    // 상품 검색
    List<Product> select(String findStr);

    //상품변경
    int update(Long pNum, Product product);

    //상품목록
    List<Product> findAll();

    //상품삭제
    int deleteByProductNum(Long pNum);

    // 오늘 마감 상품 목록
    List<Product> today_deadline();

    //상품관리목록
    List<Product> pManage(Long ownerNumber);

    //판매 내역
    List<Product> saleList(Long ownerNumber);

    //------------------------------------
    // 상품 최신순 목록
    List <Product> recentList();

    // 상품 높은 할인순 목록
    List <Product> discountListDesc();

    // 상품 높은 가격순 목록
    List <Product> priceList();

    // 상품 높은 가격순 목록
    List <Product> priceListDesc();
}