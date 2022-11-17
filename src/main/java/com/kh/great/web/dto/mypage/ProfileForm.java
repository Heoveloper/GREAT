package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.mypage.Bookmark;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class ProfileForm {
    private Long memNumber;

    private String memType;

    private String memId;

    private String memPassword;

    private String memName;

    private String memNickname;

    private String memEmail;

    private String memBusinessnumber;

    private String memStoreName;

    private String memStorePhonenumber;

    private String memStoreLocation;

    private String memStoreLatitude;

    private String memStoreLongitude;

    private String memStoreIntroduce;

    private String memStoreSns;

    private LocalDateTime memRegtime;

    private LocalDateTime memLockExpiration;

    private String memAdmin;

    private Product product;

    private Bookmark bookmark;

    private Long pNumber;

    private Long ownerNumber;

    private String deadlineTime;

    private String pTitle;

    private String detailInfo;

    private MultipartFile file;

    private List<MultipartFile> files;

    private List<UploadFile> imageFiles;
}
