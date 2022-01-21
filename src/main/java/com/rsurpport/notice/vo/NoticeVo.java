package com.rsurpport.notice.vo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <pre>
 * 공지사항 VO
 * </pre>
 *
 * @author 오수정
 * @since 2022. 01. 21.
 * @see
 */
@Data
public class NoticeVo {

    private Long id;
    private String title;
    private String contentTxt;
    private String endDate;
    private String userId;
}
