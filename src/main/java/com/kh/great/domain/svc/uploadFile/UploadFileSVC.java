package com.kh.great.domain.svc.uploadFile;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UploadFileSVC {
    /**
     * 업로드 파일 등록 - 단건
     * @param multipartFile 업로드 파일
     * @param code 분류코드
     * @param rid 참조번호
     * @return 파일 아이디
     */
    Long addFile(MultipartFile multipartFile, AttachCode code, Long rid);

    /**
     * 업로드 파일 등록 - 여러건
     * @param multipartFiles 업로드 파일
     * @param code 분류코드
     * @param rid 참조번호
     */
    void addFile(List<MultipartFile> multipartFiles, AttachCode code, Long rid);

    /**
     * 업로드 파일 조회 - 단건
     * @param uploadFileId 파일 아이디
     * @return 업로드 파일
     */
    Optional<UploadFile> findFileByUploadFileId(Long uploadFileId);

    /**
     * 업로드 파일 조회 - 여러건
     * @param code 분류코드
     * @param rid 참조번호
     * @return 업로드 파일
     */
    List<UploadFile> getFilesByCodeWithRid(String code, Long rid);

    /**
     * 업로드 파일 삭제 by 파일 아이디
     * @param uploadFileId 파일 아이디
     * @return 삭제 건수
     */
    int deleteFileByUploadFileId(Long uploadFileId);

    /**
     * 업로드 파일 삭제 by 분류코드, 참조번호
     * @param code 분류코드
     * @param rid 참조번호
     * @return 삭제 건수
     */
    int deleteFileByCodeWithRid(String code, Long rid);
}
