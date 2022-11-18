package com.kh.great.web.controller.notice;

import com.kh.great.domain.common.paging.FindCriteria;
import com.kh.great.domain.dao.notice.BbsFilterCondition;
import com.kh.great.domain.dao.notice.Notice;
import com.kh.great.domain.svc.notice.NoticeSVC;
import com.kh.great.web.dto.notice.DetailForm;
import com.kh.great.web.dto.notice.EditForm;
import com.kh.great.web.dto.notice.ListForm;
import com.kh.great.web.dto.notice.WriteForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeSVC noticeSVC;

    @Autowired
    @Qualifier("fc10")
    private FindCriteria fc;

    //공지사항 등록 화면
    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("writeForm", new WriteForm());

        return "notice/noticeWriteForm";
    }

    //공지사항 등록 처리
    @PostMapping("/write")
    public String write(@Valid @ModelAttribute WriteForm writeForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes
    ) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return "notice/noticeWriteForm";
        }

        Notice notice = new Notice();
        BeanUtils.copyProperties(writeForm, notice);

        Notice write = noticeSVC.write(notice);

        Long noticeId = write.getNoticeId();
        redirectAttributes.addAttribute("id", noticeId);
        return "redirect:/notice/{id}"; //공지사항 조회 화면
    }

    //공지사항 조회 화면
    @GetMapping("/{id}")
    public String read(@PathVariable("id") Long noticeId, DetailForm detailForm, Model model) {
        Notice readNotice = noticeSVC.read(noticeId);

        BeanUtils.copyProperties(readNotice, detailForm);

        model.addAttribute("detailForm", detailForm);

        return "notice/noticeViewForm";
    }

    //공지사항 수정 화면
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long noticeId, Model model) {
        Notice modifyNotice = noticeSVC.read(noticeId);
        EditForm editForm = new EditForm();
        BeanUtils.copyProperties(modifyNotice, editForm);

        model.addAttribute("editForm", editForm);

        return "notice/noticeModifyForm";
    }

    //공지사항 수정 처리
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long noticeId,
                       @Valid @ModelAttribute("editForm") EditForm editForm,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes
    ) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return "notice/noticeModifyForm";
        }

        Notice notice = new Notice();
        BeanUtils.copyProperties(editForm, notice);
        noticeSVC.update(noticeId, notice);

        redirectAttributes.addAttribute("id", noticeId);
        return "redirect:/notice/{id}"; //공지사항 조회 화면
    }

    //공지사항 삭제 처리
    @GetMapping("/{id}/del")
    public String delete(@PathVariable("id") Long noticeId) {
        noticeSVC.delete(noticeId);

        return "redirect:/notice/list"; //공지사항 목록 화면
    }

    //공지사항 목록 화면
    @GetMapping({"/list", "/list/{reqPage}", "/list/{reqPage}//", "/list/{reqPage}/{searchType}/{keyword}"})
    public String listAndReqPage(@PathVariable(required = false) Optional<Integer> reqPage,
                                 @PathVariable(required = false) Optional<String> searchType,
                                 @PathVariable(required = false) Optional<String> keyword,
                                 Model model
    ) {
        log.info("/list 요청 = {}, {}, {}, {}", reqPage, searchType, keyword);

        //Criteria 값 설정
        fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청 없으면 1
        fc.setSearchType(searchType.orElse(""));  //검색 유형
        fc.setKeyword(keyword.orElse(""));        //검색어

        List<Notice> list = null;
        //검색 유형과 검색어 모두 존재하면
        if (searchType.isPresent() && keyword.isPresent()) {
            BbsFilterCondition filterCondition = new BbsFilterCondition(fc.getRc().getStartRec(), fc.getRc().getEndRec(), searchType.get(), keyword.get());
            fc.setTotalRec(noticeSVC.totalCount(filterCondition));
            fc.setSearchType(searchType.get());
            fc.setKeyword(keyword.get());
            list = noticeSVC.findAll(filterCondition);
        //검색 유형과 검색어 중 하나라도 없으면
        } else {
            //총레코드수
            fc.setTotalRec(noticeSVC.totalCount());
            list = noticeSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
        }

        List<ListForm> partOfList = new ArrayList<>();
        for (Notice notice : list) {
            ListForm listForm = new ListForm();
            BeanUtils.copyProperties(notice, listForm);
            partOfList.add(listForm);
        }

        model.addAttribute("listForm", partOfList);
        model.addAttribute("fc", fc);

        return "notice/noticeMainForm";
    }

    //검증 오류 메세지
    private Map<String, String> getErrMsg(BindingResult bindingResult) {
        Map<String, String> errmsg = new HashMap<>();

        bindingResult.getAllErrors().stream().forEach(objectError -> {
            errmsg.put(objectError.getCodes()[0], objectError.getDefaultMessage());
        });

        return errmsg;
    }
}