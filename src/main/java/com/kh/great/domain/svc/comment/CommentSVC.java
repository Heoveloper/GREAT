package com.kh.great.domain.svc.comment;

import com.kh.great.domain.dao.comment.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CommentSVC {
    /**
     * 댓글 등록
     * @param comment 등록정보
     * @return 댓글정보
     */
    Comment save(Comment comment);

    /**
     * 댓글 등록 (업로드 파일 포함)
     * @param comment 등록정보
     * @param file 업로드 파일
     * @return 댓글정보
     */
    Comment save(Comment comment, MultipartFile file);

    /**
     * 댓글 조회
     * @param commentNum 댓글번호
     * @return 댓글정보
     */
    Optional<Comment> find(Long commentNum);

    /**
     * 댓글 수정
     * @param commentNum 댓글번호
     * @param comment 수정할 정보
     * @return 댓글정보
     */
    Comment update(Long commentNum, Comment comment);

    /**
     * 댓글 수정 (업로드 파일 포함)
     * @param commentNum 댓글번호
     * @param comment 수정할 정보
     * @param file 업로드 파일
     * @return 댓글정보
     */
    Comment update(Long commentNum, Comment comment, MultipartFile file);

    /**
     * 댓글 삭제
     * @param commentNum 댓글번호
     */
    void delete(Long commentNum);

    /**
     * 답댓글이 있는 댓글 삭제
     * @param commentNum 댓글번호
     * @return 삭제 건수
     */
    int updateToDeletedComment(Long commentNum);

    /**
     * 댓글 목록 by 게시글번호
     * @param articleNum 게시글번호
     * @return 댓글정보
     */
    List<Comment> findAll(Long articleNum);

    /**
     * 답댓글 수 by 댓글번호
     * @param commentNum 댓글번호
     * @return 답댓글 수
     */
    int countOfChildrenComments(Long commentNum);
}
