package com.example.blog.handler;

import com.example.blog.exception.BlogAPIException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exc) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(String.valueOf(new Date()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BlogAPIException exc) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(exc.getStatus().value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(String.valueOf(new Date()));
        return new ResponseEntity<>(error, exc.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(String.valueOf(new Date()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fileName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fileName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//        Map<String, String> errors = new HashMap<>();
//        exception.getBindingResult().getAllErrors().forEach(error -> {
//            String fileName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fileName, message);
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
}