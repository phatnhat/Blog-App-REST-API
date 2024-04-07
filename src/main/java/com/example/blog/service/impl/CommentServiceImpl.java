package com.example.blog.service.impl;

import com.example.blog.dto.CommentDto;
import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.exception.BlogAPIException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment commentSave = commentRepository.save(comment);
        CommentDto commentResponse = modelMapper.map(commentSave, CommentDto.class);
        return commentResponse;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<CommentDto> commentsResponse = commentRepository.findByPostId(postId).stream().map((comment) -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentsResponse;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);
        return commentResponse;
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        modelMapper.map(commentDto, comment);
        Comment commentUpdate = commentRepository.save(comment);
        CommentDto commentResponse = modelMapper.map(commentUpdate, CommentDto.class);
        return commentResponse;
    }

    @Override
    public void deletePostById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.deleteById(commentId);
    }
}
