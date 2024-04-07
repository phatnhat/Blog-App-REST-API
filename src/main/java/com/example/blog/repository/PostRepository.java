package com.example.blog.repository;

import com.example.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.comments WHERE p.id = ?1")
    Optional<Post> getAllByIdJoinFetch(Long id);

    List<Post> findByCategoryId(Long categoryId);
}
