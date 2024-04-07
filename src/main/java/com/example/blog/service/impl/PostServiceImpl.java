package com.example.blog.service.impl;

import com.example.blog.dto.PostDto;
import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.response.PostResponse;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId()));

        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDto> content = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
