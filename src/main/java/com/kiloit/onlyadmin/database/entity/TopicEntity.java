package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "topics")
public class TopicEntity extends BaseEntity {
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private FileMedia fileMediaId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "topicEntity")
    List<PostEntity> postEntities;

}
