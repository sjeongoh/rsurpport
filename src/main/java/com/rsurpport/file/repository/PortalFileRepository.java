package com.rsurpport.file.repository;

import com.rsurpport.file.domain.PortalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PortalFileRepository extends JpaRepository<PortalFile, Long> {

        List<PortalFile> findAllByParentId(Long parentId);
}
