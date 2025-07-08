package com.smallsquare_plus.modules.post.application;

import com.smallsquare_plus.modules.post.web.dto.request.CreatePostReqDTO;
import com.smallsquare_plus.modules.post.web.dto.response.CreatePostResDTO;

public interface PostService {

    CreatePostResDTO createPost(Long userId, CreatePostReqDTO reqDTO);
}
