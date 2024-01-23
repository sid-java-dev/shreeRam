package com.blog.shreeRam.security;

import com.blog.shreeRam.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);


    List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
