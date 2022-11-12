package com.kh.great.domain.dao.product;

import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.dao.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {
    private final JdbcTemplate jt;

    //상품 등록
    @Override
    public Long save(Product product) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into product_info (p_number, owner_number, p_title, p_name, deadline_time, category, total_count, remain_count, normal_price, sale_price, discount_rate, payment_option, detail_info) ");
        sql.append("                  values (product_p_num_seq.nextval, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD\"T\"HH24:MI'), ?, ?, ?, ?, ?, ?, ?, ?) ");

        log.info("sql = {}", sql);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"p_number"});
                log.info("product.getOwnerNumber() = {}", product.getOwnerNumber());
                pstmt.setLong(1, product.getOwnerNumber());
                pstmt.setString(2, product.getPTitle());
                pstmt.setString(3, product.getPName());
                pstmt.setString(4, product.getDeadlineTime());
                pstmt.setString(5, product.getCategory());
                pstmt.setInt(6, product.getTotalCount());
                pstmt.setInt(7, product.getTotalCount());
                pstmt.setInt(8, product.getNormalPrice());
                pstmt.setInt(9, product.getSalePrice());
                pstmt.setInt(10, (product.getNormalPrice()-product.getSalePrice())*100/product.getNormalPrice());
                pstmt.setString(11, product.getPaymentOption());
                pstmt.setString(12, product.getDetailInfo());
                return pstmt;
            }
        }, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("p_number").toString());
    }

    //상품 조회
    @Override
    public Product findByProductNum(Long pNum) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from product_info p, member m ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and p.p_number = ? ");

        Product product = null;

        try {
            product = jt.queryForObject(sql.toString(), new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    product.setMember(member);
                    return product;
                }
            }, pNum);
        } catch (DataAccessException e) {
            log.info("조회할 상품이 없습니다! 상품번호 = {}", pNum);
        }

        return product;
    }

    //상품 수정
    @Override
    public int update(Long pNum, Product product) {
        int result = 0;
        StringBuffer sql = new StringBuffer();

        sql.append("update product_info ");
        sql.append("   set p_title = ?, ");
        sql.append("       p_name = ?, ");
        sql.append("       deadline_time = TO_DATE(?, 'YYYY-MM-DD\"T\"HH24:MI'), ");
        sql.append("       category = ?, ");
        sql.append("       remain_count = ?, ");
        sql.append("       normal_price = ?, ");
        sql.append("       sale_price = ?, ");
        sql.append("       discount_rate = ?, ");
        sql.append("       payment_option = ?, ");
        sql.append("       detail_info = ?, ");
        sql.append("       u_date = sysdate ");
        sql.append(" where p_number = ? ");

        result = jt.update(sql.toString(), product.getPTitle(), product.getPName(), product.getDeadlineTime(), product.getCategory(), product.getRemainCount(), product.getNormalPrice(), product.getSalePrice(), (product.getNormalPrice()-product.getSalePrice())*100/product.getNormalPrice(), product.getPaymentOption(), product.getDetailInfo(), pNum);

        return result;
    }

    //상품 삭제
    @Override
    public int deleteByProductNum(Long pNum) {
        String sql = "delete from product_info where p_number = ? ";

        return jt.update(sql, pNum);
    }

    //상품 목록
    @Override
    public List<Product> findAll() {
        StringBuffer sql = new StringBuffer();

        sql.append("  select p_number, p_name, discount_rate, sale_price, normal_price, deadline_time ");
        sql.append("    from product_info");
        sql.append("   where deadline_time > sysdate ");
        sql.append("     and remain_count > 0 ");
        sql.append("order by r_date desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    //오늘의 마감 할인 상품 목록
    @Override
    public List<Product> today_deadline() {
        StringBuffer sql = new StringBuffer();

        sql.append("  select * ");
        sql.append("    from product_info ");
        sql.append("   where to_char(deadline_time, 'yyyy-mm-dd') = to_char(sysdate, 'yyyy-mm-dd') ");
        sql.append("     and deadline_time > sysdate ");
        sql.append("     and remain_count > 0 ");
        sql.append("     and p_status = 0 ");
        sql.append("order by deadline_time asc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    //상품 관리
    public List<Product> manage(Long ownerNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select * ");
        sql.append("    from product_info p, member m ");
        sql.append("   where p.owner_number = m.mem_number ");
        sql.append("     and m.mem_type = 'owner' ");
        sql.append("     and p.owner_number = ? ");
        sql.append("     and p.r_date between '2022-09-30' and '2022-10-03' ");
        sql.append("order by r_date desc ");

        List<Product> result = null;
        try {
            result = jt.query(sql.toString(), new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    product.setMember(member);
                    return product;
                }
            }, ownerNumber);
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다! 회원번호 = {}", ownerNumber);
        }

        return result;
    }

    //상품 관리
    public List<Product> pManage(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam("sell_status") Integer sell_status, @RequestParam("history_start_date") String history_start_date, @RequestParam("history_end_date") String history_end_date) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from product_info p, member m ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and m.mem_type = 'owner' ");
        sql.append("   and p.owner_number = " + ownerNumber + "  ");
        sql.append("   and p.r_date between '" + history_start_date + "' and to_date('" + history_end_date + "', 'YYYY-MM-DD') + 1 ");

        if (sell_status == 0 || sell_status == 1) {
            sql.append("and p_status = " + sell_status + "  ");
        }
        sql.append("order by r_date desc ");

        List<Product> result = null;
        try {
            result = jt.query(sql.toString(), new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    product.setMember(member);
                    return product;
                }
            });
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다! 회원번호 = {}", ownerNumber);
        }

        return result;
    }

    //상품 관리: 상품 판매 상태 변경
    @Override
    public int pManage_status_update(Long pNum, Integer pStatus) {
        int result = 0;
        StringBuffer sql = new StringBuffer();

        sql.append("update product_info ");
        sql.append("   set p_status = ? ");
        sql.append(" where p_number = ? ");

        result = jt.update(sql.toString(), pStatus, pNum);

        return result;
    }

    //판매 내역
    public List<Product> saleList(Long ownerNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("select *");
        sql.append("  from product_info p, member m, deal d ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and d.p_number = p.p_number ");
        sql.append("   and m.mem_type = 'owner' ");
        sql.append("   and p.owner_number = 9 ");

        List<Product> result = null;
        try {
            result = jt.query(sql.toString(), new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs, rowNum);
                    product.setMember(member);
                    product.setDeal(deal);

                    return product;
                }
            });
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다! 회원번호 = {}", ownerNumber);
        }

        return result;
    }

    //판매 내역 CSR
    public List<Product> pSaleList(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam("pickUp_status") Integer pickUp_status,  @RequestParam("history_start_date") String history_start_date, @RequestParam("history_end_date") String history_end_date) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from (select * ");
        sql.append("          from member m, deal d ");
        sql.append("         where m.mem_number = d.buyer_number) t1, product_info p ");
        sql.append(" where t1.p_number = p.p_number ");
        sql.append("   and t1.seller_number = " + ownerNumber + "  ");
        sql.append("   and t1.orderdate between '" + history_start_date + "' and to_date('" + history_end_date + "', 'YYYY-MM-DD') + 1 ");

        if (pickUp_status == 0 || pickUp_status == 1) {
            sql.append("and t1.pickup_status = " + pickUp_status + "  ");
        }
        sql.append("order by t1.orderdate desc ");

        List<Product> result = null;
        try {
            result = jt.query(sql.toString(), new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                    Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs, rowNum);
                    product.setMember(member);
                    product.setDeal(deal);

                    return product;
                }
            });
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다! 회원번호 = {}", ownerNumber);
        }

        return result;
    }

    //판매 내역: 상품 픽업 상태 변경
    @Override
    public int pickUP_status_update(Long pNum, Integer pickStatus) {
        int result = 0;
        StringBuffer sql = new StringBuffer();

        sql.append("update deal ");
        sql.append("   set pickup_status = ? ");
        sql.append(" where p_number = ? ");

        result = jt.update(sql.toString(), pickStatus, pNum);

        return result;
    }

    //상품 최신순 목록
    @Override
    public List<Product> recentList(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from product_info p, member m ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and p.deadline_time > sysdate ");
        sql.append("   and p.remain_count > 0 ");
        sql.append("   and p_status = 0 ");
        sql.append("   and m.mem_store_location like '%" + zone + "%' ");

        if (!category.equals("전체")) {
            sql.append("and p.category like '%" + category + "%' ");
        }
        sql.append("order by r_date desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    //상품 높은 할인순 목록
    @Override
    public List<Product> discountListDesc(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from product_info p, member m ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and p.deadline_time > sysdate ");
        sql.append("   and p.remain_count > 0 ");
        sql.append("   and p_status = 0 ");
        sql.append("   and m.mem_store_location like '%" + zone + "%' ");

        if (!category.equals("전체")) {
            sql.append("and p.category like '%" + category + "%' ");
        }
        sql.append("order by discount_rate desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    //상품 낮은 가격순 목록
    @Override
    public List<Product> priceList(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from product_info p, member m ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and p.deadline_time > sysdate ");
        sql.append("   and p.remain_count > 0 ");
        sql.append("   and p_status = 0 ");
        sql.append("   and m.mem_store_location like '%" + zone + "%' ");

        if (!category.equals("전체")) {
            sql.append("and p.category like '%" + category + "%' ");
        }
        sql.append("order by sale_price asc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    //상품 높은 가격순 목록
    @Override
    public List<Product> priceListDesc(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from product_info p, member m ");
        sql.append(" where p.owner_number = m.mem_number ");
        sql.append("   and p.deadline_time > sysdate ");
        sql.append("   and p.remain_count > 0 ");
        sql.append("   and p_status = 0 ");
        sql.append("   and m.mem_store_location like '%" + zone + "%' ");

        if (!category.equals("전체")) {
            sql.append("and p.category like '%" + category + "%' ");
        }
        sql.append("order by sale_price desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    //상품 검색
    @Override
    public List<Product> search(@RequestParam("searchKeyword") String searchKeyword) {
        StringBuffer sql = new StringBuffer();

        sql.append("  select * ");
        sql.append("    from product_info ");
        sql.append("   where p_name like '%" + searchKeyword + "%' or p_title like '%" + searchKeyword + "%' ");
        sql.append("     and deadline_time > sysdate ");
        sql.append("     and remain_count > 0 ");
        sql.append("     and p_status = 0 ");
        sql.append("order by r_date desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }
}