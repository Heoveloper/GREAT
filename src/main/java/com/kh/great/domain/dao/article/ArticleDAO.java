package com.kh.great.domain.dao.article;

import java.util.List;
import java.util.Optional;

public interface ArticleDAO {
    /**
     * 신규 게시글번호 생성
     * @return 게시글번호
     */
    Long generatedArticleNum();

    /**
     * 게시글 등록
     * @param article 등록정보
     * @return 게시글번호
     */
    int save(Article article);

    /**
     * 게시글 조회
     * @param articleNum 게시글번호
     * @return 게시글정보
     */
    Optional<Article> read(Long articleNum);

    /**
     * 게시글 수정
     * @param articleNum 게시글번호
     * @param article 수정할 정보
     * @return 수정 건수
     */
    int update(Long articleNum, Article article);

    /**
     * 게시글 삭제
     * @param articleNum 게시글번호
     * @return 삭제 건수
     */
    int delete(Long articleNum);

    /**
     * 게시글 목록 - 전체
     * @return 게시글 목록
     */
    List<Article> findAll();

    /**
     * 게시글 목록 - 카테고리
     * @param category 카테고리
     * @return 게시글 목록
     */
    List<Article> findAll(String category);

    /**
     * 게시글 목록 - 레코드
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 게시글 목록
     */
    List<Article> findAll(int startRec, int endRec);

    /**
     * 게시글 목록 - 카테고리, 레코드
     * @param category 카테고리
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 게시글 목록
     */
    List<Article> findAll(String category, int startRec, int endRec);

    /**
     * 게시글 목록 - 검색
     * @param filterCondition 카테고리, 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 게시글 목록
     */
    List<Article> findAll(ArticleFilterCondition filterCondition);

    /**
     * 조회수 증가
     * @param articleNum 게시글번호
     * @return 조회수 증가된 게시글 수
     */
    int increaseViewCount(Long articleNum);

    /**
     * 댓글수 변동
     * @param totalCountOfArticle 댓글수
     * @param articleNum 게시글번호
     * @return 댓글수 변동된 게시글 수
     */
    int updateCommentsCnt(Long totalCountOfArticle, Long articleNum);

    /**
     * 게시글 수 - 전체
     * @return 게시글 수
     */
    int totalCount();

    /**
     * 게시글 수 - 카테고리
     * @param category 카테고리
     * @return 게시글 수
     */
    int totalCount(String category);

    /**
     * 게시글 수 - 검색
     * @param filterCondition 카테고리, 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 게시글 수
     */
    int totalCount(ArticleFilterCondition filterCondition);
}