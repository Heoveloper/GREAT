package com.kh.great.domain.svc.notice;

import com.kh.great.domain.dao.notice.BbsFilterCondition;
import com.kh.great.domain.dao.notice.Notice;
import com.kh.great.domain.dao.notice.NoticeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeSVCImpl implements NoticeSVC {
    private final NoticeDAO noticeDAO;

    /**
     * 공지사항 등록
     * @param notice 등록정보
     * @return 공지사항정보
     */
    @Override
    public Notice write(Notice notice) {

        return noticeDAO.save(notice);
    }

    /**
     * 조회 by 공지사항번호
     * @param noticeId 공지사항번호
     * @return 공지사항정보
     */
    @Override
    public Notice read(Long noticeId) {
        //조회수 증가
        noticeDAO.increaseViewCount(noticeId);

        return noticeDAO.read(noticeId);
    }

    /**
     * 공지사항 수정
     * @param noticeId 공지사항번호
     * @param notice 수정할 정보
     * @return 공지사항정보
     */
    @Override
    public Notice update(Long noticeId, Notice notice) {
        //공지사항 수정
        noticeDAO.update(noticeId, notice);

        return noticeDAO.read(noticeId);
    }

    /**
     * 공지사항 수정 (업로드 파일 포함)
     * @param noticeId 공지사항번호
     * @param notice 수정할 정보
     * @param files 업로드 파일
     * @return 공지사항정보
     */
    @Override
    public Notice update(Long noticeId, Notice notice, List<MultipartFile> files) {
        //공지사항 수정
        noticeDAO.update(noticeId, notice);

        return noticeDAO.read(noticeId);
    }

    /**
     * 공지사항 삭제
     * @param noticeId 공지사항번호
     */
    @Override
    public void delete(Long noticeId) {

        noticeDAO.delete(noticeId);
    }

    /**
     * 공지사항 목록 - 전체
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> foundAll() {

        return noticeDAO.foundAll();
    }

    /**
     * 공지사항 목록 - 전체 (작성자, 첨부파일 유무 포함)
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> findAll() {

        return noticeDAO.selectAll();
    }

    /**
     * 공지사항 목록 - 레코드
     * @param startRec 시작 레코드번호
     * @param endRec 종료 레코드번호
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> findAll(int startRec, int endRec) {

        return noticeDAO.findAll(startRec, endRec);
    }

    /**
     * 공지사항 목록 - 검색
     * @param filterCondition 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 공지사항 목록
     */
    @Override
    public List<Notice> findAll(BbsFilterCondition filterCondition) {

        return noticeDAO.findAll(filterCondition);
    }

    /**
     * 공지사항 수 - 전체
     * @return 공지사항 수
     */
    @Override
    public int totalCount() {

        return noticeDAO.totalCount();
    }

    /**
     * 공지사항 수 - 검색
     * @param filterCondition 시작 레코드번호, 종료 레코드번호, 검색 유형, 검색어
     * @return 공지사항 수
     */
    @Override
    public int totalCount(BbsFilterCondition filterCondition) {

        return noticeDAO.totalCount(filterCondition);
    }
}