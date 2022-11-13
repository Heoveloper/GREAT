package com.kh.great.domain.svc.product;

import com.kh.great.domain.dao.product.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductSVC {
    //상품 등록
    Long save(Product product);

    //상품 등록 (업로드 파일 포함)
    Long save(Product product, List<MultipartFile> files);

    //상품 조회
    Product findByProductNum(Long pNum);

    //상품 수정
    int update(Long pNum, Product product);

    //상품 수정 (업로드 파일 포함)
    int update(Long pNum, Product product, List<MultipartFile> files);

    //상품 삭제
    int deleteByProductNum(Long pNum);

    //상품 목록
    List<Product> findAll();

    //오늘의 마감 할인 상품 목록
    List<Product> today_deadline();

    //--------------------------------------------------
    //상품 관리
    List<Product> manage(Long ownerNumber);

    //상품 관리 CSR
    List<Product> pManage(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam("sell_status") Integer sell_status, @RequestParam("history_start_date") String history_start_date, @RequestParam("history_end_date") String history_end_date);

    //상품 관리: 상품 판매 상태 변경
    int pManage_status_update(Long pNum, Integer pStatus);

    //--------------------------------------------------
    //판매 내역
    List<Product> saleList(Long ownerNumber);

    //판매 내역 CSR
    List<Product> pSaleList(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam("pickUp_status") Integer pickUp_status, @RequestParam("history_start_date") String history_start_date, @RequestParam("history_end_date") String history_end_date);

    //판매 내역: 상품 픽업 상태 변경
    int pickUP_status_update(Long pNum, Integer pickStatus);

    //--------------------------------------------------
    //상품 최신순 목록
    List <Product> recentList(@RequestParam Map<String, Object> allParameters);

    //상품 높은 할인순 목록
    List <Product> discountListDesc(@RequestParam Map<String, Object> allParameters);

    //상품 낮은 가격순 목록
    List <Product> priceList(@RequestParam Map<String, Object> allParameters);

    //상품 높은 가격순 목록
    List <Product> priceListDesc(@RequestParam Map<String, Object> allParameters);

    //--------------------------------------------------
    //상품 검색
    List<Product> search(@RequestParam("searchKeyword") String searchKeyword);
}
