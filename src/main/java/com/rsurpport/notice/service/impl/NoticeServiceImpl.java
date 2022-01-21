package com.rsurpport.notice.service.impl;

import com.rsurpport.file.domain.PortalFile;
import com.rsurpport.file.repository.PortalFileRepository;
import com.rsurpport.notice.domain.CommNotice;
import com.rsurpport.notice.repository.NoticeRepository;
import com.rsurpport.notice.service.NoticeService;
import com.rsurpport.notice.vo.NoticeVo;
import com.rsurpport.user.repository.UserInfoReporitory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.rsurpport.common.util.PortalFileUtil.*;

/**
 * <pre>
 * 공지사항을 위한 서비스 Implements 구현
 * </pre>
 *
 * @author 오수정
 * @since 2022. 01 21.
 * @see
 */
@Service
@Transactional
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private static final String CREATED_DATE = "createdDate";

    @Autowired
    private NoticeRepository noticeRepo;

    @Autowired
    private UserInfoReporitory userInfoRepo;

    @Autowired
    private PortalFileRepository portalFileRepo;

    /**
     * 공지사항 목록 조회
     * @param page 페이지
     * @param size 사이즈
     * @param searchText 검색 내용
     * @return 공지사항 목록
     */
    @Override
    public Page<CommNotice> listNotice(int page, int size, String searchText) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATE).descending());
        Page<CommNotice> notices = noticeRepo.findAllByTitleContaining(searchText, pageable);
        if(notices != null) {
            for(CommNotice commNotice : notices) {
                commNotice.setAttachedFileList(portalFileRepo.findAllByParentId( commNotice.getId()));
                commNotice.setUserName(userInfoRepo.findByUserId(commNotice.getCreatedUserId()).getUserName());
            }
        }

        return notices;
    }

    /**
     * 공지사항 상세 조회
     * @param id 공지사항 id
     * @return 공지사항 상세
     */
    @Override
    public CommNotice getNotice(Long id) {
        CommNotice commNotice = null;

        Optional<CommNotice> optionalNotice = noticeRepo.findById(id);
        if(optionalNotice.isPresent()) {
            commNotice = optionalNotice.get();
            commNotice.setViewCnt(commNotice.getViewCnt() + 1);
            commNotice = noticeRepo.save(commNotice);
            commNotice.setUserName(userInfoRepo.findByUserId(commNotice.getCreatedUserId()).getUserName());
            commNotice.setAttachedFileList(portalFileRepo.findAllByParentId(commNotice.getId()));

        }

        return commNotice;
    }

    /**
     * 공지사항 추가
     * @param noticeVo 공지사항 vo
     * @param attachedFileList 첨부파일 목록
     * @throws Exception exception
     */
    @Override
    public void insertNotice(NoticeVo noticeVo, List<MultipartFile> attachedFileList) throws Exception {
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = format.parse(noticeVo.getEndDate());
        CommNotice commNotice = new CommNotice();
        commNotice.setContentTxt(noticeVo.getContentTxt());
        commNotice.setTitle(noticeVo.getTitle());
        commNotice.setCreatedDate(new Date());
        commNotice.setCreatedUserId(noticeVo.getUserId());
        commNotice.setModifiedDate(new Date());
        commNotice.setEndDate(endDate);
        commNotice.setViewCnt(0L);
        CommNotice resultCommNotice = noticeRepo.save(commNotice);

        // 파일 서버에 업로드...
        saveNewPortalFileList(attachedFileList, resultCommNotice.getId());

    }

    /**
     * 공지사항 수정
     * @param noticeVo 공지사항 vo
     * @param deletedFileIdList 삭제된 첨부파일 id 목록
     * @param attachedFileList 첨부파일 목록
     * @throws Exception exception
     */
    @Override
    public void updateNotice(NoticeVo noticeVo, List<Long> deletedFileIdList, List<MultipartFile> attachedFileList) throws Exception {
        Optional<CommNotice> optionalNotice = noticeRepo.findById(noticeVo.getId());
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = format.parse(noticeVo.getEndDate());
        if(optionalNotice.isPresent()) {
            CommNotice commNotice = optionalNotice.get();
            commNotice.setTitle(noticeVo.getTitle());
            commNotice.setContentTxt(noticeVo.getContentTxt());
            commNotice.setModifiedDate(new Date());
            commNotice.setModifiedUserId(noticeVo.getUserId());
            commNotice.setEndDate(endDate);
            // 파일 수정..
            // 기존 파일 중 삭제할 파일
            if(deletedFileIdList != null && !deletedFileIdList.isEmpty()) {
                for(Long deletedFileId : deletedFileIdList) {
                    PortalFile portalFile = getPortalFile(deletedFileId);
                    deleteFile(portalFile);
                }
            }
            // 새로 추가된 파일
            saveNewPortalFileList(attachedFileList, commNotice.getId());

            noticeRepo.save(commNotice);
        }
    }

   /* 첨부파일 저장*/
    private void saveNewPortalFileList(List<MultipartFile> attachedFileList, Long noticeId) throws Exception {
        List<PortalFile> portalMultipartFileList = new ArrayList<>();
        if (attachedFileList != null && !attachedFileList.isEmpty()) {
            for (MultipartFile multipartFile : attachedFileList) {
                PortalFile newPortalFile = fileUpload("local", multipartFile,  noticeId);
                portalMultipartFileList.add(newPortalFile);
            }
        }

        if (!portalMultipartFileList.isEmpty()) {
            for (PortalFile portalFile : portalMultipartFileList) {
                portalFile.setParentId(noticeId);
                portalFileRepo.save(portalFile);
            }
        }
    }

    /**
     * 공지사항 삭제
     * @param id 공지사항 id
     */
    @Override
    public void deleteNotice(Long id) {
        Optional<CommNotice> optionalNotice = noticeRepo.findById(id);

        if(optionalNotice.isPresent()) {
            CommNotice commNotice = optionalNotice.get();
            // 파일 삭제...
            List<PortalFile> portalFileList = portalFileRepo.findAllByParentId(commNotice.getId());
            if(portalFileList != null) {
                for (PortalFile portalFile : portalFileList) {
                    deleteFile(portalFile);
                }
            }

            noticeRepo.delete(commNotice);
        }
    }
}
