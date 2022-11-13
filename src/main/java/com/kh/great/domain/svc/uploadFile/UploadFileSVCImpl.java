package com.kh.great.domain.svc.uploadFile;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.dao.uploadFile.UploadFileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileSVCImpl implements UploadFileSVC {
    private final UploadFileDAO uploadFileDAO;
    private final FileUtils fileUtils;

    /**
     * 업로드 파일 등록 - 단건
     * @param multipartFile 업로드 파일
     * @param code 분류코드
     * @param rid 참조번호
     * @return 파일 아이디
     */
    @Override
    public Long addFile(MultipartFile multipartFile, AttachCode code, Long rid) {
        //1) 스토리지 저장
        UploadFile uploadFile = fileUtils.multipartFileToUploadFile(multipartFile, code, rid);
        //2) 첨부파일 메타정보 저장
        Long affectedRow = uploadFileDAO.addFile(uploadFile);

        return affectedRow;
    }

    /**
     * 업로드 파일 등록 - 여러건
     * @param multipartFiles 업로드 파일
     * @param code 분류코드
     * @param rid 참조번호
     */
    @Override
    public void addFile(List<MultipartFile> multipartFiles, AttachCode code, Long rid) {
        //1) 스토리지 저장
        List<UploadFile> uploadFiles = fileUtils.multipartFilesToUploadFiles(multipartFiles, code, rid);
        //2) 첨부파일 메타정보 저장
        uploadFileDAO.addFile(uploadFiles);
    }

    /**
     * 업로드 파일 조회 - 단건
     * @param uploadFileId 파일 아이디
     * @return 업로드 파일
     */
    @Override
    public Optional<UploadFile> findFileByUploadFileId(Long uploadFileId) {

        return uploadFileDAO.findFileByUploadFileId(uploadFileId);
    }

    /**
     * 업로드 파일 조회 - 여러건
     * @param code 분류코드
     * @param rid 참조번호
     * @return 업로드 파일
     */
    @Override
    public List<UploadFile> getFilesByCodeWithRid(String code, Long rid) {

        return uploadFileDAO.getFilesByCodeWithRid(code, rid);
    }

    /**
     * 업로드 파일 삭제 by 파일 아이디
     * @param uploadFileId 파일 아이디
     * @return 삭제 건수
     */
    @Override
    public int deleteFileByUploadFileId(Long uploadFileId) {
        Optional<UploadFile> optional = uploadFileDAO.findFileByUploadFileId(uploadFileId);
        UploadFile uploadFile = optional.get();

        if (optional.isEmpty()) return 0;

        //1) 스토리지 파일 삭제
        fileUtils.deleteAttachFile(AttachCode.valueOf(uploadFile.getCode()), uploadFile.getStoreFilename());
        //2) 첨부파일 메타정보 삭제
        int affectedRow = uploadFileDAO.deleteFileByUploadFileId(uploadFileId);

        return affectedRow;
    }

    /**
     * 업로드 파일 삭제 by 분류코드, 참조번호
     * @param code 분류코드
     * @param rid 참조번호
     * @return 삭제 건수
     */
    @Override
    public int deleteFileByCodeWithRid(String code, Long rid) {
        List<UploadFile> uploadFiles = uploadFileDAO.getFilesByCodeWithRid(code, rid);

        if (uploadFiles.size() == 0) return 0;

        //1) 스토리지 파일 삭제
        for (UploadFile uploadFile : uploadFiles) {
            fileUtils.deleteAttachFile(AttachCode.valueOf(code), uploadFile.getStoreFilename());
        }
        //2) 첨부파일 메타정보 삭제
        int affectedRow = uploadFileDAO.deleteFileByCodeWithRid(code, rid);

        return affectedRow;
    }
}