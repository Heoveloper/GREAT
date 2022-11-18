package com.kh.great.domain.dao.comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
    /**
     * 신규 댓글번호 생성
     * @return 댓글번호
     */
    Long generatedCommentNum();

    /**
     * 신규 댓글그룹번호 생성
     * @return 댓글그룹번호
     */
    Long generatedCommentGroupNum();

    /**
     * 댓글 등록
     * @param comment 댓글정보
     * @return 등록 건수
     */
    int save(Comment comment);

    /**
     * 댓글 조회
     * @param commentNum 댓글번호
     * @return 댓글정보
     */
    Optional<Comment> find(Long commentNum);

    /**
     * 댓글 수정
     * @param commentNum 댓글번호
     * @param comment 수정할 내용
     * @return 수정 건수
     */
    int update(Long commentNum, Comment comment);

    /**
     * 댓글 삭제
     * @param commentNum 댓글번호
     * @return 삭제 건수
     */
    int delete(Long commentNum);

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
     * 댓글 수 by 게시글번호
     * @param articleNum 게시글번호
     * @return 댓글 수
     */
    int totalCountOfArticle(Long articleNum);

    /**
     * 답댓글 수 by 댓글번호
     * @param commentNum 댓글번호
     * @return 답댓글 수
     */
    int countOfChildrenComments(Long commentNum);

    /**
     * 댓글 순서 변경
     * @param comment 댓글정보
     * @param commentOrder 순서
     */
    void changeCommentOrder(Comment comment, Long commentOrder);

    /**
     * 게시글 내 동일 그룹의 댓글 순서 중 최고 순서 산출
     * @param comment 댓글정보
     * @return 최고 순서
     */
    Long maxCommentOrder(Comment comment);

    /**
     * 게시글 내 부모댓글이 같은 동일 그룹, 동일 단계의 댓글 순서 중 최고 순서 산출
     * @param comment 댓글정보
     * @return 최고 순서
     */
    Long maxCommentOrderInSameParent(Comment comment);
}