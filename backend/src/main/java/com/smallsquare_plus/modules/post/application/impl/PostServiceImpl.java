package com.smallsquare_plus.modules.post.application.impl;

import com.smallsquare_plus.modules.post.application.PostService;
import com.smallsquare_plus.modules.post.utils.PostUtils;
import com.smallsquare_plus.modules.post.web.dto.request.CreatePostReqDTO;
import com.smallsquare_plus.modules.post.web.dto.response.CreatePostResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostUtils postUtils;

    @Override
    public CreatePostResDTO createPost(Long userId, CreatePostReqDTO reqDTO) {

        return null;
    }
}
