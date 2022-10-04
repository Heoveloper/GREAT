package com.kh.great.domain.dao.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

    @Autowired
    private MemberDAO memberDAO;

    @Test
    void resetPw() {
        Member member = new Member();
        member.setMemPassword("text1234");
        memberDAO.resetPw(6l, "text1234");
    }
}