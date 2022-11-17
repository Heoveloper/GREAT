package com.kh.great.domain.svc.notice;

import com.kh.great.domain.dao.notice.BbsFilterCondition;
import com.kh.great.domain.dao.notice.Notice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeSVC {
    /**
     * 공지사항 등록
     * @param notice 등록정보
     * @return 공지사항정보
     */
    Notice write(Notice notice);

    /**
     * 조회 by 공지사항번호
     * @param noticeId 공지사항번호
     * @return 공지사항정보
     */
    Notice read(Long noticeId);

    /**
     * 공지사항 수정
     * @param noticeId 공지사항번호
     * @param notice 수정할 정보
     * @return 수정 건수
     */
    Notice update(Long noticeId, Notice notice);

    /**
     * 공지사항 수정 (업로드 파일 포함)
     * @param noticeId 공지사항번호
     * @param notice 수정할 정보
     * @param files 업로드 파일
     * @return 수정 건수
     */
    Notice update(Long noticeId, Notice notice, List<MultipartFile> files);

    /**
     * 공지사항 삭제
     * @param noticeId 공지사항번호
     */
    void delete(Long noticeId);

    /**
     * 공지사항 목록 - 전체
     * @return 공지사항 목록
     */
    List<Notice> foundAll();

    /**
     * 공지사항 목록 - 전체 (작성자, 첨부파일 유무 포함)
     * @return 공지사항 목록
     */
    List<Notice> findAll();

    /**
     * 공지사항 목록 - 레코드
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 공지사항 목록
     */
    List<Notice> findAll(int startRec, int endRec);

    /**
     * 공지사항 목록 - 검색
     * @param filterCondition 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 공지사항 목록
     */
    List<Notice> findAll(BbsFilterCondition filterCondition);

    /**
     * 공지사항 수 - 전체
     * @return 공지사항 수
     */
    int totalCount();

    /**
     * 공지사항 수 - 검색
     * @param filterCondition 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 공지사항 수
     */
    int totalCount(BbsFilterCondition filterCondition);
}
