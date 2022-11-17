package com.kh.great.domain.svc.comment;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.dao.article.ArticleDAO;
import com.kh.great.domain.dao.comment.Comment;
import com.kh.great.domain.dao.comment.CommentDAO;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentSVCImpl implements CommentSVC {
    private final CommentDAO commentDAO;
    private final ArticleDAO articleDAO;
    private final UploadFileSVC uploadFileSVC;
    private final FileUtils fileUtils;

    /**
     * 댓글 등록
     * @param comment 등록정보
     * @return 댓글정보
     */
    @Override
    public Comment save(Comment comment) {
        //댓글번호 생성
        Long generatedCommentNum = commentDAO.generatedCommentNum();
        //생성한 댓글번호 부여
        comment.setCommentNum(generatedCommentNum);
        //그룹이 없으면
        if (comment.getCommentGroup() == null) {
            //댓글그룹번호 생성
            Long generatedCommentGroupNum = commentDAO.generatedCommentGroupNum();
            //생성한 댓글그룹번호 부여
            comment.setCommentGroup(generatedCommentGroupNum);
        }
        //댓글 등록
        commentDAO.save(comment);
        //댓글 등록한 게시글의 댓글 수
        int totalCountOfArticle = commentDAO.totalCountOfArticle(comment.getArticleNum());
        //댓글 등록한 게시글의 댓글 수 변경
        articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle), comment.getArticleNum());

        return commentDAO.find(generatedCommentNum).get();
    }

    /**
     * 댓글 등록 (업로드 파일 포함)
     * @param comment 등록정보
     * @param file 업로드 파일
     * @return 댓글정보
     */
    @Override
    public Comment save(Comment comment, MultipartFile file) {
        //댓글번호 생성
        Long generatedCommentNum = commentDAO.generatedCommentNum();
        //생성한 댓글번호 부여
        comment.setCommentNum(generatedCommentNum);
        //그룹이 없으면
        if (comment.getCommentGroup() == null) {
            //댓글그룹번호 생성
            Long generatedCommentGroupNum = commentDAO.generatedCommentGroupNum();
            //생성한 댓글그룹번호 부여
            comment.setCommentGroup(generatedCommentGroupNum);
        }
        //댓글 등록
        commentDAO.save(comment);

        //댓글 등록한 게시글의 댓글 수
        int totalCountOfArticle = commentDAO.totalCountOfArticle(comment.getArticleNum());
        //댓글 등록한 게시글의 댓글 수 변경
        articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle), comment.getArticleNum());
        //업로드 파일 등록
        uploadFileSVC.addFile(file, AttachCode.B0101,generatedCommentNum);

        return commentDAO.find(generatedCommentNum).get();
    }

    /**
     * 댓글 조회
     * @param commentNum 댓글번호
     * @return 댓글정보
     */
    @Override
    public Optional<Comment> find(Long commentNum) {

        return commentDAO.find(commentNum);
    }

    /**
     * 댓글 수정
     * @param commentNum 댓글번호
     * @param comment 수정할 정보
     * @return 댓글정보
     */
    @Override
    public Comment update(Long commentNum, Comment comment) {
        commentDAO.update(commentNum, comment);

        return commentDAO.find(commentNum).get();
    }

    /**
     * 댓글 수정 (업로드 파일 포함)
     * @param commentNum 댓글번호
     * @param comment 수정할 정보
     * @param file 업로드 파일
     * @return 댓글정보
     */
    @Override
    public Comment update(Long commentNum, Comment comment, MultipartFile file) {
        //댓글 수정
        commentDAO.update(commentNum, comment);
        //기존 첨부파일 메타정보 조회
        List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), commentNum);
        //스토리지에서 기존 첨부파일 삭제
        attachFiles.stream().forEach(attachFile -> fileUtils.deleteAttachFile(AttachCode.valueOf(attachFile.getCode()), attachFile.getStoreFilename()));
        //기존 첨부파일 메타정보 삭제
        uploadFileSVC.deleteFileByCodeWithRid(AttachCode.B0101.name(), commentNum);
        //업로드 파일 등록
        uploadFileSVC.addFile(file, AttachCode.B0101, commentNum);

        return commentDAO.find(commentNum).get();
    }

    /**
     * 댓글 삭제
     * @param commentNum 댓글번호
     */
    @Override
    public void delete(Long commentNum) {
        //첨부파일 메타정보 조회
        List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), commentNum);
        //스토리지에서 첨부파일 삭제
        attachFiles.stream().forEach(file -> fileUtils.deleteAttachFile(AttachCode.valueOf(file.getCode()), file.getStoreFilename()));
        //댓글 삭제
        commentDAO.delete(commentNum);
        //첨부파일 메타 정보 삭제
        uploadFileSVC.deleteFileByCodeWithRid(AttachCode.B0101.name(), commentNum);

        //댓글 조회 by 댓글번호
        Optional<Comment> foundComment = commentDAO.find(commentNum);
        //댓글이 등록된 게시글번호 조회 by 조회한 댓글정보
        Long articleNum = foundComment.get().getArticleNum();
        //댓글 삭제한 게시글의 댓글 수
        int totalCountOfArticle = commentDAO.totalCountOfArticle(articleNum);
        //댓글 삭제한 게시글의 댓글 수 변경
        articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle), articleNum);
    }

    /**
     * 답댓글이 있는 댓글 삭제
     * @param commentNum 댓글번호
     * @return 삭제 건수
     */
    @Override
    public int updateToDeletedComment(Long commentNum) {

        return commentDAO.updateToDeletedComment(commentNum);
    }

    /**
     * 댓글 조회 by 게시글번호
     * @param articleNum 게시글번호
     * @return 댓글정보
     */
    @Override
    public List<Comment> findAll(Long articleNum) {

        return commentDAO.findAll(articleNum);
    }

    /**
     * 답댓글 수 by 댓글번호
     * @param commentNum 댓글번호
     * @return 답댓글 수
     */
    @Override
    public int countOfChildrenComments(Long commentNum) {

        return commentDAO.countOfChildrenComments(commentNum);
    }
}