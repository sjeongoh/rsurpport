package com.rsurpport.user.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <pre>
 * 사용자 정보 Entity
 * </pre>
 *
 * @author 오수정
 * @see
 * @since 2022. 01. 21.
 */
@Entity(name = "user_info_tb")
@Data
public class UserInfo {

    @Id
    private Long id;

    private String userId;

    private String userName;

}
