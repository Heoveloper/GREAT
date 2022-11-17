package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class GoodForm {
    private Long goodNumber;

    private Long memNumber;

    private Long pNumber;

    //--------------------------------------------------

    private MultipartFile file;

    private List<MultipartFile> files;

    private List<UploadFile> imageFiles;
}