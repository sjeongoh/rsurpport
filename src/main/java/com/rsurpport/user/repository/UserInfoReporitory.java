package com.rsurpport.user.repository;

import com.rsurpport.user.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * <pre>
 * 사용자정보를 위한 레파지토리 인터페이스
 * </pre>
 *
 * @author 오수정
 * @since 2022. 01. 21.
 * @see
 */

public interface UserInfoReporitory  extends JpaRepository<UserInfo, Long> {
    UserInfo findByUserId(String userId);
}
