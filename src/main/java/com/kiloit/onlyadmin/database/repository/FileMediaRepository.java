package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.FileMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMediaRepository extends JpaRepository<FileMedia, Long> {
}