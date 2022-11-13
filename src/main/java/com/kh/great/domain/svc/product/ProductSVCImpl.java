package com.kh.great.domain.svc.product;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.dao.product.ProductDAO;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    //상품 등록 (업로드 파일 포함)
    @Override
    public Long save(Product product, List<MultipartFile> files) {
        //상품 등록
        Long num = save(product);
        //업로드 파일 등록
        uploadFileSVC.addFile(files, AttachCode.P0102, num);

        return num;
    }

    //상품 조회
    @Transactional(readOnly = true)
    @Override
    public Product findByProductNum(Long pNum) {

        return productDAO.findByProductNum(pNum);
    }

    //상품 수정
    @Override
    public int update(Long pNum, Product product) {

        return productDAO.update(pNum, product);
    }

    //상품 수정 (업로드 파일 포함)
    @Override
    public int update(Long pNum, Product product, List<MultipartFile> files) {
        //상품 수정
        int affectedRow = update(pNum, product);
        //업로드 파일 등록
        uploadFileSVC.addFile(files, AttachCode.P0102, pNum);

        return affectedRow;
    }

    //상품 삭제
    @Override
    public int deleteByProductNum(Long pNum) {

        return productDAO.deleteByProductNum(pNum);
    }

    //상품 목록
    @Override
    public List<Product> findAll() {

        return productDAO.findAll();
    }

    //오늘의 마감 할인 상품 목록
    @Override
    public List<Product> today_deadline() {

        return productDAO.today_deadline();
    }

    //--------------------------------------------------
    //상품 관리
    @Override
    public List<Product> manage(Long ownerNumber) {

        return productDAO.manage(ownerNumber);
    }

    //상품 관리 CSR
    @Override
    public List<Product> pManage(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam("sell_status") Integer sell_status, @RequestParam("history_start_date") String history_start_date, @RequestParam("history_end_date") String history_end_date) {

        return productDAO.pManage(ownerNumber, sell_status, history_start_date, history_end_date);
    }

    //상품 관리: 상품 판매 상태 변경
    @Override
    public int pManage_status_update(Long pNum, Integer pStatus) {

        return productDAO.pManage_status_update(pNum, pStatus);
    }

    //--------------------------------------------------
    //판매 내역
    @Override
    public List<Product> saleList(Long ownerNumber) {

        return productDAO.saleList(ownerNumber);
    }

    //판매 내역 CSR
    @Override
    public List<Product> pSaleList(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam("pickUp_status") Integer pickUp_status, @RequestParam("history_start_date") String history_start_date, @RequestParam("history_end_date") String history_end_date) {

        return productDAO.pSaleList(ownerNumber, pickUp_status, history_start_date, history_end_date);
    }

    //판매 내역: 상품 픽업 상태 변경
    @Override
    public int pickUP_status_update(Long pNum, Integer pickStatus) {

        return productDAO.pickUP_status_update(pNum, pickStatus);
    }

    //--------------------------------------------------
    //상품 최신순 목록
    @Override
    public List<Product> recentList(@RequestParam Map<String, Object> allParameters) {

        return productDAO.recentList(allParameters);
    }

    //상품 높은 할인순 목록
    @Override
    public List<Product> discountListDesc(@RequestParam Map<String, Object> allParameters) {

        return productDAO.discountListDesc(allParameters);
    }

    //상품 낮은 가격순 목록
    @Override
    public List<Product> priceList(@RequestParam Map<String, Object> allParameters) {

        return productDAO.priceList(allParameters);
    }

    //상품 높은 가격순 목록
    @Override
    public List<Product> priceListDesc(@RequestParam Map<String, Object> allParameters) {

        return productDAO.priceListDesc(allParameters);
    }

    //--------------------------------------------------
    //상품 검색
    @Override
    public List<Product> search(@RequestParam("searchKeyword") String searchKeyword) {

        return productDAO.search(searchKeyword);
    }
}