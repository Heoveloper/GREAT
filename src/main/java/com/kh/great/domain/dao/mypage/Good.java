package com.kh.great.domain.dao.mypage;

import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Good {
    private Long goodNumber;    //good_number number(10),   --좋아요번호
    private Long memNumber;     //mem_number number(6),     --회원번호
    private Long pNumber;       //p_number number(5)        --상품번호

    private Product product;
    private MultipartFile file;
    private List<MultipartFile> files;
    private List<UploadFile> imageFiles;
}
