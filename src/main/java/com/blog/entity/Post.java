package com.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = "commentSet")
@Table(
        name = "tb_post",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "post_title")
        }
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long postId;

    @Column(name = "post_title",nullable = false)
    private String title;

    @Column(name = "post_description",nullable = false)
    private String description;

    @Column(name = "post_content",nullable = false)
    private String content;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Comment> commentSet;
}
