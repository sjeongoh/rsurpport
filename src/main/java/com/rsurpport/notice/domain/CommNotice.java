package com.rsurpport.notice.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rsurpport.file.domain.PortalFile;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 공지사항 정보 Entity
 * </pre>
 *
 * @author 오수정
 * @see
 * @since 2022. 01. 21.
 */
@Entity(name = "notice_tb")
@Data
public class CommNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contentTxt;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    private String createdUserId;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifiedDate;

    private String modifiedUserId;

    @Transient
    @OneToMany(mappedBy = "commNotice", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<PortalFile> attachedFileList;

    private Long viewCnt;

    private Date endDate;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    String userName;
}
