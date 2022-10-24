package com.kh.great.web.controller.main;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.member.EmailSVCImpl;
import com.kh.great.domain.svc.member.MemberSVC;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import com.kh.great.web.api.member.FindId;
import com.kh.great.web.dto.member.*;
import com.kh.great.web.dto.product.DetailForm;
import com.kh.great.web.session.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MemberSVC memberSVC;
    final ProductSVC productSVC;
    private final UploadFileSVC uploadFileSVC;
    private final EmailSVCImpl emailSVCImpl;

    @GetMapping
    public String home(Model model) {
        List<Product> list = productSVC.today_deadline();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
        model.addAttribute("list", list);

        return "main/main";
    }

    //회원가입 화면
    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("join", new Join());

        return "member/join";    //회원가입 화면
    }

    //회원가입 처리
    @PostMapping("/join")
    public String join(
            @Valid @ModelAttribute("join") Join join,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "member/join";
        }

        //필드 검증(field error)
        //아이디 길이 8~15자
        //if (join.getMemId().length() < 8 || join.getMemId().length() > 15) {
        //    bindingResult.rejectValue("memId", null, "아이디 길이는 8~15자입니다.");
        //    return "member/join";
        //}

        //필드 검증(field error)
        //아이디 길이 8~15자
        if (request.getParameter("memId").length() < 8 || request.getParameter("memId").length() > 15) {
            bindingResult.rejectValue("memId", null, "아이디 길이는 8~15자입니다.");
            return "member/join";
        }

        //닉네임 길이 2~6자
        if (request.getParameter("memNickname").length() < 2 || request.getParameter("memNickname").length() > 6) {
            bindingResult.rejectValue("memNickname", null, "닉네임 길이는 2~6자입니다.");
            return "member/join";
        }

        //오브젝트 검증(object error)
        //비밀번호-비밀번호 확인 일치
        if (!(join.getMemPassword().equals(join.getMemPasswordCheck()))) {
            bindingResult.reject(null, "비밀번호가 일치하지 않습니다.");
            return "member/join";
        }

        Member member = new Member();
        member.setMemType(join.getMemType());
        member.setMemId(join.getMemId());
        member.setMemPassword(join.getMemPassword());
        member.setMemName(join.getMemName());
        member.setMemNickname(join.getMemNickname());
        member.setMemEmail(join.getMemEmail());
        member.setMemBusinessnumber(join.getMemBusinessnumber());
        member.setMemStoreName(join.getMemStoreName());
        member.setMemStorePhonenumber(join.getMemStorePhonenumber());
        member.setMemStoreLocation(join.getMemStoreLocation());
        member.setMemStoreLatitude(join.getMemStoreLatitude());
        member.setMemStoreLongitude(join.getMemStoreLongitude());
        member.setMemStoreIntroduce(join.getMemStoreIntroduce());
        member.setMemStoreSns(join.getMemStoreSns());
        Member joinedMember = memberSVC.join(member);

        Long id = joinedMember.getMemNumber();
        redirectAttributes.addAttribute("memNumber", id);
        return "redirect:/joinComplete/{memNumber}";  //가입완료화면
    }

    //회원가입 완료 화면
    @GetMapping("/joinComplete/{memNumber}")
    public String joinComplete(@PathVariable("memNumber") Long memNumber, Model model) {

        Member findedMember = memberSVC.findByMemNumber(memNumber);

        JoinComplete joinComplete = new JoinComplete();
        BeanUtils.copyProperties(findedMember, joinComplete);

        model.addAttribute("joinComplete", joinComplete);

        return "member/joinComplete";
    }

    //아이디 찾기 화면
    @GetMapping("/findId")
    public String findId(Model model) {
        model.addAttribute("findId", new FindId());

        return "member/findId";
    }

    //비밀번호 찾기 화면
    @GetMapping("/findPw")
    public String findPw(Model model) {
        model.addAttribute("findPw", new FindPw());

        return "member/findPw";
    }

    //비밀번호 찾기 처리
    @PostMapping("/findPw")
    public String findPw(
            @Valid @ModelAttribute("findPw") FindPw findPw,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "member/findPw";
        }

        Member findedMember = memberSVC.findByMemIdAndMemEmail(findPw.getMemId(), findPw.getMemEmail());
        //찾은 회원이 없으면
        if (findedMember == null) {
            bindingResult.rejectValue("memId", null, "아이디와 이메일이 일치하는 회원이 없습니다.");
            return "member/findPw";
        }

        log.info("findedMember = {}", findedMember);
        log.info("findedMember.getMemNumber = {}", findedMember.getMemNumber());

        redirectAttributes.addAttribute("memNumber", findedMember.getMemNumber());
        return "redirect:/resetPw/{memNumber}";
    }

    //비밀번호 재설정 화면
    @GetMapping("/resetPw/{memNumber}")
    public String resetPw(@PathVariable("memNumber") Long memNumber, Model model) {

        Member findedMember = memberSVC.findByMemNumber(memNumber);

        ResetPw resetPw = new ResetPw();
        resetPw.setMemId(findedMember.getMemId());

        model.addAttribute("resetPw", resetPw);

        return "member/resetPw";
    }

    //로그인 화면
    @GetMapping("/login")
    public String login(@ModelAttribute("login") Login login){

        return "member/login";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("login") Login login,
            BindingResult bindingResult,
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "/") String redirectUrl
    ){
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "member/login";
        }

        //회원유무
        Optional<Member> member = memberSVC.login(login.getMemId(), login.getMemPassword());
        log.info("member = {}", member);
        if(member.isEmpty()){
            bindingResult.reject(null,"회원정보를 찾을 수 없습니다.");
            return "member/login";
        }

        //세션에 회원정보 저장
        LoginMember loginMember = new LoginMember(member.get().getMemNumber(), member.get().getMemType(),
                member.get().getMemId(), member.get().getMemNickname(), member.get().getMemStoreName(), member.get().getMemAdmin());

        //세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute("loginMember", loginMember);
        session.setAttribute("memNumber", member.get().getMemNumber());
        session.setAttribute("memType", member.get().getMemType());
        session.setAttribute("memNickname", member.get().getMemNickname());
        session.setAttribute("memAdmin", member.get().getMemAdmin());

        return "redirect:" + redirectUrl;
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        //세션 조회
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/"; //메인
    }

    //상품 개별 조회
    @GetMapping("/product/{num}")
    public String findByProductNum(@PathVariable("num") Long num, Model model) {
        //1) 상품조회
        Product findedProduct = productSVC.findByProductNum(num);
        DetailForm detailForm = new DetailForm();

        BeanUtils.copyProperties(findedProduct, detailForm);

        //2) 첨부파일 조회
//        List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), num);
//        if(uploadFiles.size() > 0 ){
//            List<UploadFile> imageFiles = new ArrayList<>();
//            for (UploadFile file : uploadFiles) {
//                imageFiles.add(file);
//            }
//            detailForm.setImageFiles(imageFiles);
//        }

        List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), num);
        if(uploadFiles.size() > 0 ){
            detailForm.setImageFiles(uploadFiles);
        }
        log.info("detailForm={}",detailForm);
        model.addAttribute("form", detailForm);

        return "product/detailForm";
    }


    // 검색 목록
    @GetMapping("/searchresult")
    public  String searchresult(Model model){
        List<Product> list = productSVC.findAll();
        model.addAttribute("list", list);

        return "main/search_result";
    }

    //지역별 상품 목록
    @GetMapping("/zonning")
    @Nullable
    public String discountListDesc(Model model) {
        List<Product> list = productSVC.findAll();
        model.addAttribute("list", list);

        return "main/zonning_list_csr";
    }

    // 오늘 마감상품 전체보기
    @GetMapping("/todayDealine")
    @Nullable
    public String todayDealine(Model model) {
        List<Product> list = productSVC.today_deadline();
        model.addAttribute("list", list);

        return "main/all_list";
    }
}