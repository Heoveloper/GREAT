package com.kh.great.web.controller.community;

import com.kh.great.domain.common.paging.FindCriteria;
import com.kh.great.domain.dao.article.Article;
import com.kh.great.domain.dao.article.ArticleFilterCondition;
import com.kh.great.domain.svc.article.ArticleSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.dto.article.ArticleAddForm;
import com.kh.great.web.dto.article.ArticleEditForm;
import com.kh.great.web.dto.article.ArticleForm;
import com.kh.great.web.dto.article.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleSVC articleSVC;

    @Autowired
    @Qualifier("fc10") //동일한 타입의 객체가 여러 개 있을 시 빈 이름을 명시적으로 지정해서 주입받을 때
    private FindCriteria fc;

    //커뮤니티 게시글 목록 화면
    @GetMapping({"/list", "/list/{reqPage}", "/list/{reqPage}//", "/list/{reqPage}/{searchType}/{keyword}"})
    public String board(@PathVariable(required = false) Optional<Integer> reqPage,
                        @PathVariable(required = false) Optional<String> searchType,
                        @PathVariable(required = false) Optional<String> keyword,
                        @RequestParam(required = false) Optional<String> category,
                        Model model
    ) {
        log.info("/list 요청 = {}, {}, {}, {}", reqPage, searchType, keyword, category);

        String cate = getCategory(category);

        //FindCriteria 값 설정
        fc.getRc().setReqPage(reqPage.orElse(1));   //요청페이지, 요청 없으면 1
        fc.setSearchType(searchType.orElse(""));    //검색 유형
        fc.setKeyword(keyword.orElse(""));          //검색어

        List<Article> list = null;
        //게시글 목록 - 전체
        if (category == null || StringUtils.isEmpty(cate)) {
            //검색 유형과 검색어 모두 존재하면
            if (searchType.isPresent() && keyword.isPresent()) {
                ArticleFilterCondition filterCondition = new ArticleFilterCondition("", fc.getRc().getStartRec(), fc.getRc().getEndRec(), searchType.get(), keyword.get());
                fc.setTotalRec(articleSVC.totalCount(filterCondition));
                fc.setSearchType(searchType.get());
                fc.setKeyword(keyword.get());
                list = articleSVC.findAll(filterCondition);
            //검색 유형과 검색어 중 하나라도 없으면
            } else {
                //총 레코드 수
                fc.setTotalRec(articleSVC.totalCount());
                list = articleSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
            }
        //게시글 목록 - 카테고리
        } else {
            //검색 유형과 검색어 모두 존재하면
            if (searchType.isPresent() && keyword.isPresent()) {
                ArticleFilterCondition filterCondition = new ArticleFilterCondition(category.get(), fc.getRc().getStartRec(), fc.getRc().getEndRec(), searchType.get(), keyword.get());
                fc.setTotalRec(articleSVC.totalCount(filterCondition));
                fc.setSearchType(searchType.get());
                fc.setKeyword(keyword.get());
                list = articleSVC.findAll(filterCondition);
            //검색 유형과 검색어 중 하나라도 없으면
            } else {
                fc.setTotalRec(articleSVC.totalCount(cate));
                list = articleSVC.findAll(cate, fc.getRc().getStartRec(), fc.getRc().getEndRec());
            }
        }

        List<BoardForm> partOfList = new ArrayList<>();
        for (Article article : list) {
            BoardForm boardForm = new BoardForm();
            BeanUtils.copyProperties(article, boardForm);
            partOfList.add(boardForm);
        }

        model.addAttribute("list", partOfList);
        model.addAttribute("fc", fc);
        model.addAttribute("category", cate);

        return "/community/board";
    }

    //게시글 등록 화면
    @GetMapping("/write")
    public String writePage(Model model) {
        model.addAttribute("articleAddForm", new ArticleAddForm());

        return "/community/writeForm";
    }

    //게시글 등록 처리
    @ResponseBody
    @PostMapping("/write")
    public ApiResponse<Object> write(@Valid @RequestBody ArticleAddForm articleAddForm, BindingResult bindingResult) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
        }

        Article article = new Article();
        BeanUtils.copyProperties(articleAddForm, article);
        Article savedArticle = articleSVC.save(article);

        return ApiResponse.createApiResMsg("00", "성공", savedArticle);
    }

    //게시글 수정 화면
    @GetMapping("edit/{id}")
    public String editPage(@PathVariable("id") Long articleNum, Model model) {
        Optional<Article> foundArticle = articleSVC.read(articleNum);
        ArticleEditForm articleEditForm = new ArticleEditForm();
        if (!foundArticle.isEmpty()) {
            BeanUtils.copyProperties(foundArticle.get(), articleEditForm);
        }

        model.addAttribute("articleEditForm", articleEditForm);

        return "/community/editForm";
    }

    //게시글 수정 처리
    @ResponseBody
    @PatchMapping("edit/{id}")
    public ApiResponse<Object> edit(@PathVariable("id") Long articleNum,
                                    @Valid @RequestBody ArticleEditForm articleEditForm,
                                    BindingResult bindingResult
    ) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
        }

        Article article = new Article();
        BeanUtils.copyProperties(articleEditForm, article);
        Article updatedArticle = articleSVC.update(articleNum, article);

        return ApiResponse.createApiResMsg("00", "성공", updatedArticle);
    }

    //게시글 조회 화면
    @GetMapping("/article/{id}")
    public String read(@PathVariable("id") Long articleNum, Model model) {
        Optional<Article> foundArticle = articleSVC.read(articleNum);
        ArticleForm articleForm = new ArticleForm();
        if (!foundArticle.isEmpty()) {
            BeanUtils.copyProperties(foundArticle.get(), articleForm);
        }

        model.addAttribute("articleForm", articleForm);

        return "/community/article";
    }

    //게시글 삭제 처리
    @ResponseBody
    @DeleteMapping("/article/{id}/del")
    public ApiResponse<Article> delete(@PathVariable("id") Long articleNum) {
        articleSVC.delete(articleNum);

        return ApiResponse.createApiResMsg("00", "성공", null);
    }

    //검증 오류 메세지
    private Map<String, String> getErrMsg(BindingResult bindingResult) {
        Map<String, String> errmsg = new HashMap<>();

        bindingResult.getAllErrors().stream().forEach(objectError -> {
            errmsg.put(objectError.getCodes()[0], objectError.getDefaultMessage());
        });

        return errmsg;
    }

    //쿼리스트링 카테고리 읽기, 없으면 "" 반환
    private String getCategory(Optional<String> category) {
        String cate = category.isPresent() ? category.get() : "";

        return cate;
    }
}