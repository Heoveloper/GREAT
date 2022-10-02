package com.kh.great.domain.dao.member;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class MemberDAOImpl implements MemberDAO {
    private final JdbcTemplate jt;

    /**
     * 신규 회원아이디(고객회원) 생성
     * @return 회원아이디
     */
    public Long generateMemberNumber(){
        String sql = "select mem_num.nextval from dual ";
        Long memNumber = jt.queryForObject(sql, Long.class);
        return memNumber;
    }

    /**
     * 회원가입
     *
     * @param member 가입정보
     * @return 회원아이디
     */
    @Override
    public Long join(Member member) {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("insert into member (mem_number, mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email, ");
        sql.append(" mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_latitude, mem_store_longitude, mem_store_introduce, mem_store_sns) ");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ");
        sql.append(" ?, ?, ?, ?, ?, ?, ?, ?) ");

        result = jt.update(sql.toString(), member.getMemNumber(), member.getMemType(), member.getMemId(), member.getMemPassword(), member.getMemName(), member.getMemNickname(), member.getMemEmail(),
                member.getMemBusinessnumber(), member.getMemStoreName(), member.getMemStorePhonenumber(), member.getMemStoreLocation(), member.getMemStoreLatitude(), member.getMemStoreLongitude(), member.getMemStoreIntroduce(), member.getMemStoreSns());

        return Long.valueOf(result);
    }

    /**
     * 아이디 찾기
     *
     * @param memName 이름
     * @param memEmail 이메일
     * @return 아이디, 등록일자
     */
    @Override
    public Member findByMemNameAndMemEmail (String memName, String memEmail) {
        StringBuffer sql = new StringBuffer();

        sql.append("select mem_id, mem_regtime ");
        sql.append("  from member ");
        sql.append(" where mem_name = ? ");
        sql.append("   and mem_email = ? ");

        Member findedMember = null;
        try {
            //BeanPropertyRowMapper는 매핑되는 자바클래스에 디폴트 생성자 필수!
            findedMember = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memName, memEmail);
        } catch (DataAccessException e) {
            log.info("찾고자하는 회원이 없습니다!={} {}", memName, memEmail);
            throw e;
        }

        return findedMember;
    }

    /**
     * 비밀번호 찾기
     *
     * @param memId
     * @param memEmail
     * @return
     */
    @Override
    public Member findByMemIdAndMemEmail(String memId, String memEmail) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from member ");
        sql.append(" where mem_id = ? ");
        sql.append("   and mem_email = ? ");

        Member findedMember = null;
        try {
            //BeanPropertyRowMapper는 매핑되는 자바클래스에 디폴트 생성자 필수!
            findedMember = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memId, memEmail);
        } catch (DataAccessException e) {
            log.info("찾고자하는 회원이 없습니다!={} {}", memId, memEmail);
            throw e;
        }

        return findedMember;
    }

    /**
     * 로그인
     *
     * @param memId 아이디
     * @param memPassword 비밀번호
     * @return 회원
     */
    @Override
    public Optional<Member> login(String memId, String memPassword) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * ");
        sql.append("  from member ");
        sql.append(" where mem_id = ? ");
        sql.append("   and mem_password = ? ");

        try {
            Member member = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memId, memPassword);
            return Optional.of(member);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * 조회 by 회원아이디
     *
     * @param memNumber 회원아이디
     * @return 회원정보
     */
    @Override
    public Member findByMemNumber(Long memNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("select mem_number, mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email, ");
        sql.append("mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_latitude, mem_store_longitude, ");
        sql.append("mem_store_introduce, mem_store_sns, mem_regtime, mem_lock_expiration, mem_admin ");
        sql.append("  from member ");
        sql.append(" where mem_number = ? ");

        Member findedMember = null;
        try {
            //BeanPropertyRowMapper는 매핑되는 자바클래스에 디폴트 생성자 필수!
            findedMember = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memNumber);
        } catch (DataAccessException e) {
            log.info("찾고자하는 회원이 없습니다!={}", memNumber);
        }

        return findedMember;
    }

    /**
     * 수정
     *
     * @param memNumber 회원아이디
     * @param member   수정할 정보
     */
    @Override
    public Long update(Long memNumber, Member member) {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("update member ");
        sql.append("   set mem_id = ?, ");
        sql.append("       mem_password = ?, ");
        sql.append("       mem_name = ?, ");
        sql.append("       mem_nickname = ?, ");
        sql.append("       mem_email = ?, ");
        sql.append("       mem_businessnumber = ?, ");
        sql.append("       mem_store_name = ?, ");
        sql.append("       mem_store_phonenumber = ?, ");
        sql.append("       mem_store_location = ?, ");
        sql.append("       mem_store_introduce = ?, ");
        sql.append("       mem_store_sns = ? ");
        sql.append(" where mem_number = ? ");

        result = jt.update(sql.toString(), member.getMemId(), member.getMemPassword(), member.getMemName(), member.getMemNickname(), member.getMemEmail(),
                member.getMemBusinessnumber(), member.getMemStoreName(), member.getMemStorePhonenumber(),
                member.getMemStoreLocation(), member.getMemStoreIntroduce(), member.getMemStoreSns(), memNumber);
        return Long.valueOf(result);
    }

    /**
     * 탈퇴
     *
     * @param memNumber 회원번호
     * @return 삭제건수
     */
    @Override
    public Long exit(Long memNumber) {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" delete ");
        sql.append("   from member ");
        sql.append("  where mem_number = ? ");

        result = jt.update(sql.toString(), memNumber);
        return Long.valueOf(result);
    }

    /**
     * 목록
     *
     * @return 회원전체
     */
    @Override
    public List<Member> all() {

        StringBuffer sql = new StringBuffer();
        sql.append("select member_id, email, pw, nickname, cdate, udate ");
        sql.append("  from member ");

        return jt.query(sql.toString(), new BeanPropertyRowMapper<>(Member.class));
    }

    /**
     * 이메일 중복체크
     *
     * @param email 이메일
     * @return 존재하면 true
     */
    @Override
    public Boolean dupChkOfMemberEmail(String email) {
        String sql = "select count(email) from member where email = ? ";
        Long rowCount = jt.queryForObject(sql, Long.class, email);
        return rowCount == 1 ? true : false;
    }
}
