package com.kiloit.onlyadmin.database.repository;
import com.kiloit.onlyadmin.database.entity.FileMedia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface FileMediaRepository extends JpaRepository<FileMedia, Long>, JpaSpecificationExecutor<FileMedia> {
    Optional<FileMedia> findByIdAndDeletedAtIsNull(Long id);
    @SuppressWarnings("null")
    Page<FileMedia> findAll(Specification<FileMedia> spec, Pageable pageable);
}