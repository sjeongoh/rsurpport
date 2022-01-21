package com.rsurpport.file.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "file_tb")
@Data
@NoArgsConstructor
public class PortalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String target;

    private String contentType;

    private String fileName;

    private String originalFileName;

    private Long fileSize;

    private String filePath;

    private Long parentId;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;


    public PortalFile(String target, String contentType, String fileName, String originalFileName, Long fileSize, String filePath, Long parentId) {
        this.target = target;
        this.contentType = contentType;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.parentId = parentId;
    }
}
