package com.kh.great.domain.svc.member;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.member.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {
    private final MemberDAO memberDAO;

    /**
     * 회원가입
     * @param member 가입정보
     * @return 회원번호
     */
    @Override
    public Member join(Member member) {
        //회원번호 생성
        Long generateMemberNumber = memberDAO.generateMemberNumber();
        //생성한 회원번호 부여
        member.setMemNumber(generateMemberNumber);
        //회원가입
        memberDAO.join(member);

        return memberDAO.findByMemNumber(generateMemberNumber);
    }

    /**
     * 아이디 찾기
     * @param memName 이름
     * @param memEmail 이메일
     * @return 아이디, 등록일자
     */
    @Override
    public Member findByMemNameAndMemEmail(String memName, String memEmail) {

        return memberDAO.findByMemNameAndMemEmail(memName, memEmail);
    }

    /**
     * 비밀번호 찾기 (조회 by 아이디, 이메일)
     * @param memId 아이디
     * @param memEmail 이메일
     * @return 회원정보
     */
    @Override
    public Member findByMemIdAndMemEmail(String memId, String memEmail) {

        return memberDAO.findByMemIdAndMemEmail(memId, memEmail);
    }

    /**
     * 비밀번호 재설정
     * @param memNumber 회원번호
     * @param newPassword 수정할 정보
     * @return 재설정 건수
     */
    @Override
    public Long resetPw(Long memNumber, String newPassword) {

        return memberDAO.resetPw(memNumber, newPassword);
    }

    /**
     * 로그인
     * @param memId 아이디
     * @param memPassword 비밀번호
     * @return 회원정보
     */
    @Override
    public Optional<Member> login(String memId, String memPassword) {

        return memberDAO.login(memId, memPassword);
    }

    /**
     * 조회 by 회원번호
     * @param memNumber 회원번호
     * @return 회원정보
     */
    @Override
    public Member findByMemNumber(Long memNumber) {

        return memberDAO.findByMemNumber(memNumber);
    }

    /**
     * 조회 by 아이디
     * @param memId 아이디
     * @return 회원정보
     */
    @Override
    public Member findByMemId(String memId) {

        return memberDAO.findByMemId(memId);
    }

    /**
     * 수정
     * @param memNumber 회원번호
     * @param member 수정할 정보
     * @return 수정 건수
     */
    @Override
    public Long update(Long memNumber, Member member) {
        Long cnt = memberDAO.update(memNumber, member);

        return cnt;
    }

    /**
     * 탈퇴
     * @param memNumber 회원번호
     * @return 탈퇴 건수
     */
    @Override
    public Long exit(Long memNumber) {
        Long cnt = memberDAO.exit(memNumber);

        return cnt;
    }

    /**
     * 아이디 중복체크
     * @param memId 아이디
     * @return 존재하면 true
     */
    @Override
    public Boolean dupChkOfMemId(String memId) {

        return memberDAO.dupChkOfMemId(memId);
    }

    /**
     * 닉네임 중복체크
     * @param memNickname 닉네임
     * @return 존재하면 true
     */
    @Override
    public Boolean dupChkOfMemNickname(String memNickname) {

        return memberDAO.dupChkOfMemNickname(memNickname);
    }
}
