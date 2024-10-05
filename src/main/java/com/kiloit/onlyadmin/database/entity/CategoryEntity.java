package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name="category")
public class CategoryEntity extends BaseEntity {

    private String name;
    private String thumbnail;
    @Column(name = "media_id")
    private int ListMediaId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "category")
    private List<TopicEntity> topicList ;

    @OneToMany(mappedBy = "categoryEntity")
    private List<PostEntity> postEntities ;
}
