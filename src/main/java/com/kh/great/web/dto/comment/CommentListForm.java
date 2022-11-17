package com.kh.great.web.dto.comment;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentListForm {
    private Long articleNum;

    private Long commentGroup;

    private Long commentNum;

    private Long pCommentNum;

    private Long memNumber;

    private String commentContents;

    private LocalDateTime createDate;

    //--------------------------------------------------

    private Member member;

    private UploadFile attachFile;
}