package com.kh.great.domain.common.file;

import com.kh.great.domain.dao.uploadFile.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {
    @Value("${attach.root_dir}")    //application.properties 파일의 키, 값을 읽어옴
    private String attachRoot;      //첨부파일 루트

    //MultipartFile -> UploadFile
    public UploadFile multipartFileToUploadFile(MultipartFile file, AttachCode code, Long rid) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setCode(code.name()); //상품관리
        uploadFile.setRid(rid);
        uploadFile.setUploadFilename(file.getOriginalFilename());

        String storeFileName = storeFileName(file.getOriginalFilename());
        uploadFile.setStoreFilename(storeFileName);
        uploadFile.setFsize(String.valueOf(file.getSize()));
        uploadFile.setFtype(file.getContentType());

        //스토리지에 파일 저장
        storageFile(file, code, storeFileName);

        return uploadFile;
    }

    //List<MultipartFile> -> List<UploadFile>
    public List<UploadFile> multipartFilesToUploadFiles(List<MultipartFile> files, AttachCode code, Long rid) {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadFile uploadFile = multipartFileToUploadFile(file, code, rid);
            uploadFiles.add(uploadFile);
        }

        return uploadFiles;
    }

    //랜덤 파일명 생성
    private String storeFileName(String originalFileName) {
        //확장자 추출
        int dotPosition = originalFileName.indexOf(".");
        String ext = originalFileName.substring(dotPosition + 1);

        //랜덤 파일명
        String storeFileName = UUID.randomUUID().toString();
        StringBuffer sb = new StringBuffer();
        storeFileName = sb.append(storeFileName)
                          .append(".")
                          .append(ext)
                          .toString();

        return storeFileName;
    }

    //스토리지에 파일 저장 메소드
    private void storageFile(MultipartFile file, AttachCode code, String storeFileName) {
        try {
            File f = new File(getAttachFilePath(code, storeFileName));
            f.mkdirs(); //경로가 없으면 디렉토리 생성
            file.transferTo(f);
        } catch (IOException e) {
            throw new RuntimeException("첨부파일 스토리지 저장 시 오류 발생!");
        }
    }

    //첨부파일의 물리적인 경로 추출 ex) d:/tmp/P0101/xx-xxx-xx.jpg
    public String getAttachFilePath(AttachCode code, String storeFileName) {

        return this.attachRoot + code.name() + "/" + storeFileName;
    }

    //첨부파일 삭제
    public void deleteAttachFile(AttachCode code, String storeFileName) {
        File f = new File(getAttachFilePath(code, storeFileName));
        if (f.exists()) {
            f.delete();
        }
    }
}