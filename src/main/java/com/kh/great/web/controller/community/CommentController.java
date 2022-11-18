package com.kh.great.web.controller.community;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.dao.comment.Comment;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.comment.CommentSVC;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.dto.comment.CommentAddForm;
import com.kh.great.web.dto.comment.CommentEditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentSVC commentSVC;
    private final UploadFileSVC uploadFileSVC;

    //댓글 등록 처리
    @PostMapping("/write/{articleNum}")
    public ApiResponse<Object> saveComment(@PathVariable Long articleNum,
                                           @Valid CommentAddForm commentAddForm,
                                           BindingResult bindingResult
    ) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddForm, comment);
        comment.setArticleNum(articleNum);

        //댓글 등록
        Comment savedComment = new Comment();
        if (commentAddForm.getFile() == null) {
            savedComment = commentSVC.save(comment);
        } else if (commentAddForm.getFile() != null) {
            savedComment = commentSVC.save(comment, commentAddForm.getFile());
        }

        return ApiResponse.createApiResMsg("00", "성공", savedComment);
    }

    //댓글 수정 화면
    @GetMapping("/edit/{commentNum}")
    public ApiResponse<Comment> editWindow(@PathVariable Long commentNum) {
        //수정할 댓글 조회
        Optional<Comment> foundComment = commentSVC.find(commentNum);

        //첨부파일 조회
        List<UploadFile> uploadFile = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), commentNum);
        if (uploadFile.size() > 0) {
            UploadFile attachFile = uploadFile.get(0);
            foundComment.get().setAttachFile(attachFile);
        }

        return ApiResponse.createApiResMsg("00", "성공", foundComment.get());
    }

    //댓글 수정 처리
    @PatchMapping("/edit/{commentNum}")
    public ApiResponse<Object> editComment(@PathVariable Long commentNum,
                                           @Valid CommentEditForm commentEditForm,
                                           BindingResult bindingResult
    ) {
        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEditForm, comment);

        //댓글 수정
        Comment updatedComment = new Comment();
        if (commentEditForm.getFile() == null) {
            updatedComment = commentSVC.update(commentNum, comment);
        } else if (commentEditForm.getFile() != null) {
            updatedComment = commentSVC.update(commentNum, comment, commentEditForm.getFile());
        }

        return ApiResponse.createApiResMsg("00", "성공", updatedComment);
    }

    //댓글 삭제 처리
    @DeleteMapping("/delete/{commentNum}")
    public ApiResponse<Object> deleteComment(@PathVariable Long commentNum) {
        //답댓글 수
        int cntOfChildrenComments = commentSVC.countOfChildrenComments(commentNum);

        //답댓글이 없으면
        if (cntOfChildrenComments == 0) {
            Long pCommentNum = commentSVC.find(commentNum).get().getPCommentNum();

            //댓글 삭제
            commentSVC.delete(commentNum);

            if (commentSVC.countOfChildrenComments(pCommentNum) == 0 && commentSVC.find(pCommentNum).get().getCommentContents().equals("삭제된 댓글입니다.")) {
                commentSVC.delete(pCommentNum);
            }

            return ApiResponse.createApiResMsg("00", "성공", null);
        }

        //답댓글이 있으면
        commentSVC.updateToDeletedComment(commentNum);

        return ApiResponse.createApiResMsg("00", "성공", cntOfChildrenComments);
    }

    //댓글 목록
    @GetMapping("/list/{articleNum}")
    public ApiResponse<List<Comment>> commentList(@PathVariable("articleNum") Long articleNum) {
        List<Comment> commentList = commentSVC.findAll(articleNum);

        for (Comment comment : commentList) {
            List<UploadFile> uploadFile = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), comment.getCommentNum());
            if (uploadFile.size() > 0) {
                UploadFile attachFile = uploadFile.get(0);
                comment.setAttachFile(attachFile);
            }
        }

        return ApiResponse.createApiResMsg("00", "성공", commentList);
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