package com.example.blog.controller;

import com.example.blog.dto.CommentDto;
import com.example.blog.repository.CommentRepository;
import com.example.blog.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long postId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId, @PathVariable(value = "id") Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping ("/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long postId,
            @PathVariable(value = "id") Long commentId){
        commentService.deletePostById(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
