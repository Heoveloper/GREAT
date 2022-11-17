package com.kh.great.domain.dao.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeDAOImpl implements NoticeDAO {
    private final JdbcTemplate jt;

    /**
     * 공지사항 등록
     * @param notice 등록정보
     * @return 공지사항정보
     */
    @Override
    public Notice save(Notice notice) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into notice (notice_id, title, content, write, count, attachments, udate) ");
        sql.append("            values (notice_notice_id_seq.nextval, ?, ?, '관리자', 0, ?, sysdate) ");

        //KeyHolder 생성
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //SQL 실행
        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                //insert 후 insert 레코드 중 반환할 컬럼명으로 keyHolder 에 저장된다.
                PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"notice_id"});
                pstmt.setString(1, notice.getTitle());
                pstmt.setString(2, notice.getContent());
                pstmt.setString(3, notice.getWrite());
                pstmt.setString(4, notice.getAttachments());
                return pstmt;
            }
        }, keyHolder);

        long notice_id = Long.valueOf(keyHolder.getKeys().get("notice_id").toString());

        return read(notice_id);
    }

    /**
     * 조회 by 공지사항번호
     * @param noticeId 공지사항번호
     * @return 공지사항정보
     */
    @Override
    public Notice read(Long noticeId) {
        StringBuffer sql = new StringBuffer();

        sql.append("select notice_id, title, content, write, attachments, count, udate ");
        sql.append("  from notice ");
        sql.append(" where notice_id = ? ");

        Notice notice = null;
        try {
            notice = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Notice.class), noticeId);
        } catch (EmptyResultDataAccessException e) {
            log.info("조회할 공지사항이 없습니다! 공지사항번호 = {}", noticeId);
        }

        return notice;
    }

    /**
     * 공지사항 수정
     * @param noticeId 공지사항번호
     * @param notice 수정할 정보
     * @return 수정 건수
     */
    @Override
    public int update(Long noticeId, Notice notice) {
        StringBuffer sql = new StringBuffer();

        sql.append("update notice ");
        sql.append("   set title = ?, ");
        sql.append("       content = ?, ");
        sql.append("       attachments = ?, ");
        sql.append("       udate = sysdate ");
        sql.append(" where notice_id = ? ");

        int affectedRow = jt.update(sql.toString(), notice.getTitle(), notice.getContent(), notice.getAttachments(), noticeId);

        return affectedRow;
    }

    /**
     * 공지사항 삭제
     * @param noticeId 공지사항번호
     * @return 삭제 건수
     */
    @Override
    public int delete(Long noticeId) {
        StringBuffer sql = new StringBuffer();

        sql.append("delete ");
        sql.append("  from notice ");
        sql.append(" where notice_id = ? ");

        int cnt = jt.update(sql.toString(), noticeId);

        return cnt;
    }

    /**
     * 공지사항 목록 - 전체
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> foundAll() {
        StringBuffer sql = new StringBuffer();

        sql.append("  select notice_id, title, content, count, udate ");
        sql.append("    from notice ");
        sql.append("order by notice_id desc ");

        List<Notice> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Notice.class));

        return list;
    }

    /**
     * 공지사항 목록 - 전체 (작성자, 첨부파일 유무 포함)
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> selectAll() {
        StringBuffer sql = new StringBuffer();

        sql.append("  select notice_id, title, content, write, attachments, count, udate ");
        sql.append("    from notice ");
        sql.append("order by notice_id desc ");

        List<Notice> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Notice.class));

        return list;
    }

    /**
     * 공지사항 목록 - 레코드
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> findAll(int startRec, int endRec) {
        StringBuffer sql = new StringBuffer();

        sql.append("select t1.* ");
        sql.append("from (select row_number() over (order by notice_id desc) no, notice_id, title, content, count, udate ");
        sql.append("        from notice) t1 ");
        sql.append("where t1.no between ? and ? ");

        List<Notice> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Notice.class), startRec, endRec);

        return list;
    }

    /**
     * 공지사항 목록 - 검색
     * @param filterCondition 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> findAll(BbsFilterCondition filterCondition) {
        StringBuffer sql = new StringBuffer();

        sql.append("select t1.* ");
        sql.append("  from (select row_number() over (order by notice_id desc) no, notice_id, title, content, count, udate ");
        sql.append("        from notice ");

        //분류
        sql = dynamicQuery(filterCondition, sql);

        sql.append("       ) t1 ");
        sql.append(" where t1.no between ? and ? ");

        List<Notice> list = null;
        //전체 공지사항
        list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Notice.class),
                filterCondition.getStartRec(), filterCondition.getEndRec());

        return list;
    }

    /**
     * 조회수 증가
     * @param noticeId 공지사항번호
     * @return 조회수 증가된 공지사항 수
     */
    @Override
    public int increaseViewCount(Long noticeId) {
        String sql = "update notice set count = count + 1 where notice_id = ? ";

        int affectedRow = jt.update(sql, noticeId);

        return affectedRow;
    }

    /**
     * 공지사항 수 - 전체
     * @return 공지사항 수
     */
    @Override
    public int totalCount() {
        String sql = "select count(*) from notice";

        Integer cnt = jt.queryForObject(sql, Integer.class);

        return cnt;
    }

    /**
     * 공지사항 수 - 검색
     * @param filterCondition 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 공지사항 수
     */
    @Override
    public int totalCount(BbsFilterCondition filterCondition) {
        StringBuffer sql = new StringBuffer();

        sql.append("select count(*) ");
        sql.append("  from notice ");

        sql = dynamicQuery(filterCondition, sql);

        Integer cnt = 0;
        //검색된 전체 공지사항 수
        cnt = jt.queryForObject(sql.toString(), Integer.class);

        return cnt;
    }

    /**
     * 동적 쿼리
     * @param filterCondition 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @param sql 쿼리
     * @return 동적 쿼리
     */
    private StringBuffer dynamicQuery(BbsFilterCondition filterCondition, StringBuffer sql) {
        //검색 유형, 검색어 모두 존재하면
        if(!StringUtils.isEmpty(filterCondition.getSearchType()) && !StringUtils.isEmpty(filterCondition.getKeyword())) {
            sql.append(" where ");
        } else {
            return sql;
        }

        //검색 유형
        switch (filterCondition.getSearchType()) {
            //제목 또는 내용
            case "TC":
                sql.append(" (title like '%" + filterCondition.getKeyword() + "%' ");
                sql.append("  or content like '%" + filterCondition.getKeyword() + "%') ");
                break;
            //제목
            case "T":
                sql.append(" title like '%" + filterCondition.getKeyword() + "%' ");
                break;
            //내용
            case "C":
                sql.append(" content like '%" + filterCondition.getKeyword() + "%' ");
                break;
            default:
        }

        return sql;
    }
}