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
    @OneToOne(optional = true)
    @JoinColumn(name = "media_id", nullable = true)
    private FileMedia media;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private List<TopicEntity> topicList ;

    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.EAGER)
    private List<PostEntity> postEntities ;
}
