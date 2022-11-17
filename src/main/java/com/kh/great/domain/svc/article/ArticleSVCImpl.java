package com.kh.great.domain.svc.article;


import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.dao.article.Article;
import com.kh.great.domain.dao.article.ArticleDAO;
import com.kh.great.domain.dao.article.ArticleFilterCondition;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleSVCImpl implements ArticleSVC {
    private final ArticleDAO articleDAO;
    private final UploadFileSVC uploadFileSVC;
    private final FileUtils fileUtils;

    /**
     * 게시글 등록
     * @param article 등록정보
     * @return 게시글정보
     */
    @Override
    public Article save(Article article) {
        //게시글번호 생성
        Long generatedArticleNum = articleDAO.generatedArticleNum();
        //생성한 게시글번호 부여
        article.setArticleNum(generatedArticleNum);
        //게시글 등록
        articleDAO.save(article);

        return articleDAO.read(generatedArticleNum).get();
    }

    /**
     * 게시글 등록 (업로드 파일 포함)
     * @param article 등록정보
     * @param files 업로드 파일
     * @return 게시글정보
     */
    @Override
    public Article save(Article article, List<MultipartFile> files) {
        //게시글번호 생성
        Long generatedArticleNum = articleDAO.generatedArticleNum();
        //생성한 게시글번호 부여
        article.setArticleNum(generatedArticleNum);
        //게시글 등록
        articleDAO.save(article);
        //업로드 파일 등록
        uploadFileSVC.addFile(files, AttachCode.B0101, generatedArticleNum);

        return articleDAO.read(generatedArticleNum).get();
    }

    /**
     * 게시글 조회
     * @param articleNum 게시글번호
     * @return 게시글정보
     */
    @Override
    public Optional<Article> read(Long articleNum) {
        //조회수 증가
        articleDAO.increaseViewCount(articleNum);

        return articleDAO.read(articleNum);
    }

    /**
     * 게시글 수정
     * @param articleNum 게시글번호
     * @param article 수정할 정보
     * @return 게시글정보
     */
    @Override
    public Article update(Long articleNum, Article article) {
        //게시글 수정
        articleDAO.update(articleNum, article);

        return articleDAO.read(articleNum).get();
    }

    /**
     * 게시글 수정 (업로드 파일 포함)
     * @param articleNum 게시글번호
     * @param article 수정할 정보
     * @param files 업로드 파일
     * @return 게시글정보
     */
    @Override
    public Article update(Long articleNum, Article article, List<MultipartFile> files) {
        //게시글 수정
        articleDAO.update(articleNum, article);
        //업로드 파일 등록
        uploadFileSVC.addFile(files, AttachCode.B0101, articleNum);

        return articleDAO.read(articleNum).get();
    }

    /**
     * 게시글 삭제
     * @param articleNum 게시글번호
     */
    @Override
    public void delete(Long articleNum) {
        //첨부파일 메타정보 조회
        List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), articleNum);
        //첨부파일 삭제
        List<UploadFile> unionFiles = new LinkedList<>();
        unionFiles.addAll(attachFiles);
        for (UploadFile file : unionFiles) {
            fileUtils.deleteAttachFile(AttachCode.valueOf(file.getCode()), file.getStoreFilename());
        }
        //게시글 삭제
        articleDAO.delete(articleNum);
        //첨부파일 메타정보 삭제
        uploadFileSVC.deleteFileByCodeWithRid(AttachCode.B0101.name(), articleNum);
    }

    /**
     * 게시글 목록 - 전체
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll() {

        return articleDAO.findAll();
    }

    /**
     * 게시글 목록 - 카테고리
     * @param category 카테고리
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll(String category) {

        return articleDAO.findAll(category);
    }

    /**
     * 게시글 목록 - 레코드
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll(int startRec, int endRec) {

        return articleDAO.findAll(startRec, endRec);
    }

    /**
     * 게시글 목록 - 카테고리, 레코드
     * @param category 카테고리
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll(String category, int startRec, int endRec) {

        return articleDAO.findAll(category, startRec, endRec);
    }

    /**
     * 게시글 목록 - 검색
     * @param filterCondition 카테고리, 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll(ArticleFilterCondition filterCondition) {

        return articleDAO.findAll(filterCondition);
    }

    /**
     * 게시글 수 - 전체
     * @return 게시글 수
     */
    @Override
    public int totalCount() {

        return articleDAO.totalCount();
    }

    /**
     * 게시글 수 - 카테고리
     * @param category 카테고리
     * @return 게시글 수
     */
    @Override
    public int totalCount(String category) {

        return articleDAO.totalCount(category);
    }

    /**
     * 게시글 수 - 검색
     * @param filterCondition 카테고리, 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 게시글 수
     */
    @Override
    public int totalCount(ArticleFilterCondition filterCondition) {

        return articleDAO.totalCount(filterCondition);
    }
}
