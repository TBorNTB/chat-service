package com.sejong.chatservice.infrastructure.comment.entity;

import com.sejong.chatservice.core.comment.domain.Comment;
import com.sejong.chatservice.core.enums.PostType;
import com.sejong.chatservice.core.reply.domain.Reply;
import com.sejong.chatservice.infrastructure.reply.entity.ReplyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long userId;
    private Long postId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private PostType postType;

    @OneToMany(mappedBy = "commentEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyEntity> replyEntities = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentEntity from(Comment comment) {

        return CommentEntity.builder()
                .id(null)
                .content(comment.getContent())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .postType(comment.getPostType())
                .replyEntities(new ArrayList<>())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public Comment toDomain() {
        return Comment.builder()
                .id(this.id)
                .content(this.content)
                .userId(this.userId)
                .postId(this.postId)
                .postType(this.postType)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .replyCount((long)replyEntities.size())
                .build();
    }

    public Comment updateComment(String content, LocalDateTime updatedAt) {
        this.content = content;
        this.updatedAt = updatedAt;

        return toDomain();
    }
}
