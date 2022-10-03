package com.kh.great.domain.svc.member;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.member.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {
    private final MemberDAO memberDAO;

    /**
     * 회원가입
     *
     * @param member 가입정보
     * @return 회원아이디
     */
    @Override
    public Member join(Member member) {

        //회원 아이디 생성
        Long generateMemberNumber = memberDAO.generateMemberNumber();
        member.setMemNumber(generateMemberNumber);
        memberDAO.join(member);
        return memberDAO.findByMemNumber(generateMemberNumber);
    }

    /**
     * 아이디 찾기
     *
     * @param memName 이름
     * @param memEmail 이메일
     * @return 아이디, 등록일자
     */
    @Override
    public Member findByMemNameAndMemEmail(String memName, String memEmail) {
        return memberDAO.findByMemNameAndMemEmail(memName, memEmail);
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
        return memberDAO.findByMemIdAndMemEmail(memId, memEmail);
    }

    /**
     * 로그인
     *
     * @param memId       아이디
     * @param memPassword 패스워드
     * @return 회원
     */
    @Override
    public Optional<Member> login(String memId, String memPassword) {
        return memberDAO.login(memId, memPassword);
    }

    /**
     * 조회 by 회원아이디
     *
     * @param memNumber 회원아이디
     * @return 회원정보
     */
    @Override
    public Member findByMemNumber(Long memNumber) {
        return memberDAO.findByMemNumber(memNumber);
    }

    /**
     * 수정
     *
     * @param memNumber 아이디
     * @param member   수정할 정보
     * @return 수정건수
     */
    @Override
    public Long update(Long memNumber, Member member) {
        Long cnt = memberDAO.update(memNumber, member);
        log.info("수정건수={}", cnt);
        return cnt;
    }

    /**
     * 탈퇴
     *
     * @param memNumber 회원번호
     * @return 삭제건수
     */
    @Override
    public Long exit(Long memNumber) {
        Long cnt = memberDAO.exit(memNumber);
        log.info("삭제건수={}", cnt);
        return cnt;
    }

    /**
     * 목록
     *
     * @return 회원전체
     */
    @Override
    public List<Member> all() {
        return memberDAO.all();
    }

    /**
     * 아이디 중복체크
     *
     * @param memId 아이디
     * @return 존재하면 true
     */
    @Override
    public Boolean dupChkOfMemId(String memId) {
        return memberDAO.dupChkOfMemId(memId);
    }

    /**
     * 닉네임 중복체크
     *
     * @param memNickname 닉네임
     * @return 존재하면 true
     */
    @Override
    public Boolean dupChkOfMemNickname(String memNickname) {
        return memberDAO.dupChkOfMemNickname(memNickname);
    }
}
