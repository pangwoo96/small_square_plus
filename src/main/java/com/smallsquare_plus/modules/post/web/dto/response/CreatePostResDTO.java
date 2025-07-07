package com.smallsquare_plus.modules.post.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class CreatePostResDTO {

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
