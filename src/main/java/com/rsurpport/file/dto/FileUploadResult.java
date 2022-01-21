package com.rsurpport.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResult {
    private String fileName;
    private String originalFileName;
    private long fileSize;
    private String contentType;
    private String filePath;
    private String downloadUrl;
}
