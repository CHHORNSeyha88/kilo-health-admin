package com.kiloit.onlyadmin.database.entity;
import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Posts")
public class PostEntity extends BaseEntity {
    private String title;
    private String description;
    private Boolean status;
    @OneToOne
    private FileMedia fileMedia;
    @Column(name = "public_at")
    private Instant publicAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_id")
    private TopicEntity topicEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "post")
    List<PostViewEntity> postView;


}
