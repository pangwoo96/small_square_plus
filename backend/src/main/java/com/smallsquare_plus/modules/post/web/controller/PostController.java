package com.smallsquare_plus.modules.post.web.controller;

import com.smallsquare_plus.modules.post.application.PostService;
import com.smallsquare_plus.modules.post.web.dto.request.CreatePostReqDTO;
import com.smallsquare_plus.modules.post.web.dto.response.CreatePostResDTO;
import com.smallsquare_plus.modules.user.infrastructure.auth.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<CreatePostResDTO> createPost(CustomUserDetails userDetails, CreatePostReqDTO reqDTO) {
        CreatePostResDTO resDTO = postService.createPost(userDetails.getUserId(), reqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDTO);
    }
}
