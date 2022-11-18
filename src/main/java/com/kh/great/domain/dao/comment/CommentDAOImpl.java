package com.kh.great.domain.dao.comment;

import com.kh.great.domain.dao.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentDAOImpl implements CommentDAO {
    private final JdbcTemplate jt;

    /**
     * 신규 댓글번호 생성
     * @return 댓글번호
     */
    @Override
    public Long generatedCommentNum() {
        String sql = "select comments_comment_num_seq.nextval from dual ";

        Long commentNum = jt.queryForObject(sql, Long.class);

        return commentNum;
    }

    /**
     * 신규 댓글그룹번호 생성
     * @return 댓글그룹번호
     */
    @Override
    public Long generatedCommentGroupNum() {
        String sql = "select comments_comment_num_seq.currval from dual ";

        Long commentGroupNum = jt.queryForObject(sql, Long.class);

        return commentGroupNum;
    }

    /**
     * 댓글 등록
     * @param comment 댓글정보
     * @return 등록 건수
     */
    @Override
    public int save(Comment comment) {
        //답댓글이라면
        if (comment.getPCommentNum() != null) {
            Optional<Comment> parentComment = find(comment.getPCommentNum());
            //답댓글 단계 = 부모댓글 단계 + 1
            comment.setStep(parentComment.get().getStep() + 1);
            //댓글 단계가 1이면
            if (comment.getStep() == 1) {
                //댓글 순서 = 그룹 내 최고 순서 + 1
                comment.setCommentOrder(maxCommentOrder(comment) + 1);
            //댓글 단계가 2 이상이면
            } else {
                //댓글 순서 변경
                changeCommentOrder(comment, maxCommentOrderInSameParent(comment));
                //댓글 순서 = 같은 부모 댓글의 동일 단계 댓글 중 최고 순서 + 1
                comment.setCommentOrder(maxCommentOrderInSameParent(comment) + 1);
            }
        }

        StringBuffer sql = new StringBuffer();

        sql.append("insert into comments (article_num, comment_group, comment_num, p_comment_num, step, comment_order, ");
        sql.append("                      p_comment_nickname, mem_number, comment_contents, create_date, reply) ");
        sql.append("              values (?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?) ");

        int affectedRow = jt.update(sql.toString(), comment.getArticleNum(), comment.getCommentGroup(), comment.getCommentNum(), comment.getPCommentNum(), comment.getStep(), comment.getCommentOrder(),
                comment.getPCommentNickname(), comment.getMemNumber(), comment.getCommentContents(), comment.getReply());

        return affectedRow;
    }

    /**
     * 댓글 조회
     * @param commentNum 댓글번호
     * @return 댓글정보
     */
    @Override
    public Optional<Comment> find(Long commentNum) {
        StringBuffer sql = new StringBuffer();

        sql.append("select article_num, comment_group, comment_num, p_comment_num, step, comment_order, ");
        sql.append("       p_comment_nickname, m.mem_number, comment_contents, create_date, reply ");
        sql.append("  from comments c, member m ");
        sql.append(" where c.mem_number = m.mem_number ");
        sql.append("   and c.comment_num = ? ");

        try {
            Comment comment = jt.queryForObject(sql.toString(), new RowMapper<Comment>() {
                @Override
                public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    Comment comment = (new BeanPropertyRowMapper<>(Comment.class)).mapRow(rs, rowNum);
                    comment.setMember(member);
                    return comment;
                }
            }, commentNum);
            return Optional.of(comment);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * 댓글 수정
     * @param commentNum 댓글번호
     * @param comment 수정할 내용
     * @return 수정 건수
     */
    @Override
    public int update(Long commentNum, Comment comment) {
        StringBuffer sql = new StringBuffer();

        sql.append("update comments ");
        sql.append("   set comment_contents = ?, ");
        sql.append("       create_date = sysdate ");
        sql.append(" where comment_num = ? ");

        int affectedRow = jt.update(sql.toString(), comment.getCommentContents(), commentNum);

        return affectedRow;
    }

    /**
     * 댓글 삭제
     * @param commentNum 댓글번호
     * @return 삭제 건수
     */
    @Override
    public int delete(Long commentNum) {
        String sql = "delete from comments where comment_num = ? ";

        int affectedRow = jt.update(sql, commentNum);

        return affectedRow;
    }

    /**
     * 답댓글이 있는 댓글 삭제
     * @param commentNum 댓글번호
     * @return 삭제 건수
     */
    @Override
    public int updateToDeletedComment(Long commentNum) {
        StringBuffer sql = new StringBuffer();

        sql.append("update comments ");
        sql.append("   set comment_contents = '삭제된 댓글입니다.', ");
        sql.append("       create_date = sysdate ");
        sql.append(" where comment_num = ? ");

        int affectedRow = jt.update(sql.toString(), commentNum);

        return affectedRow;
    }

    /**
     * 댓글 목록 by 게시글번호
     * @param articleNum 게시글번호
     * @return 댓글정보
     */
    @Override
    public List<Comment> findAll(Long articleNum) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select article_num, comment_group, comment_num, p_comment_num, step, comment_order, ");
        sql.append("         p_comment_nickname, m.mem_nickname, c.mem_number, comment_contents, create_date, reply ");
        sql.append("    from comments c, member m ");
        sql.append("   where c.mem_number = m.mem_number ");
        sql.append("     and c.article_num = ? ");
        sql.append("order by comment_group asc, comment_order asc ");

        List<Comment> comments = jt.query(sql.toString(), new RowMapper<Comment>() {
            @Override
            public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                Comment comment = (new BeanPropertyRowMapper<>(Comment.class)).mapRow(rs, rowNum);
                comment.setMember(member);
                return comment;
            }
        }, articleNum);

        return comments;
    }

    /**
     * 댓글 수 by 게시글번호
     * @param articleNum 게시글번호
     * @return 댓글 수
     */
    @Override
    public int totalCountOfArticle(Long articleNum) {
        String sql = "select count(*) from comments where article_num = ? ";

        Integer cntPerArticle = jt.queryForObject(sql, Integer.class, articleNum);

        return cntPerArticle;
    }

    /**
     * 답댓글 수 by 댓글번호
     * @param commentNum 댓글번호
     * @return 답댓글 수
     */
    @Override
    public int countOfChildrenComments(Long commentNum) {
        String sql = "select count(*) from comments where p_comment_num = ? ";

        Integer cntOfChildrenComments = jt.queryForObject(sql, Integer.class, commentNum);

        return cntOfChildrenComments;
    }

    /**
     * 댓글 순서 변경
     * @param comment 댓글정보
     * @param commentOrder 순서
     */
    @Override
    public void changeCommentOrder(Comment comment, Long commentOrder) {
        StringBuffer sql = new StringBuffer();

        sql.append("update comments ");
        sql.append("   set comment_order = comment_order + 1 ");
        sql.append(" where article_num = ? ");
        sql.append("   and comment_group = ? ");
        sql.append("   and comment_order > ? ");

        jt.update(sql.toString(), comment.getArticleNum(), comment.getCommentGroup(), commentOrder);
    }

    /**
     * 게시글 내 동일 그룹의 댓글 순서 중 최고 순서 산출
     * @param comment 댓글정보
     * @return 최고 순서
     */
    @Override
    public Long maxCommentOrder(Comment comment) {
        String sql = "select max(comment_order) from comments where article_num = ? and comment_group = ? ";

        Long maxCommentOrder = jt.queryForObject(sql, Long.class, comment.getArticleNum(), comment.getCommentGroup());

        return maxCommentOrder;
    }

    /**
     * 게시글 내 부모댓글이 같은 동일 그룹, 동일 단계의 댓글 순서 중 최고 순서 산출
     * @param comment 댓글정보
     * @return 최고 순서
     */
    @Override
    public Long maxCommentOrderInSameParent(Comment comment) {
        StringBuffer sql = new StringBuffer();

        sql.append("select max(comment_order) ");
        sql.append("  from comments ");
        sql.append(" where article_num = ? ");
        sql.append("   and comment_group = ? ");
        sql.append("   and p_comment_num = ? ");
        sql.append("   and step = ? ");

        Long maxCommentOrderInSameParent = jt.queryForObject(sql.toString(), Long.class, comment.getArticleNum(), comment.getCommentGroup(), comment.getPCommentNum(), comment.getStep());
        //산출한 최고 순서가 null 이면 부모댓글 순서 반환
        if (maxCommentOrderInSameParent == null) {
            maxCommentOrderInSameParent = find(comment.getPCommentNum()).get().getCommentOrder();
        }

        return maxCommentOrderInSameParent;
    }
}