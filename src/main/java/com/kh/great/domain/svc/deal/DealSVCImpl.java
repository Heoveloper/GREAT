package com.kh.great.domain.svc.deal;

import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.dao.deal.DealDAO;
import com.kh.great.domain.dao.product.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DealSVCImpl implements DealSVC {
    private final DealDAO dealDAO;
    private final ProductDAO productDAO;

    //구매 등록
    @Override
    public Deal add(Deal deal) {
        return dealDAO.add(deal);
    }

    //구매 조회 (조회 by 회원번호)
    @Override
    public List<Deal> findByMemberNumber(Long memNumber) {
        return dealDAO.findByMemberNumber(memNumber);
    }

    //구매 조회 (조회 by 주문번호)
    @Override
    public Optional<Deal> findByOrderNumber(Long orderNumber) {
        return dealDAO.findByOrderNumber(orderNumber);
    }

    //구매 시 상품 개수 감소
    @Override
    public int update(Long pNumber, Deal deal) {
        return dealDAO.update(pNumber,deal);
    }

    //구매 취소
    @Override
    public int deleteByOrderNumber(Long orderNumber) {
        Optional<Deal> foundOrder = dealDAO.findByOrderNumber(orderNumber);
        log.info("foundOrder : {} ", foundOrder);
        dealDAO.delUpdate(foundOrder.get().getPNumber(),foundOrder.get());

        int affectedRow = dealDAO.deleteByOrderNumber(orderNumber);
        return affectedRow;
    }

    //구매 취소 시 상품 개수 증가
    //@Override
    //public int delUpdate(Long pNumber, Deal deal) {
    //    return dealDAO.delUpdate(pNumber, deal);
    //}

    //남은 수량 0개일 시
    @Override
    public int updatePstatus(Long pNumber) {
        return dealDAO.updatePstatus(pNumber);
    }
}
