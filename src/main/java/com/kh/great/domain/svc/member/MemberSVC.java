package com.kh.great.domain.svc.member;

import com.kh.great.domain.dao.member.Member;

import java.util.Optional;

public interface MemberSVC {
    /**
     * 회원가입
     * @param member 가입정보
     * @return 회원아이디
     */
    Member join(Member member);

    /**
     * 아이디 찾기
     * @param memName 이름
     * @param memEmail 이메일
     * @return 아이디, 등록일자
     */
    Member findByMemNameAndMemEmail(String memName, String memEmail);

    /**
     * 비밀번호 찾기 (조회 by 아이디, 이메일)
     * @param memId 아이디
     * @param memEmail 이메일
     * @return 회원정보
     */
    Member findByMemIdAndMemEmail(String memId, String memEmail);

    /**
     * 비밀번호 재설정
     * @param memNumber 회원번호
     * @param newPassword 수정할 정보
     * @return 재설정건수
     */
    Long resetPw(Long memNumber, String newPassword);

    /**
     * 로그인
     * @param memId 아이디
     * @param memPassword 비밀번호
     * @return 회원정보
     */
    Optional<Member> login(String memId, String memPassword);

    /**
     * 조회 by 회원번호
     * @param memNumber 회원번호
     * @return 회원정보
     */
    Member findByMemNumber(Long memNumber);

    /**
     * 조회 by 회원아이디
     * @param memId 아이디
     * @return 회원정보
     */
    Member findByMemId(String memId);

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
     * 아이디 중복체크
     * @param memId 아이디
     * @return 존재하면 true
     */
    Boolean dupChkOfMemId(String memId);

    /**
     * 닉네임 중복체크
     * @param memNickname 닉네임
     * @return 존재하면 true
     */
    Boolean dupChkOfMemNickname(String memNickname);
}
