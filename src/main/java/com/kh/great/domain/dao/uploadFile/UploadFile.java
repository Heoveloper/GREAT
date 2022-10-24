package com.kh.great.domain.dao.uploadFile;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadFile {
    private Long uploadfileId;         //  uploadfile_id   number(10),                     --파일아이디
    private String code;               //  code            varchar2(11),                   --분류코드
    private Long rid;                  //  rid             varchar2(10),                   --참조번호(게시글번호등)
    private String storeFilename;      //  store_filename  varchar2(100),                  --서버보관파일명
    private String uploadFilename;     //  upload_filename varchar2(100),                  --업로드파일명(유저가 업로드한파일명)
    private String fsize;              //  fsize           varchar2(45),                   --업로드파일크기(단위byte)
    private String ftype;              //  ftype           varchar2(100),                  --파일유형(mimetype)
    private LocalDateTime cdate;       //  cdate           timestamp default systimestamp, --등록일시
    private LocalDateTime udate;       //  udate           timestamp default systimestamp  --수정일시
}
