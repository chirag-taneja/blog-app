package com.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "tb_comment"
)
@ToString(exclude = "post")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String comment;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "postId",
            referencedColumnName = "post_id",
            nullable = false
    )
    private Post post;

    public Comment(String comment, String name) {
        this.comment = comment;
        this.name = name;
    }
}
