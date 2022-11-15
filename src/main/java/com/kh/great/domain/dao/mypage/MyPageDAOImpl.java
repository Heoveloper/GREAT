package com.kh.great.domain.dao.mypage;

import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyPageDAOImpl implements MyPageDAO {
    private final JdbcTemplate jt;

    //리뷰 등록
    @Override
    public Review save(Review review) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into review (review_number, buyer_number, seller_number, content, grade) ");
        sql.append("            values (review_review_num_seq.nextval, ?, ?, ?, ?) ");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"review_number"});
                pstmt.setLong(1, review.getBuyerNumber());
                pstmt.setLong(2, review.getSellerNumber());
                pstmt.setString(3, review.getContent());
                pstmt.setLong(4, review.getGrade());
                return pstmt;
            }
        }, keyHolder);

        Long review_number = Long.valueOf(keyHolder.getKeys().get("review_number").toString());
        review.setReviewNumber(review_number);

        return review;
    }

    //리뷰 조회 by 회원번호 where 내 리뷰
    @Override
    public List<Review> findByMemNumber(Long memNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select * ");
        sql.append("    from review r, member m ");
        sql.append("   where r.seller_number = m.mem_number ");
        sql.append("     and r.buyer_number = ? ");
        sql.append("order by r.write_date desc ");

        List<Review> reviews = null;
        try {
            reviews = jt.query(sql.toString(), new RowMapper<Review>() {
                @Override
                public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Review review = (new BeanPropertyRowMapper<>(Review.class)).mapRow(rs, rowNum);
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    deal.setProduct(product);
                    review.setDeal(deal);
                    review.setMember(member);
                    return review;
                }
            }, memNumber);
        } catch (DataAccessException e) {
            log.info("존재하지 않는 회원번호입니다!");
        }

        return reviews;
    }

    //리뷰 조회 by 회원번호 where 판매자 프로필
    @Override
    public List<Review> findBySellerNumber(Long memNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select * ");
        sql.append("    from review r, member m ");
        sql.append("   where r.buyer_number = m.mem_number ");
        sql.append("     and r.seller_number = ? ");
        sql.append("order by r.write_date desc ");

        List<Review> reviews = null;
        try {
            reviews = jt.query(sql.toString(), new RowMapper<Review>() {
                @Override
                public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Review review = (new BeanPropertyRowMapper<>(Review.class)).mapRow(rs, rowNum);
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs, rowNum);
                    deal.setProduct(product);
                    review.setDeal(deal);
                    review.setMember(member);
                    return review;
                }
            }, memNumber);
        } catch (DataAccessException e) {
            log.info("존재하지 않는 회원번호입니다!");
        }

        return reviews;
    }

    //리뷰 조회 by 리뷰번호
    @Override
    public Optional<Review> findByReviewNumber(Long reviewNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from review r, member m ");
        sql.append(" where r.buyer_number = m.mem_number ");
        sql.append("   and r.review_number = ? ");

        try {
            Review review = jt.queryForObject(sql.toString(), new RowMapper<Review>() {
                @Override
                public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Review review = (new BeanPropertyRowMapper<>(Review.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    review.setMember(member);
                    return review;
                }
            }, reviewNumber);
            return Optional.of(review);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    //판매글 조회 where 판매자 프로필
    @Override
    public List<Product> findByOwnerNumber(Long ownerNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from product_info p, member m ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and p.owner_number = ? ");

        List<Product> products = null;
        try {
            products = jt.query(sql.toString(), new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    product.setMember(member);
                    return product;
                }
            }, ownerNumber);
        } catch (DataAccessException e) {
            log.info("존재하지 않는 회원번호입니다!");
        }

        return products;
    }

    //리뷰 수정
    @Override
    public int update(Long reviewNumber, Review review) {
        StringBuffer sql =new StringBuffer();

        sql.append("update review ");
        sql.append("   set content = ?, ");
        sql.append("       grade = ? ");
        sql.append(" where review_number = ? ");
        sql.append("   and buyer_number = ? ");

        int affectedRow = jt.update(sql.toString(), review.getContent(), review.getGrade(), review.getReviewNumber(), review.getBuyerNumber());

        return affectedRow;
    }

    //리뷰 삭제
    @Override
    public int deleteByReviewId(Long reviewNumber) {
        String sql = "delete from review where review_number = ? ";

        int affectedRow = jt.update(sql.toString(), reviewNumber);

        return affectedRow;
    }

    //회원 조회 by 회원번호
    @Override
    public Optional<Member> findMember(Long memNumber) {
        String sql = "select * from member where mem_number = ? ";

        try {
            Member member = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memNumber);
            return Optional.of(member);
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    //즐겨찾기 추가
    @Override
    public Bookmark addBookmark(Bookmark bookmark) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into bookmark (bookmark_number, buyer_number, seller_number) ");
        sql.append("              values (bookmark_bookmark_num_seq.nextval, ?, ?) ");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"bookmark_number"});
                pstmt.setLong(1, bookmark.getBuyerNumber());
                pstmt.setLong(2, bookmark.getSellerNumber());

                return pstmt;
            }
        }, keyHolder);

        Long bookmark_number = Long.valueOf(keyHolder.getKeys().get("bookmark_number").toString());
        bookmark.setBookmarkNumber(bookmark_number);

        return bookmark;
    }

    //즐겨찾기 조회
    @Override
    public Optional<Bookmark> findBookmarkNumber(Long bookmarkNumber) {
        String sql = "select * from bookmark where bookmark_number = ? ";

        try {
            Bookmark bookmark = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Bookmark.class), bookmarkNumber);
            return Optional.of(bookmark);
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    //즐겨찾기 삭제 where 판매자 프로필
    @Override
    public int delBookmark(Long memNumber) {
        String sql = "delete from bookmark where seller_number = ? ";

        int affectedRow = jt.update(sql.toString(), memNumber);

        return affectedRow;
    }

    //즐겨찾기 삭제 where 내 즐겨찾기
    @Override
    public int delBookmarkInMyPage(Long bookmarkNumber) {
        String sql = "delete from bookmark where bookmark_number = ? ";

        int affectedRow = jt.update(sql.toString(), bookmarkNumber);

        return affectedRow;
    }

    //즐겨찾기 회원 조회
    @Override
    public List<Bookmark> findBookmark(Long memNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select * ");
        sql.append("    from bookmark b, member m ");
        sql.append("   where b.seller_number = m.mem_number ");
        sql.append("     and b.buyer_number = ? ");
        sql.append("order by b.bookmark_number asc ");

        List<Bookmark> bookmarks = null;
        try {
            bookmarks = jt.query(sql.toString(), new RowMapper<Bookmark>() {
                @Override
                public Bookmark mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Bookmark bookmark = (new BeanPropertyRowMapper<>(Bookmark.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    bookmark.setMember(member);
                    return bookmark;
                }
            }, memNumber);
        } catch (DataAccessException e) {
            log.info("즐겨찾기 회원을 찾을 수 없습니다!");
        }

        return bookmarks;
    }

    //좋아요 추가
    @Override
    public Good addGood(Good good) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into good (good_number, mem_number, p_number) ");
        sql.append("          values (good_good_num_seq.nextval, ?, ?) ");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"good_number"});
                pstmt.setLong(1, good.getMemNumber());
                pstmt.setLong(2, good.getPNumber());
                return pstmt;
            }
        }, keyHolder);

        Long good_number = Long.valueOf(keyHolder.getKeys().get("good_number").toString());
        good.setGoodNumber(good_number);

        return good;
    }

    //좋아요 삭제 where 판매글
    @Override
    public int delGood(Long pNumber) {
        String sql = "delete from good where p_number = ? ";

        int affectedRow = jt.update(sql.toString(), pNumber);

        return affectedRow;
    }

    //좋아요 삭제 where 내 좋아요
    @Override
    public int delGoodInMyPage(Long goodNumber) {
        String sql = "delete from good where good_number = ? ";

        int affectedRow = jt.update(sql.toString(), goodNumber);

        return affectedRow;
    }

    //좋아요 회원 조회
    @Override
    public List<Good> findGoods(Long memNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select * ");
        sql.append("    from good g, product_info p ");
        sql.append("   where g.p_number = p.p_number ");
        sql.append("     and g.mem_number = ? ");
        sql.append("order by g.good_number asc ");

        List<Good> goods = null;
        try {
            goods = jt.query(sql.toString(), new RowMapper<Good>() {
                @Override
                public Good mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Good good = (new BeanPropertyRowMapper<>(Good.class)).mapRow(rs, rowNum);
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    good.setProduct(product);
                    return good;
                }
            }, memNumber);
        } catch (DataAccessException e) {
            log.info("좋아요 회원을 찾을 수 없습니다!");
        }

        return goods;
    }
}