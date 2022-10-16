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
    //타임리프를 사용하기 위한 객체를 의존성 주입으로 가져온다
    private final SpringTemplateEngine templateEngine;
    //이메일 인증 저장소
    private final EmailAuthStore emailAuthStore;

    //랜덤 인증 코드 생성
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i = 0; i < 8; i++) {
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


        String setFrom = "altruism_tap@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "GREAT 이메일 인증코드"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(authNo),  //인증 코드 생성
                "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        String authNo = createCode();
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail, authNo);
        //실제 메일 전송
        emailSender.send(emailForm);
        //이메일-인증번호 저장
        emailAuthStore.add(toEmail, authNo);

        return authNo; //인증 코드 반환
    }

    //타임리프를 이용한 context 설정
    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("member/mail", context); //mail.html
    }
}
