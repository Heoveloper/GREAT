package com.kh.great.domain.dao.comment;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long articleNum;            //article_num number(6),            --게시글번호
    private Long commentGroup;          //comment_group number(6),          --그룹
    private Long commentNum;            //comment_num number(6),            --댓글번호
    private Long pCommentNum;           //p_comment_num number(6),          --부모댓글번호
    private Long step;                  //step number(3),                   --단계
    private Long commentOrder;          //comment_order number(3),          --순서
    private String pCommentNickname;    //p_comment_nickname varchar2(18),  --부모댓글닉네임
    private Long memNumber;             //mem_number number(6),             --작성자번호
    private String commentContents;     //comment_contents clob,            --내용
    @DateTimeFormat(pattern = "yyyy.MM.dd. HH:mm")
    private LocalDateTime createDate;   //create_date date,                 --등록일자
    private String reply;               //reply varchar2(1)                 --답댓글여부

    private Member member;
    private UploadFile attachFile;
}
