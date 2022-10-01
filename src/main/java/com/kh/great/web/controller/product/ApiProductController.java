package com.kh.great.web.controller.product;

import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.web.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductController {
    private final ProductSVC productSVC;

    // 최신순 목록 GET /api/zonning/recentList
    @GetMapping("/zonning/recentList")
    public ApiResponse<List<Product>> recentList(){
        List<Product> list = productSVC.recentList();

        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 높은 할인순순 목록 GET /api/zonning/discountListDesc
    @GetMapping("/zonning/discountListDesc")
    public ApiResponse<List<Product>> discountListDesc(){
        List<Product> list = productSVC.discountListDesc();

        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 낮은 가격순 목록 GET /api/zonning/priceList
    @GetMapping("/zonning/priceList")
    public ApiResponse<List<Product>> priceList(){
        List<Product> list = productSVC.priceList();

        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 높은 가격순 목록 GET /api/zonning/priceListDesc
    @GetMapping("/zonning/priceListDesc")
    public ApiResponse<List<Product>> priceListDesc(){
        List<Product> list = productSVC.priceListDesc();

        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 날짜로 상품관리 목록 조회
    @GetMapping("/manage/{ownerNumber}")
    public ApiResponse<List<Product>> manageByDate(@PathVariable("ownerNumber") Long ownerNumber, Model model) {
        List<Product> list = productSVC.pManage(ownerNumber);
        model.addAttribute("list", list);

        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }
}
