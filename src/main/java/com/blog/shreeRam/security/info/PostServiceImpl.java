package com.blog.shreeRam.security.info;

import com.blog.shreeRam.entity.Post;
import com.blog.shreeRam.exception.ResourceNotFoundException;
import com.blog.shreeRam.payload.PostDto;
import com.blog.shreeRam.repository.PostRepository;
import com.blog.shreeRam.security.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savePost = postRepository.save(post);
        PostDto dto = mapToDto(savePost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post is not found with id:" + id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);
        System.out.println(pagePosts);
        List<Post> posts = pagePosts.getContent();

        System.out.println(posts);
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;
    }


    Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;

    }
}
