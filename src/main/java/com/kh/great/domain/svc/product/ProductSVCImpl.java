package com.kh.great.domain.svc.product;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.common.file.UploadFileSVC;
import com.kh.great.domain.dao.product.ProductDAO;
import com.kh.great.domain.dao.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductSVCImpl implements ProductSVC {
    private final ProductDAO productDAO;
    private final UploadFileSVC uploadFileSVC;
    private final FileUtils fileUtils;

    //상품 등록
    @Override
    public Long save(Product product) {
        return productDAO.save(product);
    }

    @Override
    public Long save(Product product, List<MultipartFile> files) {
        //1) 상품 등록
        Long num = save(product);
        //2) 첨부파일- 상품 설명
        uploadFileSVC.addFile(files, AttachCode.P0102, num);
        return num;
    }

    //상품 조회
    @Transactional(readOnly = true)
    @Override
    public Product findByProductNum(Long pNum) {
        return productDAO.findByProductNum(pNum);
    }

    // 상품 검색
    @Override
    public List<Product> select(String findStr) {
        return productDAO.select(findStr);
    }

    //상품 수정
    @Override
    public int update(Long pNum, Product product) {
        return productDAO.update(pNum, product);
    }
    //상품 목록
    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }
    //상품 삭제
    @Override
    public int deleteByProductNum(Long pNum) {
        return productDAO.deleteByProductNum(pNum);
    }

    // 오늘 마감 상품 목록
    @Override
    public List<Product> today_deadline() {
        return productDAO.today_deadline();
    }

    //상품 관리 목록
    @Override
    public List<Product> pManage(Long ownerNumber) {
        return productDAO.pManage(ownerNumber);
    }

    //판매 내역 목록
    @Override
    public List<Product> saleList(Long ownerNumber) {
        return productDAO.saleList(ownerNumber);
    }

    //------------------------------------------
    // 상품 최신순 목록
    @Override
    public List<Product> recentList() {
        return productDAO.recentList();
    }
    // 상품 높은 할인순 목록
    @Override
    public List<Product> discountListDesc() {
        return productDAO.discountListDesc();
    }
    // 상품 높은 가격순 목록
    @Override
    public List<Product> priceList() {
        return productDAO.priceList();
    }
    // 상품 높은 가격순 목록
    @Override
    public List<Product> priceListDesc() {
        return productDAO.priceListDesc();
    }
}