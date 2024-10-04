package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "list_media")
@Getter
@Setter
public class ListMediaEntity extends BaseEntity {
    @OneToMany(mappedBy = "listMedia")
    private List<FileMedia> files;
}
