package com.example.blog.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
