package com.storius.storius.services;

import com.storius.storius.entities.Book;
import com.storius.storius.entities.Comment;
import com.storius.storius.entities.User;
import com.storius.storius.repos.BookRepository;
import com.storius.storius.repos.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService
{
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentService(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
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

        return commentRepository.save(comment);
    }
}
