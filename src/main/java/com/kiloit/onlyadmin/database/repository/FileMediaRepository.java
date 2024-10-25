package com.kiloit.onlyadmin.database.repository;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FileMediaRepository extends JpaRepository<FileMedia, Long>, JpaSpecificationExecutor<FileMedia> {
    Optional<FileMedia> findByIdAndDeletedAtIsNull(Long id);
    @Query(value = "SELECT fm FROM FileMedia fm WHERE fm.deletedAt IS NULL ORDER BY fm.id")
    Page<FileMedia> findAll(Specification<FileMedia> specification, PageRequest pageRequest);
}