package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.*;

@Entity
@Table(name="file_medias")
@Setter
@Getter
public class FileMedia extends BaseEntity {

    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String fileType;
    @Column(nullable = false)
    private String filePath;
    @Column(nullable = false)
    private Long fileSize;
    @Column(nullable = false)
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private ListMediaEntity listMedia;
    
}
