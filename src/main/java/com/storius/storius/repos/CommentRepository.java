package com.storius.storius.repos;

import com.storius.storius.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    List<Comment> findByBookIdOrderByTimestampDesc(Long bookId);
}
