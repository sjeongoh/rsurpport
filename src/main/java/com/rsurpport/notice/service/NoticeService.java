package com.rsurpport.notice.service;

import com.rsurpport.notice.domain.CommNotice;
import com.rsurpport.notice.vo.NoticeVo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <pre>
 * 공지사항을 위한 서비스 인터페이스
 * </pre>
 *
 * @author 오수정
 * @since 2022. 01. 21.
 * @see
 */
public interface NoticeService {

    /**
     * 공지사항 목록 조회
     * @param page 페이지
     * @param size 사이즈
     * @param searchText 검색 내용
     * @return 공지사항 목록
     */
    Page<CommNotice> listNotice(int page, int size, String searchText);

    /**
     * 관리자 공지사항 상세 조회
     * @param id 공지사항 id
     * @return 공지사항 상세
     */
    CommNotice getNotice(Long id);

    /**
     * 공지사항 추가
     * @param noticeVo 공지사항 vo
     * @param attachedFileList 첨부파일 목록
     * @throws Exception exception
     */
    void insertNotice(NoticeVo noticeVo, List<MultipartFile> attachedFileList) throws Exception;

    /**
     * 공지사항 수정
     * @param noticeVo 공지사항 vo
     * @param deletedFileIdList 삭제된 첨부파일 id 목록
     * @param attachedFileList 첨부파일 목록
     * @throws Exception exception
     */
    void updateNotice(NoticeVo noticeVo, List<Long> deletedFileIdList, List<MultipartFile> attachedFileList) throws Exception;

    /**
     * 공지사항 삭제
     * @param id 공지사항 id
     */
    void deleteNotice(Long id);
}
