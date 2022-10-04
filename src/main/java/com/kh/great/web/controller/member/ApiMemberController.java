package com.kh.great.web.controller.member;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.svc.member.EmailSVCImpl;
import com.kh.great.domain.svc.member.MemberSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.api.member.EmailDto;
import com.kh.great.web.api.member.FindId;
import com.kh.great.web.common.EmailAuthStore;
import com.kh.great.web.dto.member.Info;
import com.kh.great.web.dto.member.Join;
import com.kh.great.web.dto.member.ResetPw;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberSVC memberSVC;
    private final EmailSVCImpl emailSVCimpl;
    private String authNum;
    private final EmailAuthStore emailAuthStore; //이메일 인증 저장소


    //아이디 중복확인
    @PostMapping("/dupChkId")
    public  ApiResponse<Object> dupChkId(@RequestBody Join join) {
        ApiResponse<Object> response = null;

        Boolean isDup = memberSVC.dupChkOfMemId(join.getMemId());
        if (isDup == false) {
            response =  ApiResponse.createApiResMsg("00", "사용가능한 아이디", isDup);
        } else {
            response =  ApiResponse.createApiResMsg("99", "중복되는 아이디 존재", isDup);
        }
        return response;
    }

    //닉네임 중복확인
    @PostMapping("/dupChkNickname")
    public  ApiResponse<Object> dupChkNn(@RequestBody Join join) {
        ApiResponse<Object> response = null;

        Boolean isDup = memberSVC.dupChkOfMemNickname(join.getMemNickname());
        if (isDup == false) {
            response =  ApiResponse.createApiResMsg("00", "사용가능한 닉네임", isDup);
        } else {
            response =  ApiResponse.createApiResMsg("99", "중복되는 닉네임 존재", isDup);
        }
        return response;
    }

    //아이디 찾기
    @PostMapping("/findId")
    public ApiResponse<Object> findId(@RequestBody FindId findId) {
        ApiResponse<Object> response = null;

        Member findedMember = memberSVC.findByMemNameAndMemEmail(findId.getMemName(), findId.getMemEmail());
        String id = findedMember.getMemId();
        LocalDateTime regtime = findedMember.getMemRegtime();
        ArrayList IdAndRegtime = new ArrayList();
        IdAndRegtime.add(id);
        IdAndRegtime.add(regtime);

        log.info("id={}", id);

        //응답메세지
        if (!StringUtils.isEmpty(id)) {
            response =  ApiResponse.createApiResMsg("00", "성공", IdAndRegtime);
        } else {
            response =  ApiResponse.createApiResMsg("99", "부합하는 아이디가 없습니다.", null);
        }
        return response;
    }

    //비밀번호 재설정
    @PatchMapping("/resetPw")
    public ApiResponse<Object> resetPw(@RequestBody ResetPw resetPw, BindingResult bindingResult) {
        ApiResponse<Object> response = null;

        //1)비밀번호 체크
        //오브젝트 검증(object error)
        //비밀번호-비밀번호 확인 일치
        if (!(resetPw.getMemPassword().equals(resetPw.getMemPasswordCheck()))) {
            bindingResult.reject(null, "비밀번호가 일치하지 않습니다.");
            response = ApiResponse.createApiResMsg("01", "비밀번호가 일치하지 않습니다.", null);
            return response;
        }

        //2)회원아이디가 존재하는지 체크
        Member findedMember = memberSVC.findByMemId(resetPw.getMemId());
        log.info("findedMember={}",findedMember);
        if (findedMember == null) {
            response = ApiResponse.createApiResMsg("99", "찾고자하는 아이디가 없습니다.", null);
            return response;
        }
        //3)비밀번호 변경
        Long updatedRow = memberSVC.resetPw(findedMember.getMemNumber(), resetPw.getMemPassword());
        if (updatedRow == 1) {
            response = ApiResponse.createApiResMsg("00", "비밀번호 재설정 성공", null);
        }
        return response;
    }

    //회원탈퇴
    @DeleteMapping("/exit")
    public ApiResponse<Object> exit(@RequestBody Info info, HttpServletRequest request) {

        Member findedMember = memberSVC.findByMemNumber(info.getMemNumber());

        HttpSession session = request.getSession(false);
        if (session != null) {

            //비밀번호 불일치시
            if (!(findedMember.getMemPassword().equals(info.getExitPwc()))) {
                return ApiResponse.createApiResMsg("99","탈퇴 실패(비밀번호 불일치)", info.getExitPwc());
            }

            session.invalidate();
        }

        Long deletedRow = memberSVC.exit(info.getMemNumber());

        return ApiResponse.createApiResMsg("00","탈퇴 성공", deletedRow);
    }

    //인증코드 발송
    @PostMapping("/mailConfirm")
    public String mailConfirm(@RequestBody EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {

        return emailSVCimpl.sendEmail(emailDto.getEmail());
    }

    //인증코드 확인
    @PostMapping("/codeConfirm")
    public  ApiResponse<Object> codeConfirm(@RequestBody EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {
        ApiResponse<Object> response = null;


        if (emailAuthStore.isExist(emailDto.getEmail(), emailDto.getCode())) {
            response =  ApiResponse.createApiResMsg("00", "코드 인증 성공", null);
            //emailAuthStore.remove(emailDto.email);
        } else {
            response =  ApiResponse.createApiResMsg("99", "코드 인증 실패", null);
        }
        return response;
    }
}
