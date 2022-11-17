package com.kh.great.domain.svc.mypage;


import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.mypage.Bookmark;
import com.kh.great.domain.dao.mypage.Good;
import com.kh.great.domain.dao.mypage.Review;
import com.kh.great.domain.dao.product.Product;

import java.util.List;
import java.util.Optional;

public interface MyPageSVC {
    //리뷰 등록
    Review save(Review review);

    //리뷰 조회 by 회원번호 where 내 리뷰
    List<Review> findByMemNumber(Long memNumber);

    //리뷰 조회 by 회원번호 where 판매자 프로필
    List<Review> findBySellerNumber(Long memNumber);

    //리뷰 조회 by 리뷰번호
    Optional<Review> findByReviewNumber(Long reviewNumber);

    //판매글 조회 where 판매자 프로필
    List<Product> findByOwnerNumber(Long ownerNumber);

    //리뷰 수정
    int update(Long reviewNumber, Review review);

    //리뷰 삭제
    int deleteByReviewId(Long reviewNumber);

    //회원 조회 by 회원번호
    Optional<Member> findMember(Long memNumber);

    //즐겨찾기 추가
    Bookmark addBookmark(Bookmark bookmark);

    //즐겨찾기 조회
    Optional<Bookmark> findBookmarkNumber(Long bookmarkNumber);

    //즐겨찾기 삭제 where 판매자 프로필
    int delBookmark(Long memNumber);

    //즐겨찾기 삭제 where 내 즐겨찾기
    int delBookmarkInMyPage(Long bookmarkNumber);

    //즐겨찾기 회원 조회
    List<Bookmark> findBookmark(Long memNumber);

    //좋아요 추가
    Good addGood(Good good);

    //좋아요 삭제 where 판매글
    int delGood(Long pNumber);

    //좋아요 삭제 where 내 좋아요
    int delGoodInMyPage(Long goodNumber);

    //좋아요 회원 조회
    List<Good> findGoods(Long memNumber);
}