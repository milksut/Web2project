package com.storius.storius.services;

import com.storius.storius.entities.Book;
import com.storius.storius.repos.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class bookService {

    private final BookRepository bookRepository;

    public bookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audiobook not found"));
    }

    public List<Book> getFeaturedBooks() {
        // Example: Fetch top 5 books based on rating
        return bookRepository.findTop5ByOrderByRatingDesc();
    }

    public List<Book> getInProgressBooks() {
        // Example: Fetch books marked as "in progress" by the user
        return bookRepository.findInProgressBooks();
    }

    public List<Book> getTrendingBooks() {
        // Example: Fetch trending books based on popularity
        return bookRepository.findTrendingBooks();
    }
}
