package com.smallsquare_plus.modules.post.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long postId;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;
    private Long dislikeCount;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

}
