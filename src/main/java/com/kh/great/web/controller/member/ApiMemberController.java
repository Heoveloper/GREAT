package com.kh.great.web.controller.member;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.svc.member.EmailSVCImpl;
import com.kh.great.domain.svc.member.MemberSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.dto.member.EmailDto;
import com.kh.great.web.dto.member.FindId;
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
    private final EmailAuthStore emailAuthStore; //이메일 인증 저장소

    //아이디 중복확인
    @PostMapping("/dupChkId")
    public ApiResponse<Object> dupChkId(@RequestBody Join join) {
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
    public ApiResponse<Object> dupChkNn(@RequestBody Join join) {
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

        if (!StringUtils.isEmpty(id)) {
            response =  ApiResponse.createApiResMsg("00", "성공", IdAndRegtime);
        } else {
            response =  ApiResponse.createApiResMsg("99", "해당하는 아이디를 찾을 수 없습니다.", null);
        }

        return response;
    }

    //비밀번호 재설정
    @PatchMapping("/resetPw")
    public ApiResponse<Object> resetPw(@RequestBody ResetPw resetPw, BindingResult bindingResult) {
        ApiResponse<Object> response = null;

        //오브젝트 검증
        //비밀번호-비밀번호 확인 일치
        if (!(resetPw.getMemPassword().equals(resetPw.getMemPasswordCheck()))) {
            bindingResult.reject(null, "비밀번호가 일치하지 않습니다.");
            response = ApiResponse.createApiResMsg("01", "비밀번호가 일치하지 않습니다.", resetPw.getMemPasswordCheck());

            return response;
        }

        //아이디 존재 여부 확인
        Member findedMember = memberSVC.findByMemId(resetPw.getMemId());
        log.info("findedMember = {}", findedMember);
        //존재하지 않으면
        if (findedMember == null) {
            response = ApiResponse.createApiResMsg("99", "아이디를 찾을 수 없습니다.", resetPw.getMemPasswordCheck());

            return response;
        }

        //비밀번호 재설정
        Long updatedRow = memberSVC.resetPw(findedMember.getMemNumber(), resetPw.getMemPassword());

        //재설정 성공시
        if (updatedRow == 1) {
            response = ApiResponse.createApiResMsg("00", "비밀번호 재설정 성공", resetPw.getMemPasswordCheck());
        }

        return response;
    }

    //회원탈퇴
    @DeleteMapping("/exit")
    public ApiResponse<Object> exit(@RequestBody Info info, BindingResult bindingResult, HttpServletRequest request) {
        Member findedMember = memberSVC.findByMemNumber(info.getMemNumber());

        //세션 조회
        HttpSession session = request.getSession(false);
        //세션이 존재하면
        if (session != null) {
            //확인 비밀번호가 일치하지 않으면
            if (!(findedMember.getMemPassword().equals(info.getExitPwc()))) {
                log.info("pwAndPwc = {} {}", findedMember.getMemPassword(), info.getExitPwc());
                bindingResult.reject(null, "비밀번호가 일치하지 않습니다.");

                return ApiResponse.createApiResMsg("01", "비밀번호가 일치하지 않습니다.", info.getExitPwc());
            }

            session.invalidate();
        }

        //회원탈퇴
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
    public ApiResponse<Object> codeConfirm(@RequestBody EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {
        ApiResponse<Object> response = null;

        //이메일 존재 여부 확인
        //이메일이 존재하고 인증코드가 같으면
        if (emailAuthStore.isExist(emailDto.getEmail(), emailDto.getCode())) {
            response =  ApiResponse.createApiResMsg("00", "코드 인증 성공", null);
            //인증 후 같은 인증코드 재사용 불가하도록 인증코드 삭제
            emailAuthStore.remove(emailDto.email);
        } else {
            response =  ApiResponse.createApiResMsg("99", "코드 인증 실패", null);
        }

        return response;
    }
}