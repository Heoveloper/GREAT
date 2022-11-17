package com.kh.great.web.dto.mypage;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileAddForm {
    private MultipartFile file;
}