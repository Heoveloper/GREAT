package com.kh.great.web.dto.comment;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentEditForm {
    //private Long articleNum;

    //private Long commentGroup;

    private Long commentNum;

    //private Long pCommentNum;

    //private Long memNumber;

    @Length(min = 1, max = 200)
    private String commentContents;

    //private LocalDateTime createDate;

    //--------------------------------------------------

    private MultipartFile file;
}