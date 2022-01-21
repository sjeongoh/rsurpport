package com.rsurpport.notice.repository;

import com.rsurpport.notice.domain.CommNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

/**
 * <pre>
 * 공지사항을 위한 레파지토리 인터페이스
 * </pre>
 *
 * @author 오수정
 * @since 2022. 01. 21
 * @see
 */
@Repository
public interface NoticeRepository extends JpaRepository<CommNotice, Long> {

    Page<CommNotice> findAllByTitleContaining(String searchText, Pageable pageable);


}