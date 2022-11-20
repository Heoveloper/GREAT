package com.kh.great.domain.dao.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {
    @Autowired
    private MemberDAO memberDAO;

    @Test
    @DisplayName("아이디 찾기")
    void findByMemNameAndMemEmail() {
        Member member = memberDAO.findByMemNameAndMemEmail("허준혁", "test333@test.com");
        log.info("id = {}", member.getMemId());
    }

    @Test
    @DisplayName("비밀번호 재설정")
    void resetPw() {
        Member member = new Member();
        member.setMemPassword("text1234");
        memberDAO.resetPw(6l, "text1234");
    }
}