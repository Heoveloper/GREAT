package com.kh.great.web.controller.attachFile;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import com.kh.great.web.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attach")
public class ApiAttachFileController {
    private final UploadFileSVC uploadFileSVC;
    private final FileUtils fileUtils;

    //이미지
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/img/{attachCode}/{storeFileName}")
    public Resource img(@PathVariable String attachCode, @PathVariable String storeFileName) throws MalformedURLException {
        //http://서버:포트/경로...
        //file:///d:/tmp/P0101/xxx-xxx-xxx-xxx.png
        String url = "file:///"+ fileUtils.getAttachFilePath(AttachCode.valueOf(attachCode), storeFileName);
        Resource resource = new UrlResource(url);

        return resource;
    }

    //첨부파일 삭제
    @DeleteMapping("/{fid}")
    public Object deleteAttachFile(@PathVariable Long fid) {
        //1) 스토리지 파일을 삭제하기 위해 분류코드(code)와 저장파일명(storeFilename)을 가져온다.
        Optional<UploadFile> optional = uploadFileSVC.findFileByUploadFileId(fid);
        if (optional.isEmpty()) {
            return ApiResponse.createApiResMsg("01", "조회하려는 파일이 존재하지 않습니다.", null);
        }

        //2) 첨부파일 삭제
        int affectedRow = uploadFileSVC.deleteFileByUploadFileId(fid);

        //삭제 성공하면
        if (affectedRow == 1) {
            return ApiResponse.createApiResMsg("00", "성공", null);
        }

        //삭제 실패하면
        return ApiResponse.createApiResMsg("99", "실패", null);
    }
}