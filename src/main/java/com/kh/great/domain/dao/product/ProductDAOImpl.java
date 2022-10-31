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

        sql.append("insert into product_info(p_number, owner_number, p_title, p_name, DEADLINE_TIME, CATEGORY, TOTAL_COUNT, REMAIN_COUNT ,NORMAL_PRICE, SALE_PRICE, DISCOUNT_RATE, PAYMENT_OPTION, DETAIL_INFO ) ");
        sql.append("values(product_p_number_seq.nextval, ?, ?, ?, TO_DATE(?,'YYYY-MM-DD\"T\"HH24:MI'), ?, ?, ?, ?, ?, ?, ?, ?) ");

        log.info("sql={}", sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"p_number"});
                log.info("product.getOwnerNumber()={}", product.getOwnerNumber());
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

        return  Long.valueOf(keyHolder.getKeys().get("p_number").toString());
    }

    //상품 조회
    @Override
    public Product findByProductNum(Long pNum) {
        StringBuffer sql = new StringBuffer();
        sql.append("select  *  ");
        sql.append("from product_info P, member M ");
        sql.append("where p.owner_number = m.mem_number and p.p_number=? ");

        Product product = null;

        try {
            product= jt.queryForObject(sql.toString(),new RowMapper<Product>(){
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
                    product.setMember(member);
//                    log.info("product={}", product);
                    return product;
                }
            },pNum);
        }catch (DataAccessException e) {
            log.info("조회할 상품이 없습니다. 상품번호={}", pNum);
//            e.printStackTrace();
//            return Optional.empty();
        }
        return product;
    }

    //상품 수정
    @Override
    public int update(Long pNum, Product product) {
        int result = 0;
        StringBuffer sql = new StringBuffer();

        sql.append("update product_info ");
        sql.append("SET p_title = ?, P_NAME = ?, DEADLINE_TIME = TO_DATE(?,'YYYY-MM-DD\"T\"HH24:MI'), CATEGORY = ?, REMAIN_COUNT = ?, NORMAL_PRICE = ?, SALE_PRICE = ?, DISCOUNT_RATE = ?, PAYMENT_OPTION = ?, detail_info = ?, u_date=sysdate ");
        sql.append("WHERE p_number = ? ");

        result=jt.update(sql.toString(), product.getPTitle(), product.getPName(), product.getDeadlineTime(), product.getCategory(), product.getRemainCount(), product.getNormalPrice(), product.getSalePrice(), (product.getNormalPrice()-product.getSalePrice())*100/product.getNormalPrice(), product.getPaymentOption(), product.getDetailInfo(), pNum );

        return result;
    }

    //상품 목록
    @Override
    public List<Product> findAll() {
        StringBuffer sql = new StringBuffer();

        sql.append("select p_number, p_name, DISCOUNT_RATE, SALE_PRICE, NORMAL_PRICE, DEADLINE_TIME ");
        sql.append(" from product_info");
        sql.append(" where deadline_time > sysdate and REMAIN_COUNT > 0 ");
        sql.append(" order by R_DATE desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
        return result;
    }

    //상품 삭제
    @Override
    public int deleteByProductNum(Long pNum) {
        String sql = "delete from product_info where P_NUMBER=? ";
        return jt.update(sql, pNum);
    }

    //오늘 마감 할인 상품
    @Override
    public List<Product> today_deadline() {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("from product_info ");
        sql.append("where to_char(deadline_time, 'yyyy-mm-dd') = to_char(sysdate, 'yyyy-mm-dd') and deadline_time>sysdate and REMAIN_COUNT >0 and p_status=0 ");
        sql.append("order by deadline_time asc " );

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
        return result;
    }

    //상품 관리
    public List<Product> manage(Long ownerNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("from product_info P, member M ");
        sql.append("where p.owner_number = m.mem_number and m.mem_type='owner' and p.owner_number=? ");
        sql.append("and p.r_date between '2022-09-30' and '2022-10-03' ");
        sql.append("order by R_DATE desc " );

        List<Product> result =null;
        try {
            result= jt.query(sql.toString(),new RowMapper<Product>(){
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
                    product.setMember(member);
                    return product;
                }
            },ownerNumber);
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다. 회원번호={}", ownerNumber);
        }
        return result;
    }

    //상품 관리
    public List<Product> pManage(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam ("sell_status") Integer sell_status, @RequestParam ("history_start_date") String history_start_date, @RequestParam ("history_end_date") String history_end_date) {
        StringBuffer sql = new StringBuffer();
        System.out.println("ownerNumber = " + ownerNumber + ", sell_status = " + sell_status + ", history_start_date = " + history_start_date + ", history_end_date = " + history_end_date);
        sql.append("select * ");
        sql.append("from product_info P, member M ");
        sql.append("where p.owner_number = m.mem_number and m.mem_type='owner' and p.owner_number="+ownerNumber+"  ");
        sql.append("and p.r_date between '" + history_start_date + "' and to_date('" + history_end_date+"','YYYY-MM-DD')+1 ");

        if(sell_status==0||sell_status==1) {
            sql.append("and p_status=" + sell_status + " ");
        }
        sql.append("order by R_DATE desc " );

        System.out.println("sql = " + sql);
        List<Product> result =null;
        try {
            result= jt.query(sql.toString(),new RowMapper<Product>(){
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
                    product.setMember(member);
                    return product;
                }
            });
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다. 회원번호={}", ownerNumber);
        }
        return result;
    }

    //판매 관리 화면에서 각 상품 판매 상태 변경하기
    @Override
    public int pManage_status_update(Long pNum, Integer pStatus) {
        int result = 0;
        StringBuffer sql = new StringBuffer();

        sql.append("update product_info ");
        sql.append("   SET p_status=? ");
        sql.append("WHERE p_number = ? ");

        result=jt.update(sql.toString(), pStatus, pNum );

        return result;
    }

    //판매 내역
    public List<Product> saleList(Long ownerNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("select *");
        sql.append("from product_info P, member M, Deal D ");
        sql.append("where p.owner_number = m.mem_number and d.p_Number=p.p_Number and m.mem_type='owner' and p.owner_number=9 ");

        List<Product> result =null;
        try {
            result= jt.query(sql.toString(),new RowMapper<Product>(){
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
                    Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs,rowNum);
                    product.setMember(member);
                    product.setDeal(deal);

                    return product;
                }
            });
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다. 회원번호={}", ownerNumber);
        }
        return result;
    }

    //판매 내역 csr
    public List<Product> pSaleList(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam ("pickUp_status") Integer pickUp_status,  @RequestParam ("history_start_date") String history_start_date, @RequestParam ("history_end_date") String history_end_date) {
        StringBuffer sql = new StringBuffer();
        System.out.println("ownerNumber = " + ownerNumber + ", pickUp_status = " + pickUp_status + ", history_start_date = " + history_start_date + ", history_end_date = " + history_end_date);
        sql.append("select * ");
        sql.append("from(select * ");
        sql.append("from member m, deal d ");
        sql.append("where m.mem_number=d.buyer_number) t1, product_info p ");
        sql.append("where t1.p_number=p.p_number and t1.seller_number="+ownerNumber+" ");
        sql.append("and t1.orderdate between '" + history_start_date + "' and to_date('" + history_end_date+"','YYYY-MM-DD')+1 ");

        if(pickUp_status==0||pickUp_status==1) {
            sql.append("and t1.pickup_status=" + pickUp_status + " ");
        }
        sql.append("order by t1.orderdate desc " );

        System.out.println("sql = " + sql);
        List<Product> result =null;
        try {
            result= jt.query(sql.toString(),new RowMapper<Product>(){
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
                    Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs,rowNum);
                    product.setMember(member);
                    product.setDeal(deal);

                    log.info("product={}", product);
                    return product;
                }
            });
        } catch (DataAccessException e) {
            log.info("조회할 회원이 없습니다. 회원번호={}", ownerNumber);
        }
        return result;
    }
    // 판매 내역 화면에서 각 상품 픽업 상태 변경하기
    @Override
    public int pickUP_status_update(Long pNum, Integer pickStatus) {
        int result = 0;
        StringBuffer sql = new StringBuffer();

        sql.append("update deal ");
        sql.append("   SET pickup_status=? ");
        sql.append("WHERE p_number = ? ");

        result=jt.update(sql.toString(), pickStatus, pNum );

        return result;
    }

    //------------------------------
    // 상품 최신순 목록
    @Override
    public List<Product> recentList(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();
        sql.append("select * ");
        sql.append("from product_info P, member M ");
        sql.append("where p.owner_number= m.mem_number and P.deadline_time>sysdate and P.REMAIN_COUNT >0 and p_status=0 ");
        sql.append("AND m.mem_store_location LIKE '%"+ zone+"%' ");
        if (!category.equals("전체")) {
            sql.append("and p.category like '%"+category+"%' ");
        }
        sql.append(" order by R_DATE desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
        System.out.println("sql => "+ sql);
//        System.out.println(result);
        return result;
    }

    // 상품 높은 할인순 목록
    @Override
    public List<Product> discountListDesc(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();
        sql.append("select * ");
        sql.append("from product_info P, member M ");
        sql.append("where p.owner_number= m.mem_number and P.deadline_time>sysdate and P.REMAIN_COUNT >0 and p_status=0  ");
        sql.append("AND m.mem_store_location LIKE '%"+ zone+"%' ");
        if (!category.equals("전체")) {
            sql.append("and p.category like '%"+category+"%' ");
        }
        sql.append(" order by DISCOUNT_RATE desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    // 상품 낮은 가격순 목록
    @Override
    public List<Product> priceList(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();
        sql.append("select * ");
        sql.append("from product_info P, member M ");
        sql.append("where p.owner_number= m.mem_number and P.deadline_time>sysdate and P.REMAIN_COUNT >0 and p_status=0 ");
        sql.append("AND m.mem_store_location LIKE '%"+ zone+"%' ");
        if (!category.equals("전체")) {
            sql.append("and p.category like '%"+category+"%' ");
        }
        sql.append(" order by SALE_PRICE asc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    // 상품 높은 가격순 목록
    @Override
    public List<Product> priceListDesc(@RequestParam Map<String, Object> allParameters) {
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        StringBuffer sql = new StringBuffer();
        sql.append("select * ");
        sql.append("from product_info P, member M ");
        sql.append("where p.owner_number= m.mem_number and P.deadline_time>sysdate and P.REMAIN_COUNT >0 and p_status=0 ");
        sql.append("AND m.mem_store_location LIKE '%"+ zone+"%' ");
        if (!category.equals("전체")) {
            sql.append("and p.category like '%"+category+"%' ");
        }
        sql.append(" order by SALE_PRICE desc ");

        List<Product> result = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));

        return result;
    }

    //-------------------------------------------------------------------------------------
    // 상품 검색
    @Override
    public List<Product> search(@RequestParam ("searchKeyword") String searchKeyword) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * ");
        sql.append("from product_info ");
        sql.append("where p_name like '%"+searchKeyword+"%' or p_title like '%"+searchKeyword+"%' ");
        sql.append("and deadline_time>sysdate and REMAIN_COUNT >0 and p_status=0 ");
        sql.append(" order by R_DATE desc ");

        List<Product> result= jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
        return result;
    }
}