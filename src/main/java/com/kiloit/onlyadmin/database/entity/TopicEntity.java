package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "topics")
public class TopicEntity extends BaseEntity {

    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id)
    private CategoryEntity category;

}
