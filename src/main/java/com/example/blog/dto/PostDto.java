package com.example.blog.dto;

import com.example.blog.entity.Category;
import com.example.blog.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;

    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 2, message = "Description title should have at least 2 characters")
    private String description;

    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    @Schema(
            description = "Blog Post Category"
    )
    private Long categoryId;
}
