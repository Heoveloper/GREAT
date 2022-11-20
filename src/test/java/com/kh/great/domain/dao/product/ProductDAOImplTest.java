package com.kh.great.domain.dao;


import com.kh.great.domain.dao.product.ProductDAO;
import com.kh.great.domain.dao.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ProductDAOImplTest {
    @Autowired
    private ProductDAO productDAO;

    private static Product testProduct;

//    @Test
//    @DisplayName("상품 등록")
//    void save() {
//        Product newProduct = new Product(44l, "그레이트 베이커리", "맛 좀 보시오", "딸기 케이크", "카페/디저트");
//
//        testProduct = productDAO.save(newProduct);
//        Assertions.assertThat(testProduct.getP_number()).isEqualTo(newProduct.getP_number());
//        Assertions.assertThat(testProduct.getStore_name()).isEqualTo(newProduct.getStore_name());
//        Assertions.assertThat(testProduct.getP_title()).isEqualTo(newProduct.getP_title());
//        Assertions.assertThat(testProduct.getP_name()).isEqualTo(newProduct.getP_name());
//        Assertions.assertThat(testProduct.getP_category()).isEqualTo(newProduct.getP_category());
//    }

    @Test
    @DisplayName("상품 조회")
    void findByProductNum() {
        Long p_number= 11l;
        Product findedProduct = productDAO.findByProductNum(p_number);
        log.error("상품 번호 = {}", findedProduct.getPNumber());
        Assertions.assertThat(findedProduct.getPNumber()).isEqualTo(11);
        log.info("findedProduct = {}", findedProduct);
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteByProductNum() {
    }
}