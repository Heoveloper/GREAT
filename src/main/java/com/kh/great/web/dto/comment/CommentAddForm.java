package com.kh.great.web.dto.comment;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentAddForm {
    private Long articleNum;

    private Long commentGroup;

    //private Long commentNum;

    private Long pCommentNum;

    private Long step;

    private Long commentOrder;

    private String pCommentNickname;

    private Long memNumber;

    @Length(min = 1, max = 200)
    private String commentContents;

    //private LocalDateTime createDate;

    private String reply;

    //--------------------------------------------------

    private MultipartFile file;
}