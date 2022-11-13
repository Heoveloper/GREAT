package com.kh.great.domain.svc.member;

import com.kh.great.web.common.EmailAuthStore;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailSVCImpl {
    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    //타임리프를 사용하기 위한 객체를 의존성 주입으로 가져온다.
    private final SpringTemplateEngine templateEngine;
    //이메일 인증 저장소
    private final EmailAuthStore emailAuthStore;

    //랜덤 인증코드 생성
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }

        return key.toString();
    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email, String authNo) throws MessagingException, UnsupportedEncodingException {
        String setFrom = "altruism_tap@naver.com"; //보내는 사람(EmailConfig 내부에 설정한 본인의 이메일 주소)
        String toEmail = email; //받는 사람
        String title = "GREAT 이메일 인증코드"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom(setFrom); //보내는 사람 이메일 설정
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //받는 사람 이메일 설정
        message.setSubject(title); //제목 설정
        message.setText(setContext(authNo), "utf-8", "html"); //인증코드 설정

        return message;
    }

    //메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        //랜덤 생성한 인증코드
        String authNo = createCode();
        //작성한 메일 양식
        MimeMessage emailForm = createEmailForm(toEmail, authNo);
        //메일 전송
        emailSender.send(emailForm);
        //이메일, 인증코드 저장
        emailAuthStore.add(toEmail, authNo);

        return authNo; //전송한 인증코드 반환
    }

    //타임리프를 활용한 Context 설정
    private String setContext(String code) {
        Context context = new Context();

        context.setVariable("code", code);

        return templateEngine.process("member/mail", context); //mail.html
    }
}
