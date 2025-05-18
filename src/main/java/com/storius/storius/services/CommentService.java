package com.storius.storius.services;

import com.storius.storius.entities.Book;
import com.storius.storius.entities.Comment;
import com.storius.storius.entities.CommentVote;
import com.storius.storius.entities.User;
import com.storius.storius.repos.BookRepository;
import com.storius.storius.repos.CommentRepository;
import com.storius.storius.repos.CommentVoteRepository;
import com.storius.storius.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService
{
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final CommentVoteRepository commentVoteRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, BookRepository bookRepository, CommentVoteRepository commentVoteRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.commentVoteRepository = commentVoteRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getCommentsBybook(Long bookId) {
        return commentRepository.findByBookIdOrderByTimestampDesc(bookId);
    }

    public Comment addComment(Long bookId, String content, User user) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book not found"));

        Comment comment = new Comment();
        comment.setBook(book);
        comment.setUser(user);
        comment.setContent(content);
        comment.setLikes(0);

        return commentRepository.save(comment);
    }

    public int upvote(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

        User user = userRepository.findById(userId).get();

        Optional<CommentVote> optionalCommentVote = commentVoteRepository.findByUserAndComment(user, comment);

        if(optionalCommentVote.isPresent())
        {
            CommentVote commentVote = optionalCommentVote.get();

            if (commentVote.getVoteType() <= -1)
            {
                commentVote.setVoteType(1);
                comment.setLikes(comment.getLikes() + 2);
                commentRepository.save(comment);
            }
            return comment.getLikes();
        }

        CommentVote vote = new CommentVote();
        vote.setUser(user);
        vote.setComment(comment);
        vote.setVoteType(1);
        commentVoteRepository.save(vote);

        comment.setLikes(comment.getLikes() + 1);
        commentRepository.save(comment);
        return comment.getLikes();
    }

    public int downvote(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        User user = userRepository.findById(userId).get();

        Optional<CommentVote> optionalCommentVote = commentVoteRepository.findByUserAndComment(user, comment);

        if(optionalCommentVote.isPresent())
        {
            CommentVote commentVote = optionalCommentVote.get();

            if (commentVote.getVoteType() >= 1)
            {
                commentVote.setVoteType(-1);
                comment.setLikes(comment.getLikes() - 2);
                commentRepository.save(comment);
            }
            return comment.getLikes();
        }

        CommentVote vote = new CommentVote();
        vote.setUser(user);
        vote.setComment(comment);
        vote.setVoteType(-1);
        commentVoteRepository.save(vote);

        comment.setLikes(comment.getLikes() - 1);
        commentRepository.save(comment);
        return comment.getLikes();
    }
}
