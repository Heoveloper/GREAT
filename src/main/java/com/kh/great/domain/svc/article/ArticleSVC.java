package com.kh.great.domain.svc.article;


import com.kh.great.domain.dao.article.Article;
import com.kh.great.domain.dao.article.ArticleFilterCondition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArticleSVC {
    /**
     * 게시글 등록
     * @param article 등록정보
     * @return 게시글정보
     */
    Article save(Article article);

    /**
     * 게시글 등록 (업로드 파일 포함)
     * @param article 등록정보
     * @param files 업로드 파일
     * @return 게시글정보
     */
    Article save(Article article, List<MultipartFile> files);

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
     * @return 게시글정보
     */
    Article update(Long articleNum, Article article);

    /**
     * 게시글 수정 (업로드 파일 포함)
     * @param articleNum 게시글번호
     * @param article 수정할 정보
     * @param files 업로드 파일
     * @return 게시글정보
     */
    Article update(Long articleNum, Article article, List<MultipartFile> files);

    /**
     * 게시글 삭제
     * @param articleNum 게시글번호
     */
    void delete(Long articleNum);

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