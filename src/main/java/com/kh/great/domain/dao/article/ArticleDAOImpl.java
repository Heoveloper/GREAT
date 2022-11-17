package com.kh.great.domain.dao.article;

import com.kh.great.domain.dao.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleDAOImpl implements ArticleDAO {
    private final JdbcTemplate jt;

    /**
     * 신규 게시글번호 생성
     * @return 게시글번호
     */
    @Override
    public Long generatedArticleNum() {
        String sql = "select article_article_num_seq.nextval from dual ";

        Long articleNum = jt.queryForObject(sql, Long.class);

        return articleNum;
    }

    /**
     * 게시글 등록
     * @param article 등록정보
     * @return 게시글번호
     */
    @Override
    public int save(Article article) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into article (article_num, mem_number, article_category, article_title, article_contents, attachment, create_date, views, comments) ");
        sql.append("             values (?, ?, ?, ?, ?, ?, sysdate, 0, 0) ");

        int affectedRow = jt.update(sql.toString(), article.getArticleNum(), article.getMemNumber(), article.getArticleCategory(),
                                    article.getArticleTitle(), article.getArticleContents(), article.getAttachment());

        return affectedRow;
    }

    /**
     * 게시글 조회
     * @param articleNum 게시글번호
     * @return 게시글정보
     */
    @Override
    public Optional<Article> read(Long articleNum) {
        StringBuffer sql = new StringBuffer();

        sql.append("select article_num, article_category, article_title, article_contents, attachment, a.mem_number, mem_nickname, create_date, views, comments ");
        sql.append("  from article a, member m ");
        sql.append(" where a.mem_number = m.mem_number ");
        sql.append("   and a.article_num = ? ");

        try {
            Article article = jt.queryForObject(sql.toString(), new RowMapper<Article>() {
                @Override
                public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
                    article.setMember(member);
                    return article;
                }
            }, articleNum);
            return Optional.of(article);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * 게시글 수정
     * @param articleNum 게시글번호
     * @param article 수정할 정보
     * @return 수정 건수
     */
    @Override
    public int update(Long articleNum, Article article) {
        StringBuffer sql = new StringBuffer();

        sql.append("update article ");
        sql.append("   set article_category = ?, ");
        sql.append("       article_title = ?, ");
        sql.append("       article_contents = ?, ");
        sql.append("       attachment = ? ");
        sql.append(" where article_num = ? ");

        int affectedRow = jt.update(sql.toString(), article.getArticleCategory(), article.getArticleTitle(), article.getArticleContents(), article.getAttachment(), articleNum);

        return affectedRow;
    }

    /**
     * 게시글 삭제
     * @param articleNum 게시글번호
     * @return 삭제 건수
     */
    @Override
    public int delete(Long articleNum) {
        String sql = "delete from article where article_num = ? ";

        int affectedRow = jt.update(sql, articleNum);

        return affectedRow;
    }

    /**
     * 게시글 목록 - 전체
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll() {
        StringBuffer sql = new StringBuffer();

        sql.append("  select article_num, article_category, article_title, attachment, mem_nickname, create_date, views, comments ");
        sql.append("    from article a, member m ");
        sql.append("   where a.mem_number = m.mem_number ");
        sql.append("order by a.article_num desc ");

        List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
                article.setMember(member);
                return article;
            }
        });

        return articles;
    }

    /**
     * 게시글 목록 - 카테고리
     * @param category 카테고리
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll(String category) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select article_num, article_category, article_title, attachment, mem_nickname, create_date, views, comments ");
        sql.append("    from article a, member m ");
        sql.append("   where a.mem_number = m.mem_number ");
        sql.append("     and a.article_category = ? ");
        sql.append("order by a.article_num desc ");

        List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
                article.setMember(member);
                return article;
            }
        }, category);

        return articles;
    }

    /**
     * 게시글 목록 - 레코드
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll(int startRec, int endRec) {
        StringBuffer sql = new StringBuffer();

        sql.append("select t1.* ");
        sql.append("  from (select row_number() over (order by a.article_num desc) no, article_num, article_category, ");
        sql.append("               article_title, attachment, mem_nickname, create_date, views, comments ");
        sql.append("          from article a, member m ");
        sql.append("         where a.mem_number = m.mem_number) t1 ");
        sql.append(" where t1.no between ? and ? ");

        List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
                article.setMember(member);
                return article;
            }
        }, startRec, endRec);

        return articles;
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
        StringBuffer sql = new StringBuffer();

        sql.append("select t1.* ");
        sql.append("  from (select row_number() over (order by a.article_num desc) no, article_num, article_category, ");
        sql.append("               article_title, attachment, mem_nickname, create_date, views, comments ");
        sql.append("          from article a, member m ");
        sql.append("         where a.mem_number = m.mem_number ");
        sql.append("           and a.article_category = ?) t1 ");
        sql.append(" where t1.no between ? and ? ");

        List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
                article.setMember(member);
                return article;
            }
        }, category, startRec, endRec);

        return articles;
    }

    /**
     * 게시글 목록 - 검색
     * @param filterCondition 카테고리, 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 게시글 목록
     */
    @Override
    public List<Article> findAll(ArticleFilterCondition filterCondition) {
        StringBuffer sql = new StringBuffer();

        sql.append("select t1.* ");
        sql.append("  from (select row_number() over (order by a.article_num desc) no, article_num, article_category, ");
        sql.append("               article_title, article_contents, attachment, mem_nickname, create_date, views, comments ");
        sql.append("          from article a, member m ");
        sql.append("         where a.mem_number = m.mem_number and ");
        sql.append("           and ");

        //분류
        sql = dynamicQuery(filterCondition, sql);

        sql.append("       ) t1 ");
        sql.append(" where t1.no between ? and ? ");

        List<Article> list = null;
        //전체 게시글
        if (StringUtils.isEmpty(filterCondition.getCategory())) {
            list = jt.query(sql.toString(), new RowMapper<Article>() {
                @Override
                public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
                    article.setMember(member);
                    return article;
                }
            }, filterCondition.getStartRec(), filterCondition.getEndRec());
        //카테고리별 게시글
        } else {
            list = jt.query(sql.toString(), new RowMapper<Article>() {
                @Override
                public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
                    article.setMember(member);
                    return article;
                }
            }, filterCondition.getCategory(), filterCondition.getStartRec(), filterCondition.getEndRec());
        }

        return list;
    }

    /**
     * 조회수 증가
     * @param articleNum 게시글번호
     * @return 조회수 증가된 게시글 수
     */
    @Override
    public int increaseViewCount(Long articleNum) {
        String sql = "update article set views = views + 1 where article_num = ? ";

        int affectedRow = jt.update(sql, articleNum);

        return affectedRow;
    }

    /**
     * 댓글수 변동
     * @param totalCountOfArticle 댓글수
     * @param articleNum 게시글번호
     * @return 댓글수 변동된 게시글 수
     */
    @Override
    public int updateCommentsCnt(Long totalCountOfArticle, Long articleNum) {
        String sql = "update article set comments = ? where article_num = ? ";

        int affectedRow = jt.update(sql, totalCountOfArticle, articleNum);

        return affectedRow;
    }

    /**
     * 게시글 수 - 전체
     * @return 게시글 수
     */
    @Override
    public int totalCount() {
        String sql = "select count(*) from article ";

        Integer cnt = jt.queryForObject(sql, Integer.class);

        return cnt;
    }

    /**
     * 게시글 수 - 카테고리
     * @param category 카테고리
     * @return 게시글 수
     */
    @Override
    public int totalCount(String category) {
        String sql = "select count(*) from article where article_category = ? ";

        Integer cnt = jt.queryForObject(sql, Integer.class, category);

        return cnt;
    }

    /**
     * 게시글 수 - 검색
     * @param filterCondition 카테고리, 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 게시글 수
     */
    @Override
    public int totalCount(ArticleFilterCondition filterCondition) {
        StringBuffer sql = new StringBuffer();

        sql.append("select count(*) ");
        sql.append("  from article a, member m ");
        sql.append(" where a.mem_number = m.mem_number ");
        sql.append("   and ");

        sql = dynamicQuery(filterCondition, sql);

        Integer cnt = 0;
        //검색된 전체 게시글 수
        if (StringUtils.isEmpty(filterCondition.getCategory())) {
            cnt = jt.queryForObject(sql.toString(), Integer.class);
        //검색된 카테고리별 게시글 수
        } else {
            cnt = jt.queryForObject(sql.toString(), Integer.class, filterCondition.getCategory());
        }

        return cnt;
    }

    /**
     * 동적 쿼리
     * @param filterCondition 카테고리, 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @param sql 쿼리
     * @return 동적 쿼리
     */
    private StringBuffer dynamicQuery(ArticleFilterCondition filterCondition, StringBuffer sql) {
        //카테고리 존재하면
        if (!StringUtils.isEmpty(filterCondition.getCategory())) {
            sql.append(" a.article_category = ? ");
        }

        //카테고리, 검색 유형, 검색어 모두 존재하면
        if (!StringUtils.isEmpty(filterCondition.getCategory()) &&
            !StringUtils.isEmpty(filterCondition.getSearchType()) &&
            !StringUtils.isEmpty(filterCondition.getKeyword())) {

            sql.append(" and ");
        }

        //검색 유형
        switch (filterCondition.getSearchType()) {
            //제목
            case "title":
                sql.append(" a.article_title like '%" + filterCondition.getKeyword() + "%' ");
                break;
            //내용
            case "contents":
                sql.append(" a.article_contents like '%" + filterCondition.getKeyword() + "%' ");
                break;
            //제목 또는 내용
            case "titleOrContents":
                sql.append(" (a.article_title like '%" + filterCondition.getKeyword() + "%' ");
                sql.append("  or a.article_contents like '%" + filterCondition.getKeyword() + "%') ");
                break;
            //닉네임
            case "nickname":
                sql.append(" m.mem_nickname like '%" + filterCondition.getKeyword() + "%' ");
                break;
            default:
        }

        return sql;
    }
}
