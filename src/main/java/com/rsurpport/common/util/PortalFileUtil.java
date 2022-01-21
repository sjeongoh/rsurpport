package com.rsurpport.common.util;

import com.rsurpport.file.domain.PortalFile;
import com.rsurpport.file.dto.FileUploadResult;
import com.rsurpport.file.repository.PortalFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class PortalFileUtil {

    private static final String SLASH = "/";

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static PortalFileRepository portalFileRepo;


    @Autowired
    public void setPortalFileRepository(PortalFileRepository portalFileRepo) {
        PortalFileUtil.portalFileRepo = portalFileRepo;
    }


    public PortalFileUtil() {}

    public static String getAddTimeFileName(String fileName) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        fileName = fileName.replaceAll(" ", "_");
        int idx = fileName.lastIndexOf(".");
        if (idx < 0) {
            fileName = fileName + "_" + simpleDateFormat.format(timestamp);
        } else {
            fileName = fileName.substring(0, idx) + "_" + simpleDateFormat.format(timestamp) + fileName.substring(idx);
        }
        return fileName;
    }

    /**
     * 파일 업로드
     * @param target 타겟
     * @param file 멀티파트 파일
     * @param parentId 저장 테이블 id
     * @return 포털 파일
     * @throws IOException IOException
     */
    public static PortalFile fileUpload(String target, MultipartFile file, Long parentId) throws IOException {

        PortalFile portalFile = null;
        if (file != null && !file.isEmpty()) {
            FileUploadResult uploadResult = targetFileUpload(target, file);
            portalFile = new PortalFile(target, uploadResult.getContentType(), uploadResult.getFileName(), uploadResult.getOriginalFileName(), uploadResult.getFileSize(), uploadResult.getFilePath(), parentId);
            portalFile.setCreatedDate(new Date());
            portalFile = portalFileRepo.save(portalFile);
        }
        return portalFile;
    }
    private static FileUploadResult targetFileUpload(String target, MultipartFile file) throws IOException {
        FileUploadResult result = new FileUploadResult();
        String originalFilename = file.getOriginalFilename();
        String fileName = getAddTimeFileName(originalFilename);
        String contentType = file.getContentType();
        result.setFileName(fileName);
        result.setOriginalFileName(originalFilename);
        result.setFileSize(file.getSize());
        result.setContentType(contentType);
        try {
            String fullFilePath = SLASH + fileName;
            File newFile = new File(fullFilePath);
            file.transferTo(newFile);
            result.setFilePath(newFile.getPath());
            result.setDownloadUrl(fullFilePath);
        } catch (IOException e) {
            log.error("파입업로드 오류!:{}", e.getMessage());
            throw e;
        }
        return result;
    }


    /**
     * 파일 삭제
     * @param portalFile 포털 파일
     */
    public static void deleteFile(PortalFile portalFile) {
        if (portalFile == null) return;
            portalFileRepo.deleteById(portalFile.getId());
    }
    /**
     * 파일 조회
     * @param id 파일 id
     * @return 포털 파일
     */
    public static PortalFile getPortalFile(Long id) {
        Optional<PortalFile> optionalPortalFile = portalFileRepo.findById(id);
        if(optionalPortalFile.isPresent()) {
            return optionalPortalFile.get();
        }
        return null;
    }



}
