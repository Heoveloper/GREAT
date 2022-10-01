package com.kh.great.domain.dao.member;

import java.util.List;
import java.util.Optional;

public interface MemberDAO {
    /**
     * 신규 회원번호 생성
     * @return 회원번호
     */
    Long generateMemberNumber();

    /**
     * 회원가입
     * @param member 가입정보
     * @return 회원번호
     */
    Long join(Member member);

    /**
     * 아이디 찾기
     * @param memName 이름
     * @param memEmail 이메일
     * @return 아이디, 등록일자
     */
    Member findByMemNameAndMemEmail(String memName, String memEmail);

    /**
     * 비밀번호 찾기
     * @param memId
     * @param memEmail
     * @return
     */
    Member findByMemIdAndMemEmail(String memId, String memEmail);

    /**
     * 로그인
     * @param memId 아이디
     * @param memPassword 비밀번호
     * @return 회원
     */
    Optional<Member> login(String memId, String memPassword);

    /**
     * 조회 by 회원번호
     * @param memNumber 회원번호
     * @return 회원정보
     */
    Member findByMemNumber(Long memNumber);

    /**
     * 수정
     * @param memNumber 회원번호
     * @param member  수정할 정보
     * @return 수정건수
     */
    Long update(Long memNumber, Member member);


    /**
     * 탈퇴
     * @param memNumber 회원번호
     * @return 삭제건수
     */
    Long exit(Long memNumber);

    /**
     * 목록
     * @return 회원전체
     */
    List<Member> all();

    /**
     * 이메일 중복체크
     * @param email 이메일
     * @return 존재하면 true
     */
    Boolean dupChkOfMemberEmail(String email);
}
