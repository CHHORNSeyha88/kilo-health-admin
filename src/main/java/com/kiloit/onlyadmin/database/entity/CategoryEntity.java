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
    @OneToOne
    @JoinColumn(name = "media_id")
    private FileMedia fileMediaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private List<TopicEntity> topicList ;

    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.EAGER)
    private List<PostEntity> postEntities ;
}
