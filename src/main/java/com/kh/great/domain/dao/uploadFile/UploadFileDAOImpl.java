package com.kh.great.domain.dao.uploadFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UploadFileDAOImpl implements UploadFileDAO {
    private final JdbcTemplate jt;

    /**
     * 업로드 파일 등록 - 단건
     * @param uploadFile 업로드 파일
     * @return 파일 아이디
     */
    @Override
    public Long addFile(UploadFile uploadFile) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into uploadfile (uploadfile_id, code, rid, store_filename, upload_filename, fsize, ftype) ");
        sql.append("                values (uploadfile_uploadfile_id_seq.nextval, ?, ?, ?, ?, ?, ?) ");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"uploadfile_id"});
                pstmt.setString(1, uploadFile.getCode());
                pstmt.setLong(2,  uploadFile.getRid());
                pstmt.setString(3, uploadFile.getStoreFilename());
                pstmt.setString(4, uploadFile.getUploadFilename());
                pstmt.setString(5, uploadFile.getFsize());
                pstmt.setString(6, uploadFile.getFtype());
                return pstmt;
            }
        }, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("uploadfile_id").toString());
    }

    /**
     * 업로드 파일 등록 - 여러건
     * @param uploadFile 업로드 파일
     */
    @Override
    public void addFile(List<UploadFile> uploadFile) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into uploadfile (uploadfile_id, code, rid, store_filename, upload_filename, fsize, ftype) ");
        sql.append("                values (uploadfile_uploadfile_id_seq.nextval, ?, ?, ?, ?, ?, ?) ");

        //배치 처리: 여러건의 갱신작업을 한꺼번에 처리하므로 단건처리할 때보다 성능이 좋다.
        jt.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, uploadFile.get(i).getCode());
                ps.setLong(2, uploadFile.get(i).getRid());
                ps.setString(3, uploadFile.get(i).getStoreFilename());
                ps.setString(4, uploadFile.get(i).getUploadFilename());
                ps.setString(5, uploadFile.get(i).getFsize());
                ps.setString(6, uploadFile.get(i).getFtype());
            }

            //배치 처리 건수
            @Override
            public int getBatchSize() {
                return uploadFile.size();
            }
        });
    }

    /**
     * 업로드 파일 조회 - 단건
     * @param uploadFileId 파일 아이디
     * @return 업로드 파일
     */
    @Override
    public Optional<UploadFile> findFileByUploadFileId(Long uploadFileId) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * ");
        sql.append("  from uploadfile ");
        sql.append(" where uploadfile_id = ? ");

        UploadFile uploadFile = null;
        try {
            uploadFile = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(UploadFile.class), uploadFileId);
            return Optional.of(uploadFile);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * 업로드 파일 조회 - 여러건
     * @param code 분류코드
     * @param rid 참조번호
     * @return 업로드 파일
     */
    @Override
    public List<UploadFile> getFilesByCodeWithRid(String code, Long rid) {
        StringBuffer sql = new StringBuffer();

        sql.append("select uploadfile_id, code, rid, store_filename, upload_filename, fsize, ftype, cdate, udate ");
        sql.append("  from uploadfile ");
        sql.append(" where code = ? ");
        sql.append("   and rid = ? ");

        //DB 테이블의 칼럼명(snake_case)는 별칭을 주지 않아도 java 객체(camelCase)로 자동 변환된다.
        List<UploadFile> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(UploadFile.class), code, rid);

        return list;
    }

    /**
     * 업로드 파일 삭제 by 파일 아이디
     * @param uploadFileId 파일 아이디
     * @return 삭제 건수
     */
    @Override
    public int deleteFileByUploadFileId(Long uploadFileId) {
        StringBuffer sql = new StringBuffer();

        sql.append("delete from uploadfile ");
        sql.append(" where uploadfile_id = ? ");

        return jt.update(sql.toString(), uploadFileId);
    }

    /**
     * 업로드 파일 삭제 by 분류코드, 참조번호
     * @param code 분류코드
     * @param rid 참조번호
     * @return 삭제 건수
     */
    @Override
    public int deleteFileByCodeWithRid(String code, Long rid) {
        StringBuffer sql = new StringBuffer();

        sql.append("delete from uploadfile ");
        sql.append(" where code = ? ");
        sql.append("   and rid = ? ");

        return jt.update(sql.toString(), code, rid);
    }
}
