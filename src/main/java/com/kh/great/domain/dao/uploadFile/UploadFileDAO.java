package com.kh.great.domain.dao.uploadFile;

import java.util.List;
import java.util.Optional;

public interface UploadFileDAO {
    /**
     * 업로드 파일 등록 - 단건
     * @param uploadFile
     * @return 파일Id
     */
    Long addFile(UploadFile uploadFile);

    /**
     * 업로드 파일 등록 - 여러건
     * @param uploadFile
     */
    void addFile(List<UploadFile> uploadFile);

    /**
     * 업로드 파일 조회 여러건
     * @param code
     * @param rid
     * @return
     */
    List<UploadFile> getFilesByCodeWithRid(String code, Long rid);


    /**
     * 업로드 파일 조회 단건
     * @param uploadFileId
     * @return
     */
    Optional<UploadFile> findFileByUploadFileId(Long uploadFileId);


    /**
     * 첨부파일 삭제 by uploadFileId
     * @param uploadFileId 첨부파일아이디
     * @return 삭제한 레코드수
     */
    int deleteFileByUploadFileId(Long uploadFileId);

    /**
     * 첨부파일 삭제 By code, rid
     * @param code 첨부파일 분류코드
     * @param rid 첨부파일아이디
     * @return 삭제한 레코드수
     */
    int deleteFileByCodeWithRid(String code, Long rid);
}
