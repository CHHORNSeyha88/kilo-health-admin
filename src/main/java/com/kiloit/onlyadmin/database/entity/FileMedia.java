package com.kiloit.onlyadmin.database.entity;
import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String fileUrl;
    @Column(nullable = false)
    private Long fileSize;
    

}
