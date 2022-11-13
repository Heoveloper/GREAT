package com.kh.great.domain.dao.deal;

import java.util.List;
import java.util.Optional;

public interface DealDAO {
    //구매 등록
    Deal add(Deal deal);

    //구매 조회 by 회원번호
    List<Deal> findByMemberNumber(Long memNumber);

    //구매 조회 by 주문번호
    Optional<Deal> findByOrderNumber(Long orderNumber);

    //구매 시 상품 개수 감소
    int update(Long pNumber, Deal deal);

    //구매 취소
    int deleteByOrderNumber(Long orderNumber);

    //구매 취소 시 상품 개수 증가
    int delUpdate(Long pNumber, Deal deal);

    //남은 수량 0개일 시
    int updatePstatus(Long pNumber);
}
