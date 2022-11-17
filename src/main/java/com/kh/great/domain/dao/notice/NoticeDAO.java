package com.kh.great.domain.dao.notice;

import java.util.List;

public interface NoticeDAO {
    /**
     * 공지사항 등록
     * @param notice 등록정보
     * @return 공지사항정보
     */
    Notice save(Notice notice);

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
    int update(Long noticeId, Notice notice);

    /**
     * 공지사항 삭제
     * @param noticeId 공지사항번호
     * @return 삭제 건수
     */
    int delete(Long noticeId);

    /**
     * 공지사항 목록 - 전체 (작성자, 첨부파일 유무 포함)
     * @return 공지사항 목록
     */
    List<Notice> selectAll();

    /**
     * 공지사항 목록 - 전체
     * @return 공지사항 목록
     */
    List<Notice> foundAll();

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
     * 조회수 증가
     * @param noticeId 공지사항번호
     * @return 조회수 증가된 공지사항 수
     */
    int increaseViewCount(Long noticeId);

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
