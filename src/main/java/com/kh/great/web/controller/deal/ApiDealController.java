package com.kh.great.web.controller.deal;

import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.svc.deal.DealSVC;
import com.kh.great.web.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/buy")
public class ApiDealController {
    private final DealSVC dealSVC;

    //구매 취소
    @DeleteMapping("/del/{orderNumber}")
    public ApiResponse<Deal> delBuy(@PathVariable("orderNumber") Long orderNumber) {
        dealSVC.deleteByOrderNumber(orderNumber);

        return ApiResponse.createApiResMsg("00", "성공", null);
    }
}