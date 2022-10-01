package com.kh.great.domain.dao;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.member.MemberDAO;
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
        log.info("id={}", member.getMemId());
    }
}