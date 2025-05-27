package com.storius.storius.repos;

import com.storius.storius.entities.Comment;
import com.storius.storius.entities.CommentVote;
import com.storius.storius.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    Optional<CommentVote> findByUserAndComment(User user, Comment comment);
}